import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream

data class CliOptions(
    val command: String,
    val outputPath: String? = null,
    val autoOpen: Boolean = true,
    val generateHtml: Boolean = false
)

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printUsage()
        return
    }

    val options = parseArgs(args)
    if (options == null) {
        printUsage()
        return
    }

    val outputFiles = when (options.command.lowercase()) {
        "flyingsaucer" -> {
            val file = FlyingSaucerPdf.createPdf(options.outputPath, options.generateHtml)
            listOf(file)
        }
        "openpdf" -> {
            val file = OpenPdf.createPdf(options.outputPath)
            listOf(file)
        }
        "itext" -> {
            val file = ItextPdf.createPdf(options.outputPath)
            listOf(file)
        }
        "all" -> {
            val outputPath1 = options.outputPath?.let { "${it}_flyingsaucer" }
            val outputPath2 = options.outputPath?.let { "${it}_openpdf" }
            val outputPath3 = options.outputPath?.let { "${it}_itext" }
            listOf(
                FlyingSaucerPdf.createPdf(outputPath1, options.generateHtml),
                OpenPdf.createPdf(outputPath2),
                ItextPdf.createPdf(outputPath3)
            )
        }
        else -> {
            println("Unknown command: ${options.command}")
            printUsage()
            return
        }
    }

    if (options.autoOpen && Desktop.isDesktopSupported()) {
        outputFiles.forEach { file ->
            Desktop.getDesktop().open(file)
        }
    } else {
        outputFiles.forEach { file ->
            println("File created at: ${file.absolutePath}")
        }
    }
}

private fun parseArgs(args: Array<String>): CliOptions? {
    if (args.isEmpty()) return null
    
    var command = args[0]
    var outputPath: String? = null
    var autoOpen = true
    var generateHtml = false
    
    var i = 1
    while (i < args.size) {
        when (args[i]) {
            "--output", "-o" -> {
                if (i + 1 >= args.size) {
                    println("Error: --output requires a path argument")
                    return null
                }
                outputPath = args[++i]
            }
            "--no-open" -> {
                autoOpen = false
            }
            "--html" -> {
                generateHtml = true
            }
            else -> {
                println("Unknown option: ${args[i]}")
                return null
            }
        }
        i++
    }
    
    return CliOptions(command, outputPath, autoOpen, generateHtml)
}

private fun printUsage() {
    println("""
        Usage: java -jar pdf-generator.jar <command> [options]
        
        Commands:
        - flyingsaucer: Generate PDF using Flying Saucer
        - openpdf: Generate PDF using OpenPDF
        - itext: Generate PDF using iText 7
        - all: Generate PDFs using all libraries
        
        Options:
        -o, --output <path>: Specify output file path (without extension)
        --no-open: Don't automatically open the generated PDF
        --html: Generate HTML instead of PDF (only for flyingsaucer command)
        
        Examples:
        java -jar pdf-generator.jar all
        java -jar pdf-generator.jar flyingsaucer -o /tmp/my-pdf
        java -jar pdf-generator.jar openpdf --no-open
        java -jar pdf-generator.jar flyingsaucer --html
        java -jar pdf-generator.jar itext -o /tmp/itext-pdf
    """.trimIndent())
}

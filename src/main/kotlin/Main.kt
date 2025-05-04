// import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
// import com.lowagie.text.*
// import com.lowagie.text.pdf.PdfWriter
// import com.lowagie.text.pdf.HyphenationAuto
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream

data class CliOptions(
    val command: String,
    val outputPath: String? = null,
    val autoOpen: Boolean = true
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

    when (options.command.lowercase()) {
        "flyingsaucer" -> FlyingSaucerPdf.createPdf(options.outputPath, options.autoOpen)
        "openpdf" -> OpenPdf.createPdf(options.outputPath, options.autoOpen)
        "all" -> {
            val outputPath1 = options.outputPath?.let { "${it}_flyingsaucer" }
            val outputPath2 = options.outputPath?.let { "${it}_openpdf" }
            FlyingSaucerPdf.createPdf(outputPath1, options.autoOpen)
            OpenPdf.createPdf(outputPath2, options.autoOpen)
        }
        else -> {
            println("Unknown command: ${options.command}")
            printUsage()
        }
    }
}

private fun parseArgs(args: Array<String>): CliOptions? {
    if (args.isEmpty()) return null
    
    var command = args[0]
    var outputPath: String? = null
    var autoOpen = true
    
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
            else -> {
                println("Unknown option: ${args[i]}")
                return null
            }
        }
        i++
    }
    
    return CliOptions(command, outputPath, autoOpen)
}

private fun printUsage() {
    println("""
        Usage: java -jar pdf-generator.jar <command> [options]
        
        Commands:
        - flyingsaucer: Generate PDF using Flying Saucer
        - openpdf: Generate PDF using OpenPDF
        - all: Generate PDFs using both libraries
        
        Options:
        -o, --output <path>: Specify output file path (without extension)
        --no-open: Don't automatically open the generated PDF
        
        Examples:
        java -jar pdf-generator.jar all
        java -jar pdf-generator.jar flyingsaucer -o /tmp/my-pdf
        java -jar pdf-generator.jar openpdf --no-open
    """.trimIndent())
}

// fun createPdfFlyingsaucer() {
//     val longText = """
//         Die Bundesrepublik Deutschland ist ein föderaler Staat in Mitteleuropa. Sie besteht aus 16 Bundesländern und hat eine parlamentarische Demokratie. Die Hauptstadt und Regierungssitz ist Berlin. Deutschland ist Gründungsmitglied der Europäischen Union und der Eurozone. Die deutsche Wirtschaft ist die größte Volkswirtschaft Europas und die viertgrößte der Welt. Die Bundesrepublik ist ein hochindustrialisiertes Land mit einer starken Exportorientierung. Die wichtigsten Wirtschaftszweige sind die Automobilindustrie, der Maschinenbau, die chemische Industrie und die Elektrotechnik. Deutschland ist bekannt für seine Ingenieurskunst und technologische Innovationen. Die deutsche Sprache ist die am häufigsten gesprochene Muttersprache in der Europäischen Union. Die deutsche Kultur hat eine reiche Tradition in den Bereichen Literatur, Musik, Philosophie und Wissenschaft. Deutschland ist ein wichtiger Akteur in der internationalen Politik und engagiert sich in den Vereinten Nationen, der NATO und anderen internationalen Organisationen. Die deutsche Gesellschaft ist geprägt von Werten wie Toleranz, Meinungsfreiheit und sozialer Gerechtigkeit. Das deutsche Bildungssystem genießt weltweit einen guten Ruf. Die Universitäten und Forschungseinrichtungen in Deutschland sind führend in vielen wissenschaftlichen Disziplinen. Die deutsche Geschichte ist geprägt von bedeutenden Ereignissen wie der Reformation, der Aufklärung und der industriellen Revolution. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Zusammenarbeit und setzt sich für Frieden, Sicherheit und nachhaltige Entwicklung ein. Die deutsche Wirtschaft ist stark von der Globalisierung geprägt und profitiert vom internationalen Handel. Die deutsche Gesellschaft ist multikulturell und weltoffen. Deutschland ist ein beliebtes Ziel für Touristen aus aller Welt. Die deutsche Kulturlandschaft ist vielfältig und reich an historischen Stätten, Museen und kulturellen Veranstaltungen. Die deutsche Sprache ist eine wichtige Wissenschaftssprache und wird weltweit an Schulen und Universitäten unterrichtet. Die Bundesrepublik Deutschland ist ein wichtiger Akteur in der internationalen Klimapolitik und setzt sich für den Schutz der Umwelt ein. Die deutsche Wirtschaft ist geprägt von einer starken mittelständischen Struktur und einer hohen Innovationskraft. Die deutsche Gesellschaft ist geprägt von einem hohen Maß an sozialer Sicherheit und einem gut ausgebauten Sozialsystem. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Entwicklungszusammenarbeit und setzt sich für die Bekämpfung von Armut und Ungleichheit ein.
//     """.trimIndent()

//     val repeated = (1..12).joinToString("\n") { index ->
//         val paragraph = "<p lang=\"de\">$longText</p>"
//         if (index == 4 || index == 9) {
//             """
//             <div class="special-paragraph">
//                 <div class="sidebar">Anmerkungen für Abschnitt $index</div>
//                 $paragraph
//             </div>
//             """
//         } else {
//             paragraph
//         }
//     }

//     val html = """
//         <!DOCTYPE html>
//         <html lang="de">
//         <head>
//             <meta charset="UTF-8" />
//             <style>
//                 @page {
//                     margin: 2cm;
//                 }

//                 @page :left {
//                     margin: 2cm 3cm 2cm 5cm;
//                 }

//                 @page :right {
//                     margin: 2cm 5cm 2cm 3cm;
//                 }

//                 body {
//                     font-family: "DejaVu Serif", serif;
//                     font-size: 11pt;
//                     line-height: 1.5;
//                     text-align: justify;
//                     hyphens: auto;
//                 }

//                 .special-paragraph {
//                     position: relative;
//                 }

//                 .sidebar {
//                     position: running(sidebar);
//                     width: 2cm;
//                     font-size: 8pt;
//                     writing-mode: vertical-rl;
//                     text-align: center;
//                 }

//                 @page :left {
//                     @left {
//                         content: element(sidebar);
//                     }
//                 }

//                 @page :right {
//                     @right {
//                         content: element(sidebar);
//                     }
//                 }

//                 h1 {
//                     text-align: center;
//                     margin-bottom: 1em;
//                 }
//             </style>
//         </head>
//         <body>
//             <h1>Beispieltext mit selektiven Seitenrändern</h1>

//             $repeated
//         </body>
//         </html>
//     """.trimIndent()

//     val outputFile = File("deutschland-seitenränder.pdf")
//     FileOutputStream(outputFile).use { os ->
//         PdfRendererBuilder().useFastMode()
//             .withHtmlContent(html, null)
//             .toStream(os)
//             .run()
//     }

//     if (Desktop.isDesktopSupported()) {
//         Desktop.getDesktop().open(outputFile)
//     } else {
//         println("Desktop is not supported. PDF was created at: ${outputFile.absolutePath}")
//     }
// }

// fun createPdfOpenPdf() {
//     // Create a new document with A4 size
//     val document = Document(PageSize.A4)
    
//     // Set margins
//     document.setMargins(36f, 36f, 36f, 36f)
    
//     // Create a temporary file to store the PDF
//     val pdfFile = File.createTempFile("example", ".pdf")
    
//     // Create a PDF writer
//     PdfWriter.getInstance(document, FileOutputStream(pdfFile))
    
//     // Open the document
//     document.open()
    
//     // Create the text for our columns
//     val hyp = HyphenationAuto("de", "DR", 2, 2)
    
//     // Add title
//     val title = Paragraph("Beispieltext für eine zweispaltige PDF-Datei", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16f))
//     title.alignment = Element.ALIGN_CENTER
//     title.spacingAfter = 20f
//     document.add(title)
    
//     // Create a longer German text
//     val longText = Chunk("""
//         Die Bundesrepublik Deutschland ist ein föderaler Staat in Mitteleuropa. Sie besteht aus 16 Bundesländern und hat eine parlamentarische Demokratie. Die Hauptstadt und Regierungssitz ist Berlin. Deutschland ist Gründungsmitglied der Europäischen Union und der Eurozone. Die deutsche Wirtschaft ist die größte Volkswirtschaft Europas und die viertgrößte der Welt. Die Bundesrepublik ist ein hochindustrialisiertes Land mit einer starken Exportorientierung. Die wichtigsten Wirtschaftszweige sind die Automobilindustrie, der Maschinenbau, die chemische Industrie und die Elektrotechnik. Deutschland ist bekannt für seine Ingenieurskunst und technologische Innovationen. Die deutsche Sprache ist die am häufigsten gesprochene Muttersprache in der Europäischen Union. Die deutsche Kultur hat eine reiche Tradition in den Bereichen Literatur, Musik, Philosophie und Wissenschaft. Deutschland ist ein wichtiger Akteur in der internationalen Politik und engagiert sich in den Vereinten Nationen, der NATO und anderen internationalen Organisationen. Die deutsche Gesellschaft ist geprägt von Werten wie Toleranz, Meinungsfreiheit und sozialer Gerechtigkeit. Das deutsche Bildungssystem genießt weltweit einen guten Ruf. Die Universitäten und Forschungseinrichtungen in Deutschland sind führend in vielen wissenschaftlichen Disziplinen. Die deutsche Geschichte ist geprägt von bedeutenden Ereignissen wie der Reformation, der Aufklärung und der industriellen Revolution. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Zusammenarbeit und setzt sich für Frieden, Sicherheit und nachhaltige Entwicklung ein. Die deutsche Wirtschaft ist stark von der Globalisierung geprägt und profitiert vom internationalen Handel. Die deutsche Gesellschaft ist multikulturell und weltoffen. Deutschland ist ein beliebtes Ziel für Touristen aus aller Welt. Die deutsche Kulturlandschaft ist vielfältig und reich an historischen Stätten, Museen und kulturellen Veranstaltungen. Die deutsche Sprache ist eine wichtige Wissenschaftssprache und wird weltweit an Schulen und Universitäten unterrichtet. Die Bundesrepublik Deutschland ist ein wichtiger Akteur in der internationalen Klimapolitik und setzt sich für den Schutz der Umwelt ein. Die deutsche Wirtschaft ist geprägt von einer starken mittelständischen Struktur und einer hohen Innovationskraft. Die deutsche Gesellschaft ist geprägt von einem hohen Maß an sozialer Sicherheit und einem gut ausgebauten Sozialsystem. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Entwicklungszusammenarbeit und setzt sich für die Bekämpfung von Armut und Ungleichheit ein.
//     """.trimIndent())
//     longText.hyphenation = hyp
    
//     // Create a table with three columns (sidebar + two main columns)
//     val table = Table(3)
//     table.width = 100f
//     table.padding = 5f
//     table.spacing = 5f
//     table.border = Rectangle.NO_BORDER
    
//     // Set column widths (sidebar: 15%, main columns: 42.5% each)
//     table.setWidths(floatArrayOf(20f, 40f, 40f))
    
//     // Create sidebar content
//     val sidebarText = Paragraph("""
//         Anmerkungen:
        
//         • Dies ist ein Beispiel für einen Seitenrand
//         • Hier können wichtige Hinweise stehen
//         • Oder Querverweise
//         • Oder ergänzende Informationen
//     """.trimIndent(), FontFactory.getFont(FontFactory.HELVETICA, 9f))
//     sidebarText.alignment = Element.ALIGN_JUSTIFIED
//     sidebarText.hyphenation = hyp
    
//     // Create the main content with hyphenation
//     val content = Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 11f))
//     content.hyphenation = hyp
//     content.alignment = Element.ALIGN_JUSTIFIED
//     content.add(longText)

//     // Add content to all columns
//     val sidebarCell = Cell()
//     sidebarCell.add(sidebarText)
//     sidebarCell.border = Rectangle.NO_BORDER
//     table.addCell(sidebarCell)
    
//     val leftCell = Cell()
//     leftCell.add(content)
//     leftCell.border = Rectangle.NO_BORDER
//     table.addCell(leftCell)
    
//     val rightCell = Cell()
//     rightCell.add(Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 11f)).apply {
//         hyphenation = hyp
//         alignment = Element.ALIGN_JUSTIFIED
//         add(longText)
//     })
//     rightCell.border = Rectangle.NO_BORDER
//     table.addCell(rightCell)
    
//     document.add(table)
    
//     // Close the document
//     document.close()
    
//     // Open the PDF with the system's default PDF viewer
//     if (Desktop.isDesktopSupported()) {
//         Desktop.getDesktop().open(pdfFile)
//     } else {
//         println("Desktop is not supported. PDF was created at: ${pdfFile.absolutePath}")
//     }
// } 
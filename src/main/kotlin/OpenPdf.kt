import com.lowagie.text.*
import com.lowagie.text.pdf.PdfWriter
import com.lowagie.text.pdf.HyphenationAuto
import com.lowagie.text.pdf.MultiColumnText
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream

object OpenPdf {
    fun createPdf(outputPath: String? = null): File {
        val longText = """
            Die Bundesrepublik Deutschland ist ein föderaler Staat in Mitteleuropa. Sie besteht aus 16 Bundesländern und hat eine parlamentarische Demokratie. Die Hauptstadt und Regierungssitz ist Berlin. Deutschland ist Gründungsmitglied der Europäischen Union und der Eurozone. Die deutsche Wirtschaft ist die größte Volkswirtschaft Europas und die viertgrößte der Welt. Die Bundesrepublik ist ein hochindustrialisiertes Land mit einer starken Exportorientierung. Die wichtigsten Wirtschaftszweige sind die Automobilindustrie, der Maschinenbau, die chemische Industrie und die Elektrotechnik. Deutschland ist bekannt für seine Ingenieurskunst und technologische Innovationen. Die deutsche Sprache ist die am häufigsten gesprochene Muttersprache in der Europäischen Union. Die deutsche Kultur hat eine reiche Tradition in den Bereichen Literatur, Musik, Philosophie und Wissenschaft. Deutschland ist ein wichtiger Akteur in der internationalen Politik und engagiert sich in den Vereinten Nationen, der NATO und anderen internationalen Organisationen. Die deutsche Gesellschaft ist geprägt von Werten wie Toleranz, Meinungsfreiheit und sozialer Gerechtigkeit. Das deutsche Bildungssystem genießt weltweit einen guten Ruf. Die Universitäten und Forschungseinrichtungen in Deutschland sind führend in vielen wissenschaftlichen Disziplinen. Die deutsche Geschichte ist geprägt von bedeutenden Ereignissen wie der Reformation, der Aufklärung und der industriellen Revolution. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Zusammenarbeit und setzt sich für Frieden, Sicherheit und nachhaltige Entwicklung ein. Die deutsche Wirtschaft ist stark von der Globalisierung geprägt und profitiert vom internationalen Handel. Die deutsche Gesellschaft ist multikulturell und weltoffen. Deutschland ist ein beliebtes Ziel für Touristen aus aller Welt. Die deutsche Kulturlandschaft ist vielfältig und reich an historischen Stätten, Museen und kulturellen Veranstaltungen. Die deutsche Sprache ist eine wichtige Wissenschaftssprache und wird weltweit an Schulen und Universitäten unterrichtet. Die Bundesrepublik Deutschland ist ein wichtiger Akteur in der internationalen Klimapolitik und setzt sich für den Schutz der Umwelt ein. Die deutsche Wirtschaft ist geprägt von einer starken mittelständischen Struktur und einer hohen Innovationskraft. Die deutsche Gesellschaft ist geprägt von einem hohen Maß an sozialer Sicherheit und einem gut ausgebauten Sozialsystem. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Entwicklungszusammenarbeit und setzt sich für die Bekämpfung von Armut und Ungleichheit ein.
        """.trimIndent()

        val outputFile = if (outputPath != null) {
            File("$outputPath.pdf")
        } else {
            File.createTempFile("openpdf", ".pdf")
        }

        FileOutputStream(outputFile).use { out ->
            val document = Document()
            PdfWriter.getInstance(document, out)
            document.open()

            // Add title with formatting
            val titleFont = Font(Font.HELVETICA, 24f, Font.BOLD)
            val title = Paragraph("Dokument mit/ohne Seitenrandnotizen", titleFont)
            title.alignment = Element.ALIGN_CENTER
            title.spacingAfter = 20f
            document.add(title)

            // Create multi-column layout
            val multiColumnText = MultiColumnText()
            val left = document.left()
            val right = document.right()
            val gutter = 10f // Space between columns
            val numColumns = 2
            multiColumnText.addRegularColumns(left, right, gutter, numColumns)

            // Create paragraph with hyphenation
            val chunk = Chunk(longText)
            chunk.hyphenation = HyphenationAuto("de", "DR", 2, 2)

            val paragraph = Paragraph(chunk)

            // Add content to columns
            multiColumnText.addElement(paragraph)
            
            // Add the multi-column text to the document
            document.add(multiColumnText)

            document.close()
        }

        return outputFile
    }
} 
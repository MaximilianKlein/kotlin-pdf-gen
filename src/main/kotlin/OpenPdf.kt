import com.lowagie.text.*
import com.lowagie.text.pdf.PdfWriter
import com.lowagie.text.pdf.HyphenationAuto
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream

object OpenPdf {
    fun createPdf(outputPath: String? = null, autoOpen: Boolean = true) {
        // Create a new document with A4 size
        val document = Document(PageSize.A4)
        
        // Set margins
        document.setMargins(36f, 36f, 36f, 36f)
        
        // Create output file
        val pdfFile = if (outputPath != null) {
            File("$outputPath.pdf")
        } else {
            File.createTempFile("openpdf", ".pdf")
        }
        
        // Create a PDF writer
        PdfWriter.getInstance(document, FileOutputStream(pdfFile))
        
        // Open the document
        document.open()
        
        // Create the text for our columns
        val hyp = HyphenationAuto("de", "DR", 2, 2)
        
        // Add title
        val title = Paragraph("Beispieltext für eine zweispaltige PDF-Datei", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16f))
        title.alignment = Element.ALIGN_CENTER
        title.spacingAfter = 20f
        document.add(title)
        
        // Create a longer German text
        val longText = Chunk("""
            Die Bundesrepublik Deutschland ist ein föderaler Staat in Mitteleuropa. Sie besteht aus 16 Bundesländern und hat eine parlamentarische Demokratie. Die Hauptstadt und Regierungssitz ist Berlin. Deutschland ist Gründungsmitglied der Europäischen Union und der Eurozone. Die deutsche Wirtschaft ist die größte Volkswirtschaft Europas und die viertgrößte der Welt. Die Bundesrepublik ist ein hochindustrialisiertes Land mit einer starken Exportorientierung. Die wichtigsten Wirtschaftszweige sind die Automobilindustrie, der Maschinenbau, die chemische Industrie und die Elektrotechnik. Deutschland ist bekannt für seine Ingenieurskunst und technologische Innovationen. Die deutsche Sprache ist die am häufigsten gesprochene Muttersprache in der Europäischen Union. Die deutsche Kultur hat eine reiche Tradition in den Bereichen Literatur, Musik, Philosophie und Wissenschaft. Deutschland ist ein wichtiger Akteur in der internationalen Politik und engagiert sich in den Vereinten Nationen, der NATO und anderen internationalen Organisationen. Die deutsche Gesellschaft ist geprägt von Werten wie Toleranz, Meinungsfreiheit und sozialer Gerechtigkeit. Das deutsche Bildungssystem genießt weltweit einen guten Ruf. Die Universitäten und Forschungseinrichtungen in Deutschland sind führend in vielen wissenschaftlichen Disziplinen. Die deutsche Geschichte ist geprägt von bedeutenden Ereignissen wie der Reformation, der Aufklärung und der industriellen Revolution. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Zusammenarbeit und setzt sich für Frieden, Sicherheit und nachhaltige Entwicklung ein. Die deutsche Wirtschaft ist stark von der Globalisierung geprägt und profitiert vom internationalen Handel. Die deutsche Gesellschaft ist multikulturell und weltoffen. Deutschland ist ein beliebtes Ziel für Touristen aus aller Welt. Die deutsche Kulturlandschaft ist vielfältig und reich an historischen Stätten, Museen und kulturellen Veranstaltungen. Die deutsche Sprache ist eine wichtige Wissenschaftssprache und wird weltweit an Schulen und Universitäten unterrichtet. Die Bundesrepublik Deutschland ist ein wichtiger Akteur in der internationalen Klimapolitik und setzt sich für den Schutz der Umwelt ein. Die deutsche Wirtschaft ist geprägt von einer starken mittelständischen Struktur und einer hohen Innovationskraft. Die deutsche Gesellschaft ist geprägt von einem hohen Maß an sozialer Sicherheit und einem gut ausgebauten Sozialsystem. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Entwicklungszusammenarbeit und setzt sich für die Bekämpfung von Armut und Ungleichheit ein.
        """.trimIndent())
        longText.hyphenation = hyp
        
        // Create a table with three columns (sidebar + two main columns)
        val table = Table(3)
        table.width = 100f
        table.padding = 5f
        table.spacing = 5f
        table.border = Rectangle.NO_BORDER
        
        // Set column widths (sidebar: 15%, main columns: 42.5% each)
        table.setWidths(floatArrayOf(20f, 40f, 40f))
        
        // Create sidebar content
        val sidebarText = Paragraph("""
            Anmerkungen:
            
            • Dies ist ein Beispiel für einen Seitenrand
            • Hier können wichtige Hinweise stehen
            • Oder Querverweise
            • Oder ergänzende Informationen
        """.trimIndent(), FontFactory.getFont(FontFactory.HELVETICA, 9f))
        sidebarText.alignment = Element.ALIGN_JUSTIFIED
        sidebarText.hyphenation = hyp
        
        // Create the main content with hyphenation
        val content = Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 11f))
        content.hyphenation = hyp
        content.alignment = Element.ALIGN_JUSTIFIED
        content.add(longText)

        // Add content to all columns
        val sidebarCell = Cell()
        sidebarCell.add(sidebarText)
        sidebarCell.border = Rectangle.NO_BORDER
        table.addCell(sidebarCell)
        
        val leftCell = Cell()
        leftCell.add(content)
        leftCell.border = Rectangle.NO_BORDER
        table.addCell(leftCell)
        
        val rightCell = Cell()
        rightCell.add(Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 11f)).apply {
            hyphenation = hyp
            alignment = Element.ALIGN_JUSTIFIED
            add(longText)
        })
        rightCell.border = Rectangle.NO_BORDER
        table.addCell(rightCell)
        
        document.add(table)
        
        // Close the document
        document.close()
        
        // Open the PDF with the system's default PDF viewer
        if (autoOpen && Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(pdfFile)
        } else {
            println("PDF was created at: ${pdfFile.absolutePath}")
        }
    }
} 
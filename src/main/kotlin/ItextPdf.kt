import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.Canvas
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.renderer.DocumentRenderer
import com.itextpdf.layout.renderer.IRenderer
import com.itextpdf.layout.renderer.ParagraphRenderer
import com.itextpdf.layout.layout.LayoutContext
import com.itextpdf.layout.layout.LayoutResult
import com.itextpdf.layout.layout.LayoutArea
import com.itextpdf.layout.layout.RootLayoutArea
import com.itextpdf.kernel.geom.Rectangle
import com.itextpdf.layout.renderer.DrawContext
import com.itextpdf.layout.hyphenation.HyphenationConfig
import java.io.File

object ItextPdf {
    fun createPdf(outputPath: String? = null): File {
        val longText = """
            Die Bundesrepublik Deutschland ist ein föderaler Staat in Mitteleuropa. Sie besteht aus 16 Bundesländern und hat eine parlamentarische Demokratie. Die Hauptstadt und Regierungssitz ist Berlin. Deutschland ist Gründungsmitglied der Europäischen Union und der Eurozone. Die deutsche Wirtschaft ist die größte Volkswirtschaft Europas und die viertgrößte der Welt. Die Bundesrepublik ist ein hochindustrialisiertes Land mit einer starken Exportorientierung. Die wichtigsten Wirtschaftszweige sind die Automobilindustrie, der Maschinenbau, die chemische Industrie und die Elektrotechnik. Deutschland ist bekannt für seine Ingenieurskunst und technologische Innovationen. Die deutsche Sprache ist die am häufigsten gesprochene Muttersprache in der Europäischen Union. Die deutsche Kultur hat eine reiche Tradition in den Bereichen Literatur, Musik, Philosophie und Wissenschaft. Deutschland ist ein wichtiger Akteur in der internationalen Politik und engagiert sich in den Vereinten Nationen, der NATO und anderen internationalen Organisationen. Die deutsche Gesellschaft ist geprägt von Werten wie Toleranz, Meinungsfreiheit und sozialer Gerechtigkeit. Das deutsche Bildungssystem genießt weltweit einen guten Ruf. Die Universitäten und Forschungseinrichtungen in Deutschland sind führend in vielen wissenschaftlichen Disziplinen. Die deutsche Geschichte ist geprägt von bedeutenden Ereignissen wie der Reformation, der Aufklärung und der industriellen Revolution. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Zusammenarbeit und setzt sich für Frieden, Sicherheit und nachhaltige Entwicklung ein. Die deutsche Wirtschaft ist stark von der Globalisierung geprägt und profitiert vom internationalen Handel. Die deutsche Gesellschaft ist multikulturell und weltoffen. Deutschland ist ein beliebtes Ziel für Touristen aus aller Welt. Die deutsche Kulturlandschaft ist vielfältig und reich an historischen Stätten, Museen und kulturellen Veranstaltungen. Die deutsche Sprache ist eine wichtige Wissenschaftssprache und wird weltweit an Schulen und Universitäten unterrichtet. Die Bundesrepublik Deutschland ist ein wichtiger Akteur in der internationalen Klimapolitik und setzt sich für den Schutz der Umwelt ein. Die deutsche Wirtschaft ist geprägt von einer starken mittelständischen Struktur und einer hohen Innovationskraft. Die deutsche Gesellschaft ist geprägt von einem hohen Maß an sozialer Sicherheit und einem gut ausgebauten Sozialsystem. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Entwicklungszusammenarbeit und setzt sich für die Bekämpfung von Armut und Ungleichheit ein.
        """.trimIndent()

        val marginNote = """
            Wichtige Anmerkung:
            
            • Dies ist ein Beispiel für einen Seitenrand
            • Hier können wichtige Hinweise stehen
            • Oder Querverweise
            • Oder ergänzende Informationen
            
            Die Anmerkung erscheint auf der Seite mit dem breiteren Rand.
        """.trimIndent()

        val outputFile = if (outputPath != null) {
            File("$outputPath.pdf")
        } else {
            File.createTempFile("itext", ".pdf")
        }

        PdfWriter(outputFile).use { writer ->
            PdfDocument(writer).use { pdf ->
                val document = Document(pdf, PageSize.A4)

                // Set a custom renderer
                document.setRenderer(object : DocumentRenderer(document) {
                    override fun getNextRenderer(): IRenderer = this

                    override fun layout(layoutContext: LayoutContext?): LayoutResult {
                        val result = super.layout(layoutContext)
                        if (result != null) {
                            val pageSize = document.pdfDocument.getDefaultPageSize()
                            val pageNum = layoutContext?.area?.pageNumber ?: 1
                            val isEven = pageNum % 2 == 0

                            // Define custom margins
                            val top = 72f
                            val bottom = 72f
                            val left = if (isEven) 172f else 72f
                            val right = if (isEven) 72f else 172f

                            val contentWidth = pageSize.width - left - right
                            val contentHeight = pageSize.height - top - bottom

                            result.occupiedArea.bBox = Rectangle(left, bottom, contentWidth, contentHeight)
                        }
                        return result
                    }

                    override fun updateCurrentArea(overflowResult: LayoutResult?): LayoutArea? {
                        super.updateCurrentArea(overflowResult)
                        val pageNum = document.pdfDocument.numberOfPages
                        val isEven = pageNum % 2 == 0

                        val pageSize = document.pdfDocument.defaultPageSize
                        val top = 72f
                        val bottom = 72f
                        val left = if (isEven) 172f else 72f
                        val right = if (isEven) 72f else 172f

                        val width = pageSize.width - left - right
                        val height = pageSize.height - top - bottom

                        val adjustedArea = RootLayoutArea(pageNum, Rectangle(left, bottom, width, height))
                        this.currentArea = adjustedArea
                        return adjustedArea
                    }
                })

                // Configure hyphenation for German text
                val hyphenationConfig = HyphenationConfig("de", "DR", 2, 2)

                // Add title
                document.add(
                    ParagraphWithMarginNote("Dokument mit Seitenrandnotizen", null)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(16f)
                        .setHyphenation(hyphenationConfig)
                )

                // Add paragraphs with margin notes
                document.add(
                    ParagraphWithMarginNote(longText)
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setFontSize(11f)
                        .setHyphenation(hyphenationConfig)
                )

                // Add more paragraphs to ensure multiple pages
                document.add(
                    ParagraphWithMarginNote(longText, marginNote)
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setFontSize(11f)
                        .setHyphenation(hyphenationConfig)
                )
                document.add(
                    ParagraphWithMarginNote(longText)
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setFontSize(11f)
                        .setHyphenation(hyphenationConfig)
                )
                document.add(
                    ParagraphWithMarginNote(longText, marginNote)
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setFontSize(11f)
                        .setHyphenation(hyphenationConfig)
                )
                document.add(
                    ParagraphWithMarginNote(longText)
                        .setTextAlignment(TextAlignment.JUSTIFIED)
                        .setFontSize(11f)
                        .setHyphenation(hyphenationConfig)
                )

                document.close()
            }
        }

        return outputFile
    }
} 
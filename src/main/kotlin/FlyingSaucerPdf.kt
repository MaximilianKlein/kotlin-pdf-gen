import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import java.io.File
import java.io.FileOutputStream

object FlyingSaucerPdf {
    fun createPdf(outputPath: String? = null, generateHtml: Boolean = false): File {
        val html = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 0;
                        padding: 0;
                    }
                    .container {
                        display: flex;
                        width: 100%;
                    }
                    .sidebar {
                        width: 20%;
                        padding: 20px;
                        background-color: #f5f5f5;
                        font-size: 9pt;
                    }
                    .content {
                        width: 80%;
                        padding: 20px;
                        font-size: 11pt;
                        text-align: justify;
                    }
                    h1 {
                        text-align: center;
                        font-size: 16pt;
                    }
                </style>
            </head>
            <body>
                <h1>Dokument mit Seitenrandnotizen</h1>
                <div class="container">
                    <div class="sidebar">
                        <h3>Anmerkungen:</h3>
                        <ul>
                            <li>Dies ist ein Beispiel für einen Seitenrand</li>
                            <li>Hier können wichtige Hinweise stehen</li>
                            <li>Oder Querverweise</li>
                            <li>Oder ergänzende Informationen</li>
                        </ul>
                    </div>
                    <div class="content">
                        <p>Die Bundesrepublik Deutschland ist ein föderaler Staat in Mitteleuropa. Sie besteht aus 16 Bundesländern und hat eine parlamentarische Demokratie. Die Hauptstadt und Regierungssitz ist Berlin. Deutschland ist Gründungsmitglied der Europäischen Union und der Eurozone. Die deutsche Wirtschaft ist die größte Volkswirtschaft Europas und die viertgrößte der Welt. Die Bundesrepublik ist ein hochindustrialisiertes Land mit einer starken Exportorientierung. Die wichtigsten Wirtschaftszweige sind die Automobilindustrie, der Maschinenbau, die chemische Industrie und die Elektrotechnik. Deutschland ist bekannt für seine Ingenieurskunst und technologische Innovationen. Die deutsche Sprache ist die am häufigsten gesprochene Muttersprache in der Europäischen Union. Die deutsche Kultur hat eine reiche Tradition in den Bereichen Literatur, Musik, Philosophie und Wissenschaft. Deutschland ist ein wichtiger Akteur in der internationalen Politik und engagiert sich in den Vereinten Nationen, der NATO und anderen internationalen Organisationen. Die deutsche Gesellschaft ist geprägt von Werten wie Toleranz, Meinungsfreiheit und sozialer Gerechtigkeit. Das deutsche Bildungssystem genießt weltweit einen guten Ruf. Die Universitäten und Forschungseinrichtungen in Deutschland sind führend in vielen wissenschaftlichen Disziplinen. Die deutsche Geschichte ist geprägt von bedeutenden Ereignissen wie der Reformation, der Aufklärung und der industriellen Revolution. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Zusammenarbeit und setzt sich für Frieden, Sicherheit und nachhaltige Entwicklung ein. Die deutsche Wirtschaft ist stark von der Globalisierung geprägt und profitiert vom internationalen Handel. Die deutsche Gesellschaft ist multikulturell und weltoffen. Deutschland ist ein beliebtes Ziel für Touristen aus aller Welt. Die deutsche Kulturlandschaft ist vielfältig und reich an historischen Stätten, Museen und kulturellen Veranstaltungen. Die deutsche Sprache ist eine wichtige Wissenschaftssprache und wird weltweit an Schulen und Universitäten unterrichtet. Die Bundesrepublik Deutschland ist ein wichtiger Akteur in der internationalen Klimapolitik und setzt sich für den Schutz der Umwelt ein. Die deutsche Wirtschaft ist geprägt von einer starken mittelständischen Struktur und einer hohen Innovationskraft. Die deutsche Gesellschaft ist geprägt von einem hohen Maß an sozialer Sicherheit und einem gut ausgebauten Sozialsystem. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Entwicklungszusammenarbeit und setzt sich für die Bekämpfung von Armut und Ungleichheit ein.</p>
                    </div>
                </div>
            </body>
            </html>
        """.trimIndent()

        val outputFile = if (outputPath != null) {
            File(if (generateHtml) "$outputPath.html" else "$outputPath.pdf")
        } else {
            File.createTempFile("flyingsaucer", if (generateHtml) ".html" else ".pdf")
        }

        if (generateHtml) {
            outputFile.writeText(html)
        } else {
            FileOutputStream(outputFile).use { out ->
                PdfRendererBuilder()
                    .useFastMode()
                    .withHtmlContent(html, null)
                    .toStream(out)
                    .run()
            }
        }

        return outputFile
    }
} 
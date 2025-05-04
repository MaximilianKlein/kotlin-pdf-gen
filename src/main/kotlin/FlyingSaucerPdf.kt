import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import java.awt.Desktop
import java.io.File
import java.io.FileOutputStream

object FlyingSaucerPdf {
    fun createPdf(outputPath: String? = null, autoOpen: Boolean = true) {
        val longText = """
            Die Bundesrepublik Deutschland ist ein föderaler Staat in Mitteleuropa. Sie besteht aus 16 Bundesländern und hat eine parlamentarische Demokratie. Die Hauptstadt und Regierungssitz ist Berlin. Deutschland ist Gründungsmitglied der Europäischen Union und der Eurozone. Die deutsche Wirtschaft ist die größte Volkswirtschaft Europas und die viertgrößte der Welt. Die Bundesrepublik ist ein hochindustrialisiertes Land mit einer starken Exportorientierung. Die wichtigsten Wirtschaftszweige sind die Automobilindustrie, der Maschinenbau, die chemische Industrie und die Elektrotechnik. Deutschland ist bekannt für seine Ingenieurskunst und technologische Innovationen. Die deutsche Sprache ist die am häufigsten gesprochene Muttersprache in der Europäischen Union. Die deutsche Kultur hat eine reiche Tradition in den Bereichen Literatur, Musik, Philosophie und Wissenschaft. Deutschland ist ein wichtiger Akteur in der internationalen Politik und engagiert sich in den Vereinten Nationen, der NATO und anderen internationalen Organisationen. Die deutsche Gesellschaft ist geprägt von Werten wie Toleranz, Meinungsfreiheit und sozialer Gerechtigkeit. Das deutsche Bildungssystem genießt weltweit einen guten Ruf. Die Universitäten und Forschungseinrichtungen in Deutschland sind führend in vielen wissenschaftlichen Disziplinen. Die deutsche Geschichte ist geprägt von bedeutenden Ereignissen wie der Reformation, der Aufklärung und der industriellen Revolution. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Zusammenarbeit und setzt sich für Frieden, Sicherheit und nachhaltige Entwicklung ein. Die deutsche Wirtschaft ist stark von der Globalisierung geprägt und profitiert vom internationalen Handel. Die deutsche Gesellschaft ist multikulturell und weltoffen. Deutschland ist ein beliebtes Ziel für Touristen aus aller Welt. Die deutsche Kulturlandschaft ist vielfältig und reich an historischen Stätten, Museen und kulturellen Veranstaltungen. Die deutsche Sprache ist eine wichtige Wissenschaftssprache und wird weltweit an Schulen und Universitäten unterrichtet. Die Bundesrepublik Deutschland ist ein wichtiger Akteur in der internationalen Klimapolitik und setzt sich für den Schutz der Umwelt ein. Die deutsche Wirtschaft ist geprägt von einer starken mittelständischen Struktur und einer hohen Innovationskraft. Die deutsche Gesellschaft ist geprägt von einem hohen Maß an sozialer Sicherheit und einem gut ausgebauten Sozialsystem. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Entwicklungszusammenarbeit und setzt sich für die Bekämpfung von Armut und Ungleichheit ein.
        """.trimIndent()

        val repeated = (1..12).joinToString("\n") { index ->
            val paragraph = "<p lang=\"de\">$longText</p>"
            if (index == 4 || index == 9) {
                """
                <div class="special-paragraph">
                    <div class="sidebar">Anmerkungen für Abschnitt $index</div>
                    $paragraph
                </div>
                """
            } else {
                paragraph
            }
        }

        val html = """
            <!DOCTYPE html>
            <html lang="de">
            <head>
                <meta charset="UTF-8" />
                <style>
                    @page {
                        margin: 2cm;
                    }

                    @page :left {
                        margin: 2cm 3cm 2cm 5cm;
                    }

                    @page :right {
                        margin: 2cm 5cm 2cm 3cm;
                    }

                    body {
                        font-family: "DejaVu Serif", serif;
                        font-size: 11pt;
                        line-height: 1.5;
                        text-align: justify;
                        hyphens: auto;
                    }

                    .special-paragraph {
                        position: relative;
                    }

                    .sidebar {
                        position: running(sidebar);
                        width: 2cm;
                        font-size: 8pt;
                        writing-mode: vertical-rl;
                        text-align: center;
                    }

                    @page :left {
                        @left {
                            content: element(sidebar);
                        }
                    }

                    @page :right {
                        @right {
                            content: element(sidebar);
                        }
                    }

                    h1 {
                        text-align: center;
                        margin-bottom: 1em;
                    }
                </style>
            </head>
            <body>
                <h1>Beispieltext mit selektiven Seitenrändern</h1>

                $repeated
            </body>
            </html>
        """.trimIndent()

        val outputFile = if (outputPath != null) {
            File("$outputPath.pdf")
        } else {
            File.createTempFile("flyingsaucer", ".pdf")
        }
        
        FileOutputStream(outputFile).use { os ->
            PdfRendererBuilder().useFastMode()
                .withHtmlContent(html, null)
                .toStream(os)
                .run()
        }

        if (autoOpen && Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(outputFile)
        } else {
            println("PDF was created at: ${outputFile.absolutePath}")
        }
    }
} 
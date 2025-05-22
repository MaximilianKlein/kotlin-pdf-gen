#let spacing = 0.65em

#set page(
  paper: "a4",
  margin: (x: 3.5cm, y: 2.5cm),
  columns: 2
)

#set heading(numbering: "1.")

#set text(lang: "de")
#let margin-note(body) = {
  place(
    left,
    dx: -25%,  // Adjusted to keep the note within the page boundary
    block(
      width: 20%,  // Reduced width to fit within the margin space
      align(
        right,
        text(
          size: 0.8em,
          body
        )
      )
    )
  )
}

= Introduction

#margin-note[
  This is a margin note that appears alongside the main text.
  It can contain multiple lines and even formatting.
]

#set par(
  justify: true,
)
Die Bundesrepublik Deutschland ist ein föderaler Staat in Mitteleuropa. Sie besteht aus 16 Bundesländern und hat eine parlamentarische Demokratie. Die Hauptstadt und Regierungssitz ist Berlin. Deutschland ist Gründungsmitglied der Europäischen Union und der Eurozone. Die deutsche Wirtschaft ist die größte Volkswirtschaft Europas und die viertgrößte der Welt. Die Bundesrepublik ist ein hochindustrialisiertes Land mit einer starken Exportorientierung. Die wichtigsten Wirtschaftszweige sind die Automobilindustrie, der Maschinenbau, die chemische Industrie und die Elektrotechnik. Deutschland ist bekannt für seine Ingenieurskunst und technologische Innovationen. Die deutsche Sprache ist die am häufigsten gesprochene Muttersprache in der Europäischen Union. Die deutsche Kultur hat eine reiche Tradition in den Bereichen Literatur, Musik, Philosophie und Wissenschaft. Deutschland ist ein wichtiger Akteur in der internationalen Politik und engagiert sich in den Vereinten Nationen, der NATO und anderen internationalen Organisationen. Die deutsche Gesellschaft ist geprägt von Werten wie Toleranz, Meinungsfreiheit und sozialer Gerechtigkeit. Das deutsche Bildungssystem genießt weltweit einen guten Ruf. Die Universitäten und Forschungseinrichtungen in Deutschland sind führend in vielen wissenschaftlichen Disziplinen. Die deutsche Geschichte ist geprägt von bedeutenden Ereignissen wie der Reformation, der Aufklärung und der industriellen Revolution. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Zusammenarbeit und setzt sich für Frieden, Sicherheit und nachhaltige Entwicklung ein. Die deutsche Wirtschaft ist stark von der Globalisierung geprägt und profitiert vom internationalen Handel. Die deutsche Gesellschaft ist multikulturell und weltoffen. Deutschland ist ein beliebtes Ziel für Touristen aus aller Welt. Die deutsche Kulturlandschaft ist vielfältig und reich an historischen Stätten, Museen und kulturellen Veranstaltungen. Die deutsche Sprache ist eine wichtige Wissenschaftssprache und wird weltweit an Schulen und Universitäten unterrichtet. Die Bundesrepublik Deutschland ist ein wichtiger Akteur in der internationalen Klimapolitik und setzt sich für den Schutz der Umwelt ein. Die deutsche Wirtschaft ist geprägt von einer starken mittelständischen Struktur und einer hohen Innovationskraft. Die deutsche Gesellschaft ist geprägt von einem hohen Maß an sozialer Sicherheit und einem gut ausgebauten Sozialsystem. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Entwicklungszusammenarbeit und setzt sich für die Bekämpfung von Armut und Ungleichheit ein.

= Conclusion

#set text(font: "Ubuntu Mono")
Die Bundesrepublik Deutschland ist ein föderaler Staat in Mitteleuropa. Sie besteht aus 16 Bundesländern und hat eine parlamentarische Demokratie. Die Hauptstadt und Regierungssitz ist Berlin. Deutschland ist Gründungsmitglied der Europäischen Union und der Eurozone. Die deutsche Wirtschaft ist die größte Volkswirtschaft Europas und die viertgrößte der Welt. Die Bundesrepublik ist ein hochindustrialisiertes Land mit einer starken Exportorientierung. Die wichtigsten Wirtschaftszweige sind die Automobilindustrie, der Maschinenbau, die chemische Industrie und die Elektrotechnik. Deutschland ist bekannt für seine Ingenieurskunst und technologische Innovationen. Die deutsche Sprache ist die am häufigsten gesprochene Muttersprache in der Europäischen Union. Die deutsche Kultur hat eine reiche Tradition in den Bereichen Literatur, Musik, Philosophie und Wissenschaft. Deutschland ist ein wichtiger Akteur in der internationalen Politik und engagiert sich in den Vereinten Nationen, der NATO und anderen internationalen Organisationen. Die deutsche Gesellschaft ist geprägt von Werten wie Toleranz, Meinungsfreiheit und sozialer Gerechtigkeit. Das deutsche Bildungssystem genießt weltweit einen guten Ruf. Die Universitäten und Forschungseinrichtungen in Deutschland sind führend in vielen wissenschaftlichen Disziplinen. Die deutsche Geschichte ist geprägt von bedeutenden Ereignissen wie der Reformation, der Aufklärung und der industriellen Revolution. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Zusammenarbeit und setzt sich für Frieden, Sicherheit und nachhaltige Entwicklung ein. Die deutsche Wirtschaft ist stark von der Globalisierung geprägt und profitiert vom internationalen Handel. Die deutsche Gesellschaft ist multikulturell und weltoffen. Deutschland ist ein beliebtes Ziel für Touristen aus aller Welt. Die deutsche Kulturlandschaft ist vielfältig und reich an historischen Stätten, Museen und kulturellen Veranstaltungen. Die deutsche Sprache ist eine wichtige Wissenschaftssprache und wird weltweit an Schulen und Universitäten unterrichtet. Die Bundesrepublik Deutschland ist ein wichtiger Akteur in der internationalen Klimapolitik und setzt sich für den Schutz der Umwelt ein. Die deutsche Wirtschaft ist geprägt von einer starken mittelständischen Struktur und einer hohen Innovationskraft. Die deutsche Gesellschaft ist geprägt von einem hohen Maß an sozialer Sicherheit und einem gut ausgebauten Sozialsystem. Die Bundesrepublik Deutschland ist ein wichtiger Partner in der internationalen Entwicklungszusammenarbeit und setzt sich für die Bekämpfung von Armut und Ungleichheit ein.

//#bibliography("bibliography.bib")

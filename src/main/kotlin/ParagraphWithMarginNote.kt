import com.itextpdf.kernel.geom.Rectangle
import com.itextpdf.kernel.pdf.PdfPage
import com.itextpdf.layout.Canvas
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.renderer.ParagraphRenderer
import com.itextpdf.layout.renderer.IRenderer
import com.itextpdf.layout.renderer.DrawContext
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.hyphenation.HyphenationConfig

class ParagraphWithMarginNote(
    content: String,
    val marginNote: String? = null
) : Paragraph(content) {

    init {
        this.setNextRenderer(MarginNoteRenderer(this, marginNote))
    }

    class MarginNoteRenderer(
        modelElement: ParagraphWithMarginNote,
        private val note: String?
    ) : ParagraphRenderer(modelElement) {

        override fun draw(drawContext: DrawContext) {
            super.draw(drawContext)

            if (note == null) {
                return
            }

            val pageNumber = occupiedArea.pageNumber
            val page = drawContext.document.getPage(pageNumber)
            val isEven = pageNumber % 2 == 0
            val pageSize = page.pageSize

            val marginX = if (isEven) 36f else pageSize.width - 136f
            val marginY = kotlin.math.min(pageSize.height - 2*132, occupiedArea.bBox.y + occupiedArea.bBox.height - 20f)

            val canvas = Canvas(page, pageSize)
            canvas.add(
                Paragraph(note)
                    .setFontSize(9f)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setHyphenation(HyphenationConfig("de", "DR", 2, 2))
                    .setFixedPosition(pageNumber, marginX, marginY, 100f)
            )
            canvas.close()
        }

        override fun getNextRenderer(): IRenderer {
            return MarginNoteRenderer(modelElement as ParagraphWithMarginNote, note)
        }
    }
}

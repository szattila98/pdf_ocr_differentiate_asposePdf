package ch.dachs.pdf_ocr_differentiate_asposePdf.extraction;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragmentAbsorber;

import ch.dachs.pdf_ocr_differentiate_asposePdf.core.TextLine;
import lombok.RequiredArgsConstructor;

/**
 * Extension of PDFTextStripper. Strips text, word character positions and
 * rendering modes of characters from a page.
 * 
 * @author Szőke Attila
 */
@RequiredArgsConstructor
public class TextLineExtractor {

	private static final int MAX_WORD_OFFSET_Y = 5;
	private static final int MAX_WORD_OFFSET_X = 15;

	private final List<TextLine> pageTextLines;

	public void extract(Page page) throws IOException {
		var absorber = new TextFragmentAbsorber();
		page.accept(absorber);
		var textFragments = absorber.getTextFragments();
		for (var fragment: textFragments) {
			if (!pageTextLines.isEmpty()) {
				var lastLine = pageTextLines.get(pageTextLines.size() - 1);
				var newLine = new TextLine(fragment.getText(), fragment.getRectangle(), fragment.getTextState().getRenderingMode());
				// coupling text that should be one line but pdfbox broke it up
				if (Math.abs(newLine.getYPosition() - lastLine.getYPosition()) < MAX_WORD_OFFSET_Y
						&& newLine.getFirstCharacterXPosition() - lastLine.getLastCharacterXPosition() < MAX_WORD_OFFSET_X) {
					lastLine.concatTextLine(newLine);
				} else {
					pageTextLines.add(new TextLine(fragment.getText(), fragment.getRectangle(), fragment.getTextState().getRenderingMode()));
				}
			} else {
				pageTextLines.add(new TextLine(fragment.getText(), fragment.getRectangle(), fragment.getTextState().getRenderingMode()));
			}
		}
	}
}
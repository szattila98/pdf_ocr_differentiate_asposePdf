package ch.dachs.pdf_ocr_differentiate_asposePdf;

import java.io.IOException;
import java.util.List;

import com.aspose.pdf.Document;
import com.aspose.pdf.Font;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.Position;
import com.aspose.pdf.TextBuilder;
import com.aspose.pdf.TextFormattingOptions;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextParagraph;

/**
 * Writes String results into a PDF file.
 * 
 * @author Szőke Attila
 */
public class ResultWriter {

	private static final String RESULT_PDF_NAME = "pdf_ocr_results.pdf";
	private static final Font FONT_TYPE = FontRepository.findFont("Times New Roman");
	private static final float FONT_SIZE = 12;
	private static final float MARGIN = 72;
	private static final float LEADING = 1.6f * FONT_SIZE;

	/**
	 * Writes a list of strings into a PDF file.
	 * 
	 * @param imageLinesList list of lines per image
	 * @throws IOException thrown when PDF cannot be written
	 */
	public void write(List<List<String>> imageLinesList) throws IOException {
		var evalModeCounter = 0;

		Document doc = new Document();
		for (var pageImageLineList : imageLinesList) {
			var page = doc.getPages().add();
			var builder = new TextBuilder(page);
			double startX = page.getMediaBox().getLLX() + MARGIN;
			double startY = page.getMediaBox().getURY() - MARGIN;
			for (var imageLine : pageImageLineList) {
				var paragraph = new TextParagraph();
				paragraph.setPosition(new Position(startX, startY));
				paragraph.getFormattingOptions().setWrapMode(TextFormattingOptions.WordWrapMode.ByWords);

				var textFragment = new TextFragment(imageLine);
				textFragment.getTextState().setFont(FONT_TYPE);
				textFragment.getTextState().setFontSize(FONT_SIZE);
				paragraph.appendLine(textFragment);

				// Evaluation mode only lets you append 4 paragraphs
				if (evalModeCounter < 4) {
					builder.appendParagraph(paragraph);
					startY = startY - LEADING;
					evalModeCounter++;
				}
			}
		}
		doc.save(RESULT_PDF_NAME);
	}
}
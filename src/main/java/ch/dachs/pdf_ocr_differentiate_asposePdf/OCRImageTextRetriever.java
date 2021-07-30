package ch.dachs.pdf_ocr_differentiate_asposePdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aspose.pdf.Document;
import com.aspose.pdf.TextRenderingMode;

import ch.dachs.pdf_ocr_differentiate_asposePdf.core.ImageInfo;
import ch.dachs.pdf_ocr_differentiate_asposePdf.core.TextLine;
import ch.dachs.pdf_ocr_differentiate_asposePdf.extraction.ImageInfoExtractor;
import ch.dachs.pdf_ocr_differentiate_asposePdf.extraction.TextLineExtractor;

/**
 * Retrieves scanned image text from the document.
 * 
 * @author Szőke Attila
 */
public class OCRImageTextRetriever {

	/**
	 * Retrieves OCR image text from the given document.
	 * 
	 * @param path the PDF doc path
	 * @return the list of text lines
	 * @throws IOException thrown when PDF cannot be processed
	 */
	public List<List<TextLine>> retrieve(String path) throws IOException {
		// open document
		var doc = new Document(path);
		int numberOfPages = doc.getPages().size();
		List<List<TextLine>> documentTextLinesPerImage = new ArrayList<>();
		for (var currentPageNum = 1; currentPageNum < numberOfPages + 1; currentPageNum++) {
			var page = doc.getPages().get_Item(currentPageNum);
			// retrieve images from the page
			var pageImages = new ArrayList<ImageInfo>();
			var imageExtractor = new ImageInfoExtractor(pageImages);
			imageExtractor.extract(page);
			// retrieve text lines only if there is an image on the page
			if (pageImages.isEmpty()) {
				continue;
			}
			var pageTextLines = new ArrayList<TextLine>();
			var textExtractor = new TextLineExtractor(pageTextLines);
			textExtractor.extract(page);
			// only retain text lines which are contained by the image and their first
			// character's rendering mode is invisible
			var imageTextLines = new ArrayList<TextLine>();
			for (var image : pageImages) {
				pageTextLines.stream()
						.filter(textLine -> textIsInImage(image, textLine)
								&& textLine.getRenderingMode() == TextRenderingMode.Invisible)
						.forEach(imageTextLines::add);
			}
			if (imageTextLines.isEmpty()) {
				continue;
			}
			documentTextLinesPerImage.add(imageTextLines);
		}
		return documentTextLinesPerImage;
	}

	/**
	 * Determines whether textLine is in the image or not.
	 * 
	 * @param imageInfo the image
	 * @param textLine  the text line
	 * @return true if line is in the image, otherwise false
	 */
	private boolean textIsInImage(ImageInfo imageInfo, TextLine textLine) {
		return textLine.getFirstCharacterXPosition() > imageInfo.getBottomLeftX()
				&& textLine.getFirstCharacterXPosition() < imageInfo.getTopRightX()
				&& textLine.getLastCharacterXPosition() > imageInfo.getBottomLeftX()
				&& textLine.getLastCharacterXPosition() < imageInfo.getTopRightX()
				&& textLine.getYPosition() > imageInfo.getBottomLeftY()
				&& textLine.getYPosition() < imageInfo.getTopRightY();
	}
}

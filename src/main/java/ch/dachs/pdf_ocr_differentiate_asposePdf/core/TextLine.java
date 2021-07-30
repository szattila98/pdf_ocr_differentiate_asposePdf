package ch.dachs.pdf_ocr_differentiate_asposePdf.core;

import com.aspose.pdf.Rectangle;

import lombok.Data;

/**
 * Represents a line of text stripped from a PDF.
 * 
 * @author Szőke Attila
 */
@Data
public class TextLine {
	private String text;
	private double firstCharacterXPosition;
	private double lastCharacterXPosition;
	private double yPosition;
	private int renderingMode;

	/**
	 * Default constructor of TextLine.
	 * 
	 * @param text          the text itself
	 * @param textRectangle the rectangle of the text fragment
	 * @param renderingMode the text rendering mode
	 */
	public TextLine(String text, Rectangle textRectangle, int renderingMode) {
		this.text = text;
		this.firstCharacterXPosition = textRectangle.getLLX();
		this.lastCharacterXPosition = textRectangle.getLLX() + textRectangle.getWidth();
		this.yPosition = textRectangle.getLLY();
		this.renderingMode = renderingMode;
	}

	/**
	 * Concats another line to this line.
	 * 
	 * @param textLine the line to concat
	 */
	public void concatTextLine(TextLine textLine) {
		this.text = this.text.concat(textLine.getText());
		this.lastCharacterXPosition = textLine.getLastCharacterXPosition();
	}
}

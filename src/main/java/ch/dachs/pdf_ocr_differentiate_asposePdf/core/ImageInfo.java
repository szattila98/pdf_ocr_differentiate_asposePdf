package ch.dachs.pdf_ocr_differentiate_asposePdf.core;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents and image dimensions.
 * 
 * @author Szőke Attila
 */
@Data
@AllArgsConstructor
public class ImageInfo {
	private double bottomLeftX;
	private double bottomLeftY;
	private double topRightX;
	private double topRightY;
}

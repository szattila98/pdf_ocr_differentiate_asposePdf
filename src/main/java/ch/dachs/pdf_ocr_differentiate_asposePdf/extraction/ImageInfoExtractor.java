package ch.dachs.pdf_ocr_differentiate_asposePdf.extraction;

import java.util.List;

import com.aspose.pdf.ImagePlacementAbsorber;
import com.aspose.pdf.Page;

import ch.dachs.pdf_ocr_differentiate_asposePdf.core.ImageInfo;
import lombok.RequiredArgsConstructor;

/**
 * Extracts image information.
 * 
 * @author Szőke Attila
 */
@RequiredArgsConstructor
public class ImageInfoExtractor {

	private final List<ImageInfo> pageImages;

	public void extract(Page page) {
		var absorber = new ImagePlacementAbsorber();
		page.accept(absorber);
		var images = absorber.getImagePlacements();
		for (var image : images) {
			var bottomLeftX = image.getRectangle().getLLX();
			var bottomLeftY = image.getRectangle().getLLY();
			var topRightX = bottomLeftX + image.getRectangle().getWidth();
			var topRightY = bottomLeftY + image.getRectangle().getHeight();
			pageImages.add(new ImageInfo(bottomLeftX, bottomLeftY, topRightX, topRightY));
		}
	}
}

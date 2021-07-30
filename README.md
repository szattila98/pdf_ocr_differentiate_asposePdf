# pdf_ocr_differentiate
A pdf OCR software that differentiates OCR image text from non-OCR text. It uses Aspose.PDF. <br>
The trial version limits collections to the size of 4, so only 4 page, and the first 4 lines get extracted.

## Usage

- Build with

`.\mvnw clean package`

- Run jar with dependencies in target dir. Alternatively run pre-packaged jar in dist dir.

`java -jar pdf_ocr_differentiate_asposePdf-0.0.1-SNAPSHOT-jar-with-dependencies.jar pdf.pdf`

- The result is a PDF doc with the OCR image text.

## Requirements
- Java 16
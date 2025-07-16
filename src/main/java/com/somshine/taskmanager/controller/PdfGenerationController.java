package com.somshine.taskmanager.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/pdf")
public class PdfGenerationController {
    private static final Logger log = LoggerFactory.getLogger(PdfGenerationController.class);

    public PdfGenerationController() {

    }

    @GetMapping("/generateSalarySlip")
    public ResponseEntity<byte[]> generateSalarySlip() {
        try (PDDocument document = new PDDocument()) {
            // Create a new page
            PDPage page = new PDPage();
            document.addPage(page);

//            FontFileFinder fontFinder = new FontFileFinder();
//            List<URI> fontURIs = fontFinder.find();
//
//            File fontFile = null;
//
//            for (URI uri : fontURIs) {
//                File font = new File(uri);
//                if (font.getName().equals("CHILLER.TTF")) {
//                    fontFile = font;
//                }
//            }

            Path fontPath = Paths.get("Documents", "font", "TiroDevanagariMarathi-Regular.ttf");

            PDType0Font marathiFont = PDType0Font.load(document, new File(fontPath.toAbsolutePath().toString()));

            // Create a content stream to add content to the page
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Salary Slip");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText("Employee Name: John Doe");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 630);
                contentStream.showText("Employee ID: 12345");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 610);
                contentStream.showText("Department: Finance");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 590);
                contentStream.showText("Basic Salary: $5000");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 570);
                contentStream.showText("HRA: $2000");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 550);
                contentStream.showText("Other Allowances: $1000");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 530);
                contentStream.showText("Gross Salary: $8000");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 510);
                contentStream.showText("Deductions: $500");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.newLineAtOffset(100, 490);
                contentStream.showText("Net Salary: $7500");
                contentStream.endText();

                String[][] content = {{"a", "b", "1"},
                    {"c", "d", "2"},
                    {"e", "f", "3"},
                    {"g", "h", "4"},
                    {"i", "j", "5"}};

//                PdfUtil.drawTable(page, contentStream, 700.0f, 100.0f, content);

                PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
                float fontSize = 14;
                float fontHeight = fontSize;
                float leading = 20;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
                Date date = new Date();

                float yCordinate = page.getCropBox().getUpperRightY() - 30;
                float startX = page.getCropBox().getLowerLeftX() + 30;
                float endX = page.getCropBox().getUpperRightX() - 30;

                contentStream.beginText();
                contentStream.newLineAtOffset(startX, yCordinate);
                contentStream.setFont(marathiFont, 12);
                contentStream.showText("क्रांतीअग्रणी डॉ. जी. डी. बापू लाड ");
                yCordinate -= fontHeight;  //This line is to track the yCordinate
                contentStream.newLineAtOffset(0, -leading);
                yCordinate -= leading;
                contentStream.showText("Date Generated: " + dateFormat.format(date));
                yCordinate -= fontHeight;
                contentStream.endText(); // End of text mode

                contentStream.moveTo(startX, yCordinate);
                contentStream.lineTo(endX, yCordinate);
                contentStream.stroke();
                yCordinate -= leading;

                contentStream.beginText();
                contentStream.newLineAtOffset(startX, yCordinate);
                contentStream.showText("Name: XXXXX");
                contentStream.endText();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Save the docdocumentPath.ument
            Path documentPath = Paths.get("Documents", "SalarySlip.pdf");
//            log.info("Path :: {}", documentPath.toAbsolutePath());
            document.save(documentPath.toAbsolutePath().toString());

            File pdfFile = new File(documentPath.toAbsolutePath() + "/SalarySlip.pdf");
//            byte[] contents = FileUtils.readFile(new FileInputStream(pdfFile));
            byte[] contents = Files.readAllBytes(documentPath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            // Here you have to set the actual filename of your pdf
            String filename = "output.pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

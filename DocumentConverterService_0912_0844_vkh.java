// 代码生成时间: 2025-09-12 08:44:39
package com.example.converter;

import io.quarkus.runtime.Quarkus;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.OpcPackage;
import org.docx4j.Docx4JException;
import org.docx4j.convert.in.Docx4jInput;
import org.docx4j.convert.out.pdf.PdfExport;

/**
 * A service for converting document formats using Quarkus framework.
 */
public class DocumentConverterService {

    // Converts a Word document (.docx) to a PDF.
    public void convertDocxToPdf(String inputPath, String outputPath) throws IOException, Docx4JException {
        try (OpcPackage p = WordprocessingMLPackage.load(new File(inputPath))) {
            Docx4jInput docx4jInput = Docx4jInput.fromDocx(p);
            PdfExport pdfExport = Docx4j.createExport(docx4jInput);
            pdfExport.to(new File(outputPath), PdfExport.PDF_EXPORT_TYPE_PDFA_1B);
        }
    }

    // Converts a Word document (.doc) to a PDF using Apache POI.
    public void convertDocToPdf(String inputPath, String outputPath) throws IOException {
        try (XWPFDocument document = new XWPFDocument(Files.newInputStream(Paths.get(inputPath)))) {
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, Files.newOutputStream(Paths.get(outputPath)), options);
        }
    }

    // Main method for testing the conversion service.
    public static void main(String[] args) {
        DocumentConverterService service = new DocumentConverterService();
        try {
            // Convert a .docx file to PDF.
            service.convertDocxToPdf("input.docx", "output.docx.pdf");

            // Convert a .doc file to PDF.
            service.convertDocToPdf("input.doc", "output.doc.pdf");
        } catch (IOException | Docx4JException e) {
            e.printStackTrace();
            Quarkus.log.error("Error during document conversion", e);
        }
    }
}

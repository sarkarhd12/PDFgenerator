


package com.pdfgenerator.pdfgn.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdfgenerator.pdfgn.model.InvoiceRequest;
import com.pdfgenerator.pdfgn.model.Item;
import com.pdfgenerator.pdfgn.response.PdfResponse;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    private static final String PDF_STORAGE = "./invoices/";

    public PdfResponse generatePdf(InvoiceRequest invoiceRequest) throws DocumentException, IOException {
        System.out.println("Generating PDF for: " + invoiceRequest); // Debugging line

        String fileName = generateFileName(invoiceRequest);
        String filePath = PDF_STORAGE + fileName;

        // Check if the file already exists
        if (new File(filePath).exists()) {
            return new PdfResponse("PDF already exists.", filePath);
        }

        Document document = new Document();
        FileOutputStream fos = null; // Declare FileOutputStream here

        try {
            fos = new FileOutputStream(filePath); // Initialize FileOutputStream
            PdfWriter.getInstance(document, fos);
            document.open();

            // Create a table for seller and buyer information
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10f);
            infoTable.setSpacingAfter(10f);

            // Seller information
            infoTable.addCell("Seller:");
            infoTable.addCell("Buyer:");

            infoTable.addCell("Name: " + invoiceRequest.getSeller() + "\n" +
                    "GSTIN: " + invoiceRequest.getSellerGstin() + "\n" +
                    "Address: " + invoiceRequest.getSellerAddress());
            infoTable.addCell("Name: " + invoiceRequest.getBuyer() + "\n" +
                    "GSTIN: " + invoiceRequest.getBuyerGstin() + "\n" +
                    "Address: " + invoiceRequest.getBuyerAddress());

            // Add the seller and buyer info table to the document
            document.add(infoTable);

            // Create a table for item details
            PdfPTable itemTable = new PdfPTable(4);
            itemTable.setWidthPercentage(100);
            itemTable.setSpacingBefore(10f);
            itemTable.setSpacingAfter(10f);

            // Add table headers
            itemTable.addCell("Item");
            itemTable.addCell("Quantity");
            itemTable.addCell("Rate");
            itemTable.addCell("Amount");

            // Add item data to the table
            for (Item item : invoiceRequest.getItems()) {
                itemTable.addCell(item.getName());
                itemTable.addCell(String.valueOf(item.getQuantity()));
                itemTable.addCell(String.valueOf(item.getRate()));
                itemTable.addCell(String.valueOf(item.getAmount()));
            }

            // Add the item table to the document
            document.add(itemTable);
        } catch (IOException e) {
            throw new IOException("Error writing PDF file: " + e.getMessage(), e);
        } finally {
            // Close resources properly
            if (document.isOpen()) {
                document.close();
            }
            if (fos != null) {
                try {
                    fos.close(); // Close the FileOutputStream
                } catch (IOException e) {
                    System.err.println("Failed to close FileOutputStream: " + e.getMessage());
                }
            }
        }

        System.out.println("PDF generated and stored: " + filePath);
        return new PdfResponse("PDF generated successfully.", filePath);
    }

    private String generateFileName(InvoiceRequest invoiceRequest) {
        String rawData = invoiceRequest.getSeller() + invoiceRequest.getSellerGstin() +
                invoiceRequest.getBuyer() + invoiceRequest.getBuyerGstin() +
                invoiceRequest.getItems().toString();
        String hash = DigestUtils.md5Hex(rawData);
        return "invoice_" + hash + ".pdf";
    }
}

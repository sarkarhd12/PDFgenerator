package com.pdfgenerator.pdfgn.service;

import com.pdfgenerator.pdfgn.model.InvoiceRequest;
import com.pdfgenerator.pdfgn.model.Item;
import com.pdfgenerator.pdfgn.response.PdfResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals; // Import this for assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue;   // Import this for assertTrue

class PdfServiceTest {
    private PdfService pdfService;

    @BeforeEach
    void setUp() {
        pdfService = new PdfService();
    }

    @AfterEach
    void tearDown() {

        File dir = new File("./invoices/");
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete();
            }
        }
    }

    @Test
    void testGeneratePdfSuccessfully() throws Exception {

        List<Item> items = new ArrayList<>();
        items.add(new Item("Item 1", "1", 100.0, 100.0));
        items.add(new Item("Item 2", "2", 150.0, 300.0));

        InvoiceRequest invoiceRequest = new InvoiceRequest(
                "Seller Name", "GSTIN123456", "Seller Address",
                "Buyer Name", "GSTIN654321", "Buyer Address", items
        );


        PdfResponse response = pdfService.generatePdf(invoiceRequest);


        assertEquals("PDF generated successfully.", response.getMessage());
        assertTrue(new File(response.getFilePath()).exists());
    }

    @Test
    void testGeneratePdfFileAlreadyExists() throws Exception {

        List<Item> items = new ArrayList<>();
        items.add(new Item("Item 1", "1", 100.0, 100.0));

        InvoiceRequest invoiceRequest = new InvoiceRequest(
                "Seller Name", "GSTIN123456", "Seller Address",
                "Buyer Name", "GSTIN654321", "Buyer Address", items
        );


        pdfService.generatePdf(invoiceRequest);


        PdfResponse response = pdfService.generatePdf(invoiceRequest);


        assertEquals("PDF already exists.", response.getMessage());
    }
}

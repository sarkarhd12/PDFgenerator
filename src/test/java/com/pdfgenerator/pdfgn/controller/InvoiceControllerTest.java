package com.pdfgenerator.pdfgn.controller;

import com.pdfgenerator.pdfgn.model.InvoiceRequest;
import com.pdfgenerator.pdfgn.response.PdfResponse;
import com.pdfgenerator.pdfgn.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class InvoiceControllerTest {

    @InjectMocks
    private InvoiceController invoiceController;

    @Mock
    private PdfService pdfService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateInvoiceSuccess() throws Exception {

        InvoiceRequest invoiceRequest = new InvoiceRequest(
                "Seller Name", "GSTIN123456", "Seller Address",
                "Buyer Name", "GSTIN654321", "Buyer Address", new ArrayList<>()
        );


        when(pdfService.generatePdf(any(InvoiceRequest.class)))
                .thenReturn(new PdfResponse("PDF generated successfully.", "some/path"));


        ResponseEntity<PdfResponse> response = invoiceController.generateInvoice(invoiceRequest);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("PDF generated successfully.", response.getBody().getMessage());
    }

    @Test
    void testGenerateInvoiceFailure() throws Exception {

        InvoiceRequest invoiceRequest = new InvoiceRequest(
                "Seller Name", "GSTIN123456", "Seller Address",
                "Buyer Name", "GSTIN654321", "Buyer Address", new ArrayList<>()
        );


        when(pdfService.generatePdf(any(InvoiceRequest.class)))
                .thenThrow(new RuntimeException("Failed to generate PDF"));


        ResponseEntity<PdfResponse> response = invoiceController.generateInvoice(invoiceRequest);


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An unexpected error occurred: Failed to generate PDF", response.getBody().getMessage());
    }
}

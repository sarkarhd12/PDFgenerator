package com.pdfgenerator.pdfgn.controller;

import com.pdfgenerator.pdfgn.model.InvoiceRequest;
import com.pdfgenerator.pdfgn.response.PdfResponse;
import com.pdfgenerator.pdfgn.service.PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final PdfService pdfService;

    public InvoiceController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/generate")
    public ResponseEntity<PdfResponse> generateInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            PdfResponse response = pdfService.generatePdf(invoiceRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(new PdfResponse("Failed to generate PDF: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new PdfResponse("An unexpected error occurred: " + e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

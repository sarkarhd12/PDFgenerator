package com.pdfgenerator.pdfgn.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PdfResponse {
    private String message;
    private String filePath;
}

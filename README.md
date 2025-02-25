# PDFgenerator

PDF Invoice Generator API
Overview

This project is a REST-based PDF invoice generation service. It accepts invoice details, including seller, buyer, and item information, via API and generates a downloadable PDF invoice. The solution is testable via Postman/Swagger and is developed using Test-Driven Development (TDD) principles.
Features

    Generate a PDF invoice by providing seller, buyer, and item details.
    RESTful APIs to create and retrieve PDF invoices.
    Test cases implemented using TDD methodology.
    The project is structured and modular, ensuring easy scalability and maintenance.
    The service checks for the existence of an invoice file before generating a new one to prevent duplicates.

Project Structure

plaintext

pdfgn
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com.pdfgenerator.pdfgn
│   │   │   │   ├── controller
│   │   │   │   │   └── InvoiceController.java
│   │   │   │   ├── model
│   │   │   │   │   ├── InvoiceRequest.java
│   │   │   │   │   ├── Item.java
│   │   │   │   ├── service
│   │   │   │   │   └── PdfService.java
│   │   │   │   ├── response
│   │   │   │   │   └── PdfResponse.java
│   ├── test
│   │   ├── java
│   │   │   ├── com.pdfgenerator.pdfgn
│   │   │   │   ├── controller
│   │   │   │   │   └── InvoiceControllerTest.java
│   │   │   │   ├── service
│   │   │   │   │   └── PdfServiceTest.java
│   └── resources
├── invoices (generated PDF files are saved here)
└── pom.xml (Maven dependencies and plugins)

Requirements

    Java 11+
    Spring Boot
    Maven
    iText (for PDF generation)
    JUnit 5 (for test cases)
    Mockito (for service mocking)

Setup Instructions

    Clone the repository:

    bash

git clone https://github.com/your-repo/pdf-generator.git

Navigate to the project directory:

bash

cd pdf-generator

Build the project:

bash

mvn clean install

Run the application:

bash

    mvn spring-boot:run

    Access the REST APIs via Postman or Swagger.

API Documentation
1. Generate PDF Invoice

Endpoint: /api/invoices/generate
Method: POST
Description: Generate a PDF invoice based on provided invoice data.
Request Body:

json

{
  "seller": "Seller Name",
  "sellerGstin": "GSTIN123456",
  "sellerAddress": "Seller Address",
  "buyer": "Buyer Name",
  "buyerGstin": "GSTIN654321",
  "buyerAddress": "Buyer Address",
  "items": [
    {
      "name": "Item 1",
      "quantity": 1,
      "rate": 100.0,
      "amount": 100.0
    },
    {
      "name": "Item 2",
      "quantity": 2,
      "rate": 150.0,
      "amount": 300.0
    }
  ]
}

Responses:

    200 OK:

    json

{
  "message": "PDF generated successfully.",
  "filePath": "./invoices/invoice_abcdef123.pdf"
}

500 Internal Server Error:

json

    {
      "message": "An unexpected error occurred: [Error Details]",
      "filePath": null
    }

Testing with Postman

    Open Postman.
    Set the request type to POST.
    Enter the API URL: http://localhost:8080/api/invoices/generate.
    Add a JSON request body as shown above.
    Send the request.
    You should receive a JSON response with the message and filePath for the generated PDF.

Unit Tests

This project follows TDD (Test-Driven Development) and includes unit tests for both the service and controller.

    Run Unit Tests:

    bash

    mvn test

Unit test files are located under src/test/java/com/pdfgenerator/pdfgn.
Test Case Examples:

    InvoiceControllerTest
        testGenerateInvoiceSuccess: Tests successful invoice generation via the API.
        testGenerateInvoiceFailure: Simulates and tests failure scenarios for invoice generation.

    PdfServiceTest
        testGeneratePdfSuccessfully: Tests that the PDF is generated successfully.
        testGeneratePdfFileAlreadyExists: Tests that the system correctly handles file duplication.
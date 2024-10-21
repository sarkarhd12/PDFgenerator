package com.pdfgenerator.pdfgn.model;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceRequest {
    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;
    private List<Item> items;

    public InvoiceRequest(String seller, String sellerGstin, String sellerAddress, String buyer, String buyerGstin, String buyerAddress, List<Item> items) {
        this.seller = seller;
        this.sellerGstin = sellerGstin;
        this.sellerAddress = sellerAddress;
        this.buyer = buyer;
        this.buyerGstin = buyerGstin;
        this.buyerAddress = buyerAddress;
        this.items = items;
    }
}

package com.pdfgenerator.pdfgn.model;

import lombok.Data;

@Data
public class Item {
    private String name;
    private String quantity;
    private double rate;
    private double amount;

    public Item(String name, String quantity, double rate, double amount) {
        this.name = name;
        this.quantity = quantity;
        this.rate = rate;
        this.amount = amount;
    }
}

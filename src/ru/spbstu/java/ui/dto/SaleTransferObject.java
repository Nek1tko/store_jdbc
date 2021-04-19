package ru.spbstu.java.ui.dto;

import java.util.Date;

public class SaleTransferObject {
    private String name;
    private Integer quantity;
    private Double amount;
    private Date date;

    public SaleTransferObject(String name, Integer quantity, Double amount, Date date) {
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}

package ru.spbstu.java.server.entity;

import java.sql.Date;

public class Sale {
    private Long id;
    private Double amount;
    private Integer quantity;
    private Date date;
    private String name;
    private Long warehouseId;

    public Sale(Long id, Double amount, Integer quantity, Date date, String name, Long warehouseId) {
        this.id = id;
        this.amount = amount;
        this.quantity = quantity;
        this.date = date;
        this.name = name;
        this.warehouseId = warehouseId;
    }

    public Sale() {
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}

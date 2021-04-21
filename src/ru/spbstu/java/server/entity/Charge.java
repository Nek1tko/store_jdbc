package ru.spbstu.java.server.entity;

import java.sql.Date;

public class Charge {
    private Long id;
    private Double amount;
    private Date date;
    private Long expenseItemId;
    private String name;

    public Charge(Long id, Double amount, Date date, Long expenseItemId, String name) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.expenseItemId = expenseItemId;
        this.name = name;
    }

    public Charge() {
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getExpenseItemId() {
        return expenseItemId;
    }

    public void setExpenseItemId(Long expenseItemId) {
        this.expenseItemId = expenseItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

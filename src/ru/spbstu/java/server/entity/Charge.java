package ru.spbstu.java.server.entity;

import java.util.Date;

public class Charge {
    private Long id;
    private Double amount;
    private Date date;
    private ExpenseItem expenseItem;

    public Charge(Long id, Double amount, Date date, ExpenseItem expenseItem) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.expenseItem = expenseItem;
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

    public ExpenseItem getExpenseItem() {
        return expenseItem;
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

    public void setExpenseItem(ExpenseItem expenseItem) {
        this.expenseItem = expenseItem;
    }
}

package ru.spbstu.java.server.entity;

public class Warehouse {
    private Long id;
    private String name;
    private Integer quantity;
    private Double amount;

    public Warehouse() {
    }
    public Warehouse(Long id, String name, Integer quantity, Double amount) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}

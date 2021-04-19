package ru.spbstu.java.server.builder;

import ru.spbstu.java.server.entity.Sale;

import java.sql.Date;

public class SaleBuilder implements Builder<Sale> {
    private Sale sale;

    public SaleBuilder() {
        this.sale = new Sale();
    }

    @Override
    public Sale build() {
        return sale;
    }

    public SaleBuilder id(Long id) {
        sale.setId(id);
        return this;
    }

    public SaleBuilder amount(Double amount) {
        sale.setAmount(amount);
        return this;
    }

    public SaleBuilder quantity(Integer quantity) {
        sale.setQuantity(quantity);
        return this;
    }

    public SaleBuilder date(Date date) {
        sale.setDate(date);
        return this;
    }

    public SaleBuilder name(String name) {
        sale.setName(name);
        return this;
    }

    public SaleBuilder warehouseId(Long id) {
        sale.setWarehouseId(id);
        return this;
    }
}

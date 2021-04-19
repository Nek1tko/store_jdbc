package ru.spbstu.java.server.builder;

import ru.spbstu.java.server.entity.Warehouse;

public class WarehouseBuilder implements Builder<Warehouse> {
    private Warehouse warehouse;

    public WarehouseBuilder() {
        this.warehouse = new Warehouse();
    }

    @Override
    public Warehouse build() {
        return warehouse;
    }

    public WarehouseBuilder id(Long id) {
        warehouse.setId(id);
        return this;
    }

    public WarehouseBuilder name(String name) {
        warehouse.setName(name);
        return this;
    }

    public WarehouseBuilder quantity(Integer quantity) {
        warehouse.setQuantity(quantity);
        return this;
    }

    public WarehouseBuilder amount(Double amount) {
        warehouse.setAmount(amount);
        return this;
    }
}

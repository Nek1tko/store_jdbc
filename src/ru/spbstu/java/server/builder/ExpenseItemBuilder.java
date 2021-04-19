package ru.spbstu.java.server.builder;

import ru.spbstu.java.server.entity.ExpenseItem;

public class ExpenseItemBuilder implements Builder<ExpenseItem> {
    private ExpenseItem expenseItem;

    public ExpenseItemBuilder() {
        this.expenseItem = new ExpenseItem();
    }

    @Override
    public ExpenseItem build() {
        return expenseItem;
    }

    public ExpenseItemBuilder id(Long id) {
        expenseItem.setId(id);
        return this;
    }

    public ExpenseItemBuilder name(String name) {
        expenseItem.setName(name);
        return this;
    }
}

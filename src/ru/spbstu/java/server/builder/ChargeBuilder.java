package ru.spbstu.java.server.builder;

import ru.spbstu.java.server.entity.Charge;
import ru.spbstu.java.server.entity.ExpenseItem;

import java.sql.Date;

public class ChargeBuilder implements Builder<Charge> {
    private Charge charge;

    public ChargeBuilder() {
        this.charge = new Charge();
    }

    @Override
    public Charge build() {
        return charge;
    }

    public ChargeBuilder id(Long id) {
        charge.setId(id);
        return this;
    }

    public ChargeBuilder amount(Double amount) {
        charge.setAmount(amount);
        return this;
    }

    public ChargeBuilder date(Date date) {
        charge.setDate(date);
        return this;
    }

    public ChargeBuilder expenseItemId(Long expenseItemId) {
        charge.setExpenseItemId(expenseItemId);
        return this;
    }

    public ChargeBuilder name(String name) {
        charge.setName(name);
        return this;
    }
}

package ru.spbstu.java.server.builder;

import ru.spbstu.java.server.entity.Charge;
import ru.spbstu.java.server.entity.ExpenseItem;

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

    public ChargeBuilder expenseItem(ExpenseItem expenseItem) {
        charge.setExpenseItem(expenseItem);
        return this;
    }
}

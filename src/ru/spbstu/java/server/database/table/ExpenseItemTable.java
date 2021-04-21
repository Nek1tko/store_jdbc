package ru.spbstu.java.server.database.table;

import ru.spbstu.java.server.builder.ExpenseItemBuilder;
import ru.spbstu.java.server.builder.WarehouseBuilder;
import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.entity.ExpenseItem;
import ru.spbstu.java.server.entity.Warehouse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ExpenseItemTable implements Table<ExpenseItem>{
    private final DBConnection dbConnection;

    public ExpenseItemTable(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<ExpenseItem> get() throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "SELECT id, name FROM EXPENSE_ITEMS";
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    List<ExpenseItem> list = new LinkedList<>();
                    while (resultSet.next()) {
                        list.add(new ExpenseItemBuilder()
                                .id(resultSet.getLong("id"))
                                .name(resultSet.getString("name"))
                                .build()
                        );
                    }
                    return list;
                }
            }
        }
    }

    @Override
    public void insert(ExpenseItem entity) {

    }

    @Override
    public void update(ExpenseItem entity) {

    }

    @Override
    public void delete(Long id) {

    }
}

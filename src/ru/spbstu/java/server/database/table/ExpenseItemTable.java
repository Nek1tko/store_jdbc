package ru.spbstu.java.server.database.table;

import ru.spbstu.java.server.builder.ExpenseItemBuilder;
import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.entity.ExpenseItem;

import java.sql.*;
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
    public void insert(ExpenseItem entity) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO EXPENSE_ITEMS (NAME) VALUES (?)",
                    new String[]{"ID"})) {
                statement.setString(1, entity.getName());
                statement.executeUpdate();
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    }
                }
            }
        }
    }

    @Override
    public void update(ExpenseItem entity) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE EXPENSE_ITEMS SET NAME = ? " +
                    "WHERE ID = ?")
            ) {
                statement.setString(1, entity.getName());
                statement.setLong(2, entity.getId());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "DELETE FROM EXPENSE_ITEMS WHERE ID = " + id;
                statement.execute(sql);
            }
        }
    }
}

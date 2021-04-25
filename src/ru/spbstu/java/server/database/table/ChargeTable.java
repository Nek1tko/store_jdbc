package ru.spbstu.java.server.database.table;

import ru.spbstu.java.server.builder.ChargeBuilder;
import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.entity.Charge;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ChargeTable implements Table<Charge>{
    private final DBConnection dbConnection;

    public ChargeTable(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Charge> get() throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "SELECT CHARGES.ID as ID, AMOUNT, CHARGE_DATE, " +
                        "E.ID as EXPENSE_ITEM_ID, NAME " +
                        "FROM CHARGES JOIN EXPENSE_ITEMS E " +
                        "ON CHARGES.EXPENSE_ITEM_ID = E.ID";
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    List<Charge> list = new LinkedList<>();
                    while (resultSet.next()) {
                        list.add(new ChargeBuilder()
                                .id(resultSet.getLong("id"))
                                .amount(resultSet.getDouble("amount"))
                                .date(resultSet.getDate("charge_date"))
                                .name(resultSet.getString("name"))
                                .expenseItemId(resultSet.getLong("expense_item_id"))
                                .build()
                        );
                    }
                    return list;
                }
            }
        }
    }

    @Override
    public void insert(Charge entity) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO CHARGES (AMOUNT, CHARGE_DATE, EXPENSE_ITEM_ID) VALUES (?, ?, ?)",
                    new String[]{"ID"})) {
                statement.setDouble(1, entity.getAmount());
                statement.setDate(2, entity.getDate());
                statement.setLong(3, entity.getExpenseItemId());
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
    public void update(Charge entity) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE CHARGES SET AMOUNT = ?," +
                    " CHARGE_DATE = ?, EXPENSE_ITEM_ID = ? WHERE ID = ?")
            ) {
                statement.setDouble(1, entity.getAmount());
                statement.setDate(2, entity.getDate());
                statement.setLong(3, entity.getExpenseItemId());
                statement.setLong(4, entity.getId());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "DELETE FROM CHARGES WHERE ID = " + id;
                statement.execute(sql);
            }
        }
    }
}

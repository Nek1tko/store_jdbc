package ru.spbstu.java.server.database.table;

import ru.spbstu.java.server.builder.SaleBuilder;
import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.entity.Sale;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SaleTable implements Table<Sale> {
    private final DBConnection dbConnection;

    public SaleTable(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Sale> get() throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "SELECT sales.id as id, sales.amount, sales.quantity, " +
                        "sale_date, name, w.id as warehouse_id " +
                        "FROM SALES JOIN WAREHOUSES W " +
                        "ON SALES.WAREHOUSE_ID = W.ID";
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    List<Sale> list = new LinkedList<>();
                    while (resultSet.next()) {
                        list.add(new SaleBuilder()
                                .id(resultSet.getLong("id"))
                                .quantity(resultSet.getInt("quantity"))
                                .amount(resultSet.getDouble("amount"))
                                .date(resultSet.getDate("sale_date"))
                                .name(resultSet.getString("name"))
                                .warehouseId(resultSet.getLong("warehouse_id"))
                                .build()
                        );
                    }
                    return list;
                }
            }
        }
    }

    @Override
    public void insert(Sale entity) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO SALES (AMOUNT, QUANTITY, SALE_DATE, WAREHOUSE_ID) VALUES (?, ?, ?, ?)",
                    new String[]{"ID"})) {
                statement.setDouble(1, entity.getAmount());
                statement.setInt(2, entity.getQuantity());
                statement.setDate(3, entity.getDate());
                statement.setLong(4, entity.getWarehouseId());
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
    public void update(Sale entity) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE SALES SET AMOUNT = ?," +
                    " QUANTITY = ?, SALE_DATE = ?, WAREHOUSE_ID = ? WHERE ID = ?")
            ) {
                statement.setDouble(1, entity.getAmount());
                statement.setInt(2, entity.getQuantity());
                statement.setDate(3, entity.getDate());
                statement.setLong(4, entity.getWarehouseId());
                statement.setLong(5, entity.getId());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "DELETE FROM SALES WHERE ID = " + id;
                statement.execute(sql);
            }
        }
    }
}

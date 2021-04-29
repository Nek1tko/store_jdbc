package ru.spbstu.java.server.database.table;

import ru.spbstu.java.server.builder.WarehouseBuilder;
import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.entity.Warehouse;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class WarehouseTable implements Table<Warehouse> {
    private final DBConnection dbConnection;

    public WarehouseTable(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Warehouse> get() throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "SELECT * FROM Warehouses";
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    List<Warehouse> list = new LinkedList<>();
                    while (resultSet.next()) {
                        list.add(new WarehouseBuilder()
                                .id(resultSet.getLong("id"))
                                .name(resultSet.getString("name"))
                                .quantity(resultSet.getInt("quantity"))
                                .amount(resultSet.getDouble("amount"))
                                .build()
                        );
                    }
                    return list;
                }
            }
        }
    }

    @Override
    public void insert(Warehouse entity) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO WAREHOUSES (NAME, QUANTITY, AMOUNT) VALUES (?, ?, ?)",
                    new String[]{"ID"})) {
                statement.setString(1, entity.getName());
                statement.setInt(2, entity.getQuantity());
                statement.setDouble(3, entity.getAmount());
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
    public void update(Warehouse entity) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE WAREHOUSES SET  NAME = ?, " +
                    "AMOUNT = ?, QUANTITY = ? WHERE ID = ?")
            ) {
                statement.setString(1, entity.getName());
                statement.setDouble(2, entity.getAmount());
                statement.setInt(3, entity.getQuantity());
                statement.setLong(4, entity.getId());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "DELETE FROM WAREHOUSES WHERE ID = " + id;
                statement.execute(sql);
            }
        }
    }
}

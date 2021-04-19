package ru.spbstu.java.server.database.table;

import ru.spbstu.java.server.builder.WarehouseBuilder;
import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.entity.Warehouse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class WarehouseTable implements Table<Warehouse> {
    private final DBConnection dbConnection;

    public WarehouseTable(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Warehouse> get() {
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
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
            throwable.printStackTrace();
            return new LinkedList<>();
        }
    }

    @Override
    public void insert(Warehouse entity) {

    }

    @Override
    public void update(Warehouse entity) {

    }

    @Override
    public void delete(Long id) {

    }
}

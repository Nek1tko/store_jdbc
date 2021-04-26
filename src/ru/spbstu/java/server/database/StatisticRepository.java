package ru.spbstu.java.server.database;

import ru.spbstu.java.server.builder.SaleBuilder;
import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.entity.Sale;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StatisticRepository {
    private DBConnection dbConnection;

    public StatisticRepository(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public List<Sale> getTop5Items(Date startDate, Date endDate) throws SQLException {
        try (Connection connection = dbConnection.connect()) {
            try (Statement statement = connection.createStatement()) {
                String sql = "SELECT NAME, SUM(SALES.AMOUNT) AS AMOUNT " +
                        "FROM SALES " +
                        "JOIN WAREHOUSES on WAREHOUSES.ID = SALES.WAREHOUSE_ID " +
                        "WHERE SALE_DATE BETWEEN " + startDate + " AND " + endDate + " " +
                        "GROUP BY NAME " +
                        "ORDER BY AMOUNT DESC " +
                        "FETCH FIRST 5 ROWS ONLY";
                ResultSet resultSet = statement.executeQuery(sql);
                List<Sale> salesList = new LinkedList<>();
                while (resultSet.next()) {
                    salesList.add(new SaleBuilder()
                            .name(resultSet.getString("name"))
                            .amount(resultSet.getDouble("amount"))
                            .build()
                    );
                }
                return salesList;
            }
        }
    }

        public Double getMarginForMonth(Date date) throws SQLException {
            try (Connection connection = dbConnection.connect()) {
                try (Statement statement = connection.createStatement()) {
                    Timestamp timestamp = new Timestamp(date.getTime());
                    String sql = "SELECT (INCOME.AMOUNT - OUTCOME.AMOUNT) AS MARGIN " +
                            "FROM (SELECT sum(AMOUNT) AS AMOUNT " +
                            "FROM SALES " +
                            "WHERE SALE_DATE BETWEEN add_months(trunc(TIMESTAMP '" + timestamp + "', 'MONTH'), -1) " +
                            "AND " +
                            "trunc(TIMESTAMP '" + timestamp + "', 'MONTH')) INCOME, " +
                            "(SELECT SUM(AMOUNT) AS AMOUNT " +
                            "FROM CHARGES " +
                            "WHERE CHARGE_DATE BETWEEN add_months(trunc(TIMESTAMP '" + timestamp + "', 'MONTH'), -1) " +
                            "AND " +
                            "trunc(TIMESTAMP '" + timestamp + "', 'MONTH')) OUTCOME";
                    ResultSet resultSet = statement.executeQuery(sql);
                    Double margin = 0.0;
                    if (resultSet.next()) {
                        margin = resultSet.getDouble("margin");
                    }
                    return margin;
                }
            }
        }
    }

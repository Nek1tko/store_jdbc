package ru.spbstu.java.server.database.table;

import ru.spbstu.java.server.builder.ChargeBuilder;
import ru.spbstu.java.server.builder.SaleBuilder;
import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.entity.Charge;
import ru.spbstu.java.server.entity.Sale;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void insert(Charge entity) {

    }

    @Override
    public void update(Charge entity) {

    }

    @Override
    public void delete(Long id) {

    }
}

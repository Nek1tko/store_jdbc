package ru.spbstu.java.server.database;

import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.database.table.ChargeTable;
import ru.spbstu.java.server.database.table.ExpenseItemTable;
import ru.spbstu.java.server.database.table.SaleTable;
import ru.spbstu.java.server.database.table.WarehouseTable;
import ru.spbstu.java.server.entity.Charge;
import ru.spbstu.java.server.entity.ExpenseItem;
import ru.spbstu.java.server.entity.Sale;
import ru.spbstu.java.server.entity.Warehouse;
import ru.spbstu.java.server.exception.SingletonException;

import java.sql.SQLException;
import java.util.List;


public class DataBase {
    private final WarehouseTable warehouseTable;
    private final SaleTable saleTable;
    private final ExpenseItemTable expenseItemTable;
    private final ChargeTable chargeTable;
    private static DataBase dataBase;

    public synchronized static void init(DBConnection dbConnection) {
        if (dataBase == null) {
            dataBase = new DataBase(dbConnection);
        }
    }

    public static DataBase instance() {
        if (dataBase != null) {
            return dataBase;
        }
        else {
            throw new SingletonException("Invoke method init before getting instance");
        }
    }
    protected DataBase(DBConnection dbConnection) {
        this.warehouseTable = new WarehouseTable(dbConnection);
        this.saleTable = new SaleTable(dbConnection);
        this.expenseItemTable = new ExpenseItemTable(dbConnection);
        this.chargeTable = new ChargeTable(dbConnection);
    }

    public List<Warehouse> getWarehouses() throws SQLException {
        return warehouseTable.get();
    }

    public List<ExpenseItem> getExpenseItems() throws SQLException {
        return expenseItemTable.get();
    }

    public List<Sale> getSales() throws SQLException {
        return saleTable.get();
    }

    public Sale addSale(Sale sale) {
        saleTable.insert(sale);
        return sale;
    }

    public Sale updateSale(Sale sale) {
        saleTable.update(sale);
        return sale;
    }

    public void deleteSale(Long id) {
        saleTable.delete(id);
    }

    public List<Charge> getCharges() throws SQLException {
        return chargeTable.get();
    }
}

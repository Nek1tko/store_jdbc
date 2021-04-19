package ru.spbstu.java.server.database;

import ru.spbstu.java.server.connection.DBConnection;
import ru.spbstu.java.server.database.table.SaleTable;
import ru.spbstu.java.server.database.table.WarehouseTable;
import ru.spbstu.java.server.entity.Sale;
import ru.spbstu.java.server.entity.Warehouse;
import ru.spbstu.java.server.exception.SingletonException;

import java.util.List;


public class DataBase {
    private WarehouseTable warehouseTable;
    private SaleTable saleTable;
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
    }

    public List<Warehouse> getWarehouses() {
        return warehouseTable.get();
    }

    public List<Sale> getSales() {
        return saleTable.get();
    }

    public Sale addSale(Sale sale) {
        saleTable.insert(sale);
        sale.setId(10L);
        return sale;
    }
}

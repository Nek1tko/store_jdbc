package ru.spbstu.java.ui.controller.tables;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.spbstu.java.Client;
import ru.spbstu.java.server.builder.SaleBuilder;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.server.entity.Sale;
import ru.spbstu.java.server.entity.Warehouse;
import ru.spbstu.java.ui.error.AlertWindow;
import ru.spbstu.java.ui.filter.TextFieldFilters;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SalesController implements Initializable {
    private DataBase dataBase;
    private Map<String, Long> warehouses;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addSaleButton;

    @FXML
    private Button updateSaleButton;

    @FXML
    private Button deleteSaleButton;

    @FXML
    private ComboBox<String> saleStuffName;

    @FXML
    private TableView<Sale> salesTable;

    @FXML
    private TableColumn<Sale, String> saleName;

    @FXML
    private TableColumn<Sale, Double> saleQuantity;

    @FXML
    private TableColumn<Sale, Integer> saleAmount;

    @FXML
    private TableColumn<Sale, Date> saleDate;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField amountField;

    @FXML
    private DatePicker dateField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataBase = ru.spbstu.java.server.database.DataBase.instance();

        amountField.setTextFormatter(new TextFormatter<>(TextFieldFilters.getDoubleFilter()));
        quantityField.setTextFormatter(new TextFormatter<>(TextFieldFilters.getIntegerFilter()));

        salesTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Sale>) change -> {
            Sale sale = salesTable.getSelectionModel().getSelectedItem();
            if (sale != null) {
                saleStuffName.setValue(sale.getName());
                quantityField.setText(sale.getQuantity().toString());
                amountField.setText(sale.getAmount().toString());
                dateField.setValue(sale.getDate().toLocalDate());
                cancelButton.setDisable(false);
                deleteSaleButton.setDisable(false);
                updateSaleButton.setDisable(false);
                updateSaleButton.setVisible(true);
                addSaleButton.setDisable(true);
                addSaleButton.setVisible(false);
            }
        });
        dateField.setValue(LocalDate.now());
        saleName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        saleQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        saleAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        saleDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

        try {
            List<Warehouse> list = getWarehouses();
            for (Warehouse el : list) {
                saleStuffName.getItems().add(el.getName());
            }
            warehouses = list.stream().collect(Collectors.toMap(Warehouse::getName, Warehouse::getId));


            ObservableList<Sale> listSaleTransferObject = getSales();
            salesTable.getItems().addAll(listSaleTransferObject);
        } catch (SQLException throwable) {
            AlertWindow.createAlertWindow("Sql error when trying to get records", "Error");
        }

    }

    private ObservableList<Sale> getSales() throws SQLException {
        List<Sale> listSaleTransferObject = dataBase.getSales();

        return FXCollections.observableList(listSaleTransferObject);
    }

    private List<Warehouse> getWarehouses() throws SQLException {
        return dataBase.getWarehouses();
    }

    private boolean checkFields() {
        return !saleStuffName.getSelectionModel().isEmpty() &&
                !quantityField.getText().isEmpty() &&
                !amountField.getText().isEmpty();
    }

    public void addSale() {
        if (checkFields()) {
            Sale sale = new SaleBuilder()
                    .name(saleStuffName.getValue())
                    .date(Date.valueOf(dateField.getValue()))
                    .warehouseId(warehouses.get(saleStuffName.getValue()))
                    .amount(Double.parseDouble(amountField.getText()))
                    .quantity(Integer.parseInt(quantityField.getText()))
                    .build();
            try {
                dataBase.addSale(sale);
                salesTable.getItems().add(sale);
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to add", "Error");
            }
        } else {
            AlertWindow.createAlertWindow("All fields must be filled", "Error");
        }
        clear();
    }

    public void updateSale() {
        if (checkFields()) {
            Sale currentSale = salesTable.getSelectionModel().getSelectedItem();
            Sale updatedSale = new SaleBuilder()
                    .id(currentSale.getId())
                    .amount(Double.parseDouble(amountField.getText()))
                    .quantity(Integer.parseInt(quantityField.getText()))
                    .date(Date.valueOf(dateField.getValue()))
                    .warehouseId(warehouses.get(saleStuffName.getValue()))
                    .build();
            try {
                dataBase.updateSale(updatedSale);
                currentSale.setQuantity(updatedSale.getQuantity());
                currentSale.setAmount(updatedSale.getAmount());
                currentSale.setDate(updatedSale.getDate());
                currentSale.setWarehouseId(updatedSale.getWarehouseId());
                salesTable.refresh();
            }
            catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to add", "Error");
                saleStuffName.setValue(currentSale.getName());
                quantityField.setText(currentSale.getQuantity().toString());
                amountField.setText(currentSale.getAmount().toString());
                dateField.setValue(currentSale.getDate().toLocalDate());
            }
        } else {
            AlertWindow.createAlertWindow("All fields must be filled", "Error");
        }
    }

    public void deleteSale() {
        Sale selectedSale = salesTable.getSelectionModel().getSelectedItem();
        if (selectedSale != null) {
            try {
                dataBase.deleteSale(selectedSale.getId());
                salesTable.getItems().remove(selectedSale);
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to delete", "Error");
            }
        }
    }

    public void cancelUpdate() {
        updateSaleButton.setDisable(true);
        updateSaleButton.setVisible(false);
        addSaleButton.setDisable(false);
        addSaleButton.setVisible(true);
        cancelButton.setDisable(true);
        deleteSaleButton.setDisable(true);
        clear();
        salesTable.getSelectionModel().clearSelection();
    }

    public void close() {
        try {
            Client.replaceStageContent("ui/resources/main_page.fxml");
        } catch (IOException e) {
            AlertWindow.createAlertWindow("Sql error when trying to open main page",
                    "Error");
        }
    }

    public void openWarehousePage() {
        try {
            Client.replaceStageContent("ui/resources/tabs/warehouse.fxml");
        } catch (IOException e) {
            AlertWindow.createAlertWindow("Sql error when trying to open warehouse page",
                    "Error");
        }
    }

    private void clear() {
        saleStuffName.getSelectionModel().clearSelection();
        quantityField.clear();
        amountField.clear();
        dateField.setValue(LocalDate.now());
    }
}

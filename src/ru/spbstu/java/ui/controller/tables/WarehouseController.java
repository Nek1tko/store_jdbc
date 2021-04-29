package ru.spbstu.java.ui.controller.tables;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.spbstu.java.Client;
import ru.spbstu.java.server.builder.WarehouseBuilder;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.server.entity.Warehouse;
import ru.spbstu.java.ui.error.AlertWindow;
import ru.spbstu.java.ui.filter.TextFieldFilters;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class WarehouseController implements Initializable {
    private DataBase dataBase;

    @FXML
    private TableView<Warehouse> warehouseTable;

    @FXML
    private TableColumn<Warehouse, String> nameColumn;

    @FXML
    private TableColumn<Warehouse, Integer> quantityColumn;

    @FXML
    private TableColumn<Warehouse, Double> amountColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField amountTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataBase = DataBase.instance();

        amountTextField.setTextFormatter(new TextFormatter<>(TextFieldFilters.getDoubleFilter()));
        quantityTextField.setTextFormatter(new TextFormatter<>(TextFieldFilters.getIntegerFilter()));
        nameTextField.setTextFormatter(new TextFormatter<>(TextFieldFilters.getStringFilter()));

        warehouseTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Warehouse>) change -> {
            Warehouse warehouse = warehouseTable.getSelectionModel().getSelectedItem();
            if (warehouse != null) {
                nameTextField.setText(warehouse.getName());
                quantityTextField.setText(warehouse.getQuantity().toString());
                amountTextField.setText(warehouse.getAmount().toString());

                cancelButton.setDisable(false);
                deleteButton.setDisable(false);
                updateButton.setDisable(false);
                updateButton.setVisible(true);
                addButton.setDisable(true);
                addButton.setVisible(false);
            }
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        try {
            List<Warehouse> warehouseList = dataBase.getWarehouses();
            warehouseTable.getItems().addAll(warehouseList);
        } catch (SQLException throwable) {
            AlertWindow.createAlertWindow("Sql error when trying to load records", "Error");
        }
    }

    public void cancel() {
        updateButton.setDisable(true);
        updateButton.setVisible(false);
        addButton.setDisable(false);
        addButton.setVisible(true);
        cancelButton.setDisable(true);
        deleteButton.setDisable(true);

        clear();
        warehouseTable.getSelectionModel().clearSelection();
    }

    public void add() {
        if (checkFields()) {
            Warehouse warehouse = new WarehouseBuilder()
                    .name(nameTextField.getText())
                    .amount(Double.parseDouble(amountTextField.getText()))
                    .quantity(Integer.parseInt(quantityTextField.getText()))
                    .build();
            try {
                dataBase.addWarehouse(warehouse);
                warehouseTable.getItems().add(warehouse);
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to add", "Error");
            }
        } else {
            AlertWindow.createAlertWindow("All fields must be filled", "Error");
        }
        clear();
    }

    public void update() {
        if (checkFields()) {
            Warehouse currentWarehouse = warehouseTable.getSelectionModel().getSelectedItem();
            Warehouse warehouse = new WarehouseBuilder()
                    .id(currentWarehouse.getId())
                    .name(nameTextField.getText())
                    .amount(Double.parseDouble(amountTextField.getText()))
                    .quantity(Integer.parseInt(quantityTextField.getText()))
                    .build();
            try {
                dataBase.updateWarehouse(warehouse);
                currentWarehouse.setName(warehouse.getName());
                currentWarehouse.setQuantity(warehouse.getQuantity());
                currentWarehouse.setAmount(warehouse.getAmount());
                warehouseTable.refresh();
            }
            catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to add", "Error");
            }
        } else {
            AlertWindow.createAlertWindow("All fields must be filled", "Error");
        }
    }

    public void close() {
        try {
            Client.replaceStageContent("ui/resources/tabs/tab_page.fxml");
        } catch (IOException e) {
            AlertWindow.createAlertWindow("Sql error when trying to open tab page",
                    "Error");
        }
    }

    public void delete() {
        Warehouse selectedWarehouse = warehouseTable.getSelectionModel().getSelectedItem();
        if (selectedWarehouse != null) {
            try {
                dataBase.deleteWarehouse(selectedWarehouse.getId());
                warehouseTable.getItems().remove(selectedWarehouse);
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to delete", "Error");
            }
        }
    }

    private boolean checkFields() {
        return !nameTextField.getText().isEmpty() &&
                !quantityTextField.getText().isEmpty() &&
                !amountTextField.getText().isEmpty();
    }

    private void clear() {
        nameTextField.clear();
        quantityTextField.clear();
        amountTextField.clear();
    }
}

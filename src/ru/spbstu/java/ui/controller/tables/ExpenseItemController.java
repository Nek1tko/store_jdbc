package ru.spbstu.java.ui.controller.tables;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.spbstu.java.Client;
import ru.spbstu.java.server.builder.ExpenseItemBuilder;
import ru.spbstu.java.server.builder.WarehouseBuilder;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.server.entity.ExpenseItem;
import ru.spbstu.java.server.entity.Warehouse;
import ru.spbstu.java.ui.error.AlertWindow;
import ru.spbstu.java.ui.filter.TextFieldFilters;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ExpenseItemController implements Initializable {
    private DataBase dataBase;

    @FXML
    private TableView<ExpenseItem> expenseItemTable;

    @FXML
    private TableColumn<ExpenseItem, String> nameColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button updateButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataBase = DataBase.instance();
        nameTextField.setTextFormatter(new TextFormatter<>(TextFieldFilters.getStringFilter()));

        expenseItemTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<ExpenseItem>) change -> {
            ExpenseItem expenseItem = expenseItemTable.getSelectionModel().getSelectedItem();
            if (expenseItem != null) {
                nameTextField.setText(expenseItem.getName());

                cancelButton.setDisable(false);
                deleteButton.setDisable(false);
                updateButton.setDisable(false);
                updateButton.setVisible(true);
                addButton.setDisable(true);
                addButton.setVisible(false);
            }
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        try {
            List<ExpenseItem> expenseItemList = dataBase.getExpenseItems();
            expenseItemTable.getItems().addAll(expenseItemList);
        } catch (SQLException throwable) {
            AlertWindow.createAlertWindow("Sql error when trying to load records", "Error");
        }
    }

    public void add() {
        if (!nameTextField.getText().isEmpty()) {
            ExpenseItem expenseItem = new ExpenseItemBuilder()
                    .name(nameTextField.getText())
                    .build();
            try {
                dataBase.addExpenseItem(expenseItem);
                expenseItemTable.getItems().add(expenseItem);
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to add", "Error");
            }
        } else {
            AlertWindow.createAlertWindow("All fields must be filled", "Error");
        }
        nameTextField.clear();
    }

    public void update() {
        if (!nameTextField.getText().isEmpty()) {
            ExpenseItem currentExpenseItem = expenseItemTable.getSelectionModel().getSelectedItem();
            ExpenseItem expenseItem = new ExpenseItemBuilder()
                    .id(currentExpenseItem.getId())
                    .name(nameTextField.getText())
                    .build();
            try {
                dataBase.updateExpenseItem(expenseItem);
                currentExpenseItem.setName(expenseItem.getName());
                expenseItemTable.refresh();
            }
            catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to add", "Error");
            }
        } else {
            AlertWindow.createAlertWindow("All fields must be filled", "Error");
        }
    }

    public void delete() {
        ExpenseItem selectedExpenseItem = expenseItemTable.getSelectionModel().getSelectedItem();
        if (selectedExpenseItem != null) {
            try {
                dataBase.deleteExpenseItem(selectedExpenseItem.getId());
                expenseItemTable.getItems().remove(selectedExpenseItem);
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to delete", "Error");
            }
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

    public void cancel() {
        updateButton.setDisable(true);
        updateButton.setVisible(false);
        addButton.setDisable(false);
        addButton.setVisible(true);
        cancelButton.setDisable(true);
        deleteButton.setDisable(true);

        nameTextField.clear();
        expenseItemTable.getSelectionModel().clearSelection();
    }
}

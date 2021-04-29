package ru.spbstu.java.ui.controller.tables;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.spbstu.java.Client;
import ru.spbstu.java.server.builder.ChargeBuilder;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.server.entity.Charge;
import ru.spbstu.java.server.entity.ExpenseItem;
import ru.spbstu.java.ui.error.AlertWindow;
import ru.spbstu.java.ui.filter.TextFieldFilters;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ChargesController implements Initializable {
    private DataBase dataBase;
    private Map<String, Long> expenseItems;

    @FXML
    private Button deleteChargeButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addChargeButton;

    @FXML
    private Button updateChargeButton;

    @FXML
    private TableView<Charge> chargesTable;

    @FXML
    private TableColumn<Charge, String> expenseItemColumn;

    @FXML
    private TableColumn<Charge, Double> amountColumn;

    @FXML
    private TableColumn<Charge, Date> dateColumn;

    @FXML
    private ComboBox<String> expenseItemComboBox;

    @FXML
    private TextField amountTextField;

    @FXML
    private DatePicker chargeDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataBase = DataBase.instance();
        amountTextField.setTextFormatter(new TextFormatter<>(TextFieldFilters.getDoubleFilter()));

        chargesTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Charge>) change -> {
            Charge charge = chargesTable.getSelectionModel().getSelectedItem();
            if (charge != null) {
                expenseItemComboBox.setValue(charge.getName());
                amountTextField.setText(charge.getAmount().toString());
                chargeDate.setValue(charge.getDate().toLocalDate());
                cancelButton.setDisable(false);
                deleteChargeButton.setDisable(false);
                updateChargeButton.setDisable(false);
                updateChargeButton.setVisible(true);
                addChargeButton.setDisable(true);
                addChargeButton.setVisible(false);
            }
        });

        chargeDate.setValue(LocalDate.now());
        expenseItemColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        try {
            List<ExpenseItem> expenseItemsList = dataBase.getExpenseItems();
            expenseItems = expenseItemsList.stream().collect(Collectors.toMap(ExpenseItem::getName,
                    ExpenseItem::getId
                    )
            );
            expenseItemsList.forEach(expenseItem -> expenseItemComboBox.getItems().add(expenseItem.getName()));
            ObservableList<Charge> listSaleTransferObject = FXCollections.observableArrayList(dataBase.getCharges());
            chargesTable.getItems().addAll(listSaleTransferObject);
        } catch (SQLException throwable) {
            AlertWindow.createAlertWindow("Sql error when trying to get records", "Error");
        }

    }

    public void addCharge() {
        if (checkFields()) {
            Charge charge = new ChargeBuilder()
                    .amount(Double.parseDouble(amountTextField.getText()))
                    .date(Date.valueOf(chargeDate.getValue()))
                    .expenseItemId(expenseItems.get(expenseItemComboBox.getValue()))
                    .name(expenseItemComboBox.getValue())
                    .build();
            try {
                dataBase.addCharge(charge);
                chargesTable.getItems().add(charge);
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to add", "Error");
            }
        }
        else {
            AlertWindow.createAlertWindow("All fields must be filled", "Error");
        }
        clear();
    }

    public void updateCharge() {
        if (checkFields()) {
            Charge currentCharge = chargesTable.getSelectionModel().getSelectedItem();
            Charge updatedCharge = new ChargeBuilder()
                    .id(currentCharge.getId())
                    .amount(Double.parseDouble(amountTextField.getText()))
                    .date(Date.valueOf(chargeDate.getValue()))
                    .expenseItemId(expenseItems.get(expenseItemComboBox.getValue()))
                    .build();
            try {
                dataBase.updateCharge(updatedCharge);
                currentCharge.setAmount(updatedCharge.getAmount());
                currentCharge.setDate(updatedCharge.getDate());
                currentCharge.setExpenseItemId(updatedCharge.getExpenseItemId());
                chargesTable.refresh();
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Sql error when trying to update", "Error");
                expenseItemComboBox.setValue(currentCharge.getName());
                amountTextField.setText(currentCharge.getAmount().toString());
                chargeDate.setValue(currentCharge.getDate().toLocalDate());
            }
        }
        else {
            AlertWindow.createAlertWindow("All fields must be filled", "Error");
        }
    }

    public void deleteCharge() {
        try {
            Charge charge = chargesTable.getSelectionModel().getSelectedItem();
            dataBase.deleteCharge(charge.getId());
            chargesTable.getItems().remove(charge);
        } catch (SQLException throwable) {
            AlertWindow.createAlertWindow("Sql error when trying to delete", "Error");
        }
    }

    public void close() {
        try {
            Client.replaceStageContent("ui/resources/main_page.fxml");
        } catch (IOException e) {
            AlertWindow.createAlertWindow("Sql error when trying to open main page",
                    "Error");
        }
    }


    public void cancelUpdate() {
        updateChargeButton.setDisable(true);
        updateChargeButton.setVisible(false);
        addChargeButton.setDisable(false);
        addChargeButton.setVisible(true);
        cancelButton.setDisable(true);
        deleteChargeButton.setDisable(true);

        clear();
        chargesTable.getSelectionModel().clearSelection();
    }

    public void openExpenseItemPage() {
        try {
            Client.replaceStageContent("ui/resources/tabs/expense_item.fxml");
        } catch (IOException e) {
            AlertWindow.createAlertWindow("Sql error when trying to open expense item page",
                    "Error");
        }
    }

    private boolean checkFields() {
        return !expenseItemComboBox.getSelectionModel().isEmpty() &&
                !amountTextField.getText().isEmpty();
    }

    private void clear() {
        amountTextField.clear();
        chargeDate.setValue(LocalDate.now());
        expenseItemComboBox.getSelectionModel().clearSelection();
    }
}

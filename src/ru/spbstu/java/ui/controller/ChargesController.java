package ru.spbstu.java.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.server.entity.Charge;
import ru.spbstu.java.server.entity.ExpenseItem;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
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
        chargesTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Charge>) change -> {
            Charge charge = chargesTable.getSelectionModel().getSelectedItem();
            if (change != null) {
                expenseItemComboBox.setValue(charge.getName());
                amountTextField.setText(charge.getAmount().toString());
                chargeDate.setValue(charge.getDate().toLocalDate());
                cancelButton.setDisable(false);
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
            throwable.printStackTrace();
        }

    }

    public void addCharge() {

    }

    public void updateCharge() {

    }

    public void deleteCharge() {

    }

    public void cancelUpdate() {

    }

    private void createAlertWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.setContentText("All fields must not be empty!");
        alert.showAndWait();
    }
}

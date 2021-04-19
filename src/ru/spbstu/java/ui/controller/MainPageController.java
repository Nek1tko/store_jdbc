package ru.spbstu.java.ui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ru.spbstu.java.server.builder.SaleBuilder;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.server.entity.Sale;
import ru.spbstu.java.server.entity.Warehouse;

import java.net.URL;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainPageController implements Initializable {
    private Stage currentStage;
    private DataBase dataBase;
    private Map<String, Long> warehouses;

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
        dataBase = DataBase.instance();
        salesTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Sale>) change -> {
            Sale sale = salesTable.getSelectionModel().getSelectedItem();
            if (sale != null) {
                saleStuffName.setValue(sale.getName());
                quantityField.setText(sale.getQuantity().toString());
                amountField.setText(sale.getAmount().toString());
                dateField.setValue(sale.getDate().toLocalDate());
            }
            else {
                saleStuffName.setValue("");
                quantityField.setText("");
                amountField.setText("");
                dateField.setValue(LocalDate.now());
            }
        });

        List<Warehouse> list = getWarehouses();
        for (Warehouse el : list) {
            saleStuffName.getItems().add(el.getName());
        }
        warehouses = list.stream().collect(Collectors.toMap(Warehouse::getName, Warehouse::getId));

        dateField.setValue(LocalDate.now());
        saleName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        saleQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        saleAmount.setCellValueFactory(new PropertyValueFactory<>("Amount"));
        saleDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ObservableList<Sale> listSaleTransferObject = getSales();
        salesTable.getItems().addAll(listSaleTransferObject);
    }

    private ObservableList<Sale> getSales() {
        List<Sale> listSaleTransferObject = dataBase.getSales();

        return FXCollections.observableList(listSaleTransferObject);
    }

    private List<Warehouse> getWarehouses() {
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
            dataBase.addSale(sale);
            salesTable.getItems().add(sale);
        } else {
            createAlertWindow();
        }
    }

    private void createAlertWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.setContentText("All fields must not be empty!");
        alert.showAndWait();
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
        currentStage.getScene().addEventFilter(MouseEvent.MOUSE_CLICKED, (evt) -> {
            Node source = evt.getPickResult().getIntersectedNode();

            while (source != null && !(source instanceof TableRow)) {
                source = source.getParent();
            }

            if (source == null || (source instanceof TableRow && ((TableRow) source).isEmpty())) {
                salesTable.getSelectionModel().clearSelection();
            }
        });
    }
}

package ru.spbstu.java.ui.controller.statistic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.spbstu.java.Client;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.server.entity.Sale;
import ru.spbstu.java.ui.error.AlertWindow;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Top5ItemsController implements Initializable {
    private DataBase dataBase;

    @FXML
    private TableView<Sale> itemsTable;

    @FXML
    private TableColumn<Sale, String> nameColumn;

    @FXML
    private TableColumn<Sale, Double> amountColumn;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataBase = DataBase.instance();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));
    }

    public void close() {
        try {
            Client.replaceStageContent("ui/resources/main_page.fxml");
        } catch (IOException e) {
            AlertWindow.createAlertWindow("Failed to load main page", "Error");
        }
    }

    public void show() {
        if (checkFields()) {
            try {
                List<Sale> saleList = dataBase.getTop5Items(
                        Date.valueOf(startDatePicker.getValue()),
                        Date.valueOf(endDatePicker.getValue())
                );
                itemsTable.getItems().clear();
                itemsTable.getItems().addAll(saleList);
                startDatePicker.setValue(null);
                endDatePicker.setValue(null);
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Failed to load top 5 sold items", "Error");
            }
        }
        else {
            AlertWindow.createAlertWindow("Invalid dates", "Error");
        }
    }

    private boolean checkFields() {
        if (startDatePicker.getValue() == null || endDatePicker.getValue() == null) {
            return false;
        }
        return startDatePicker.getValue().isBefore(endDatePicker.getValue());
    }
}

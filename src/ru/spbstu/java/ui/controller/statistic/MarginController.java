package ru.spbstu.java.ui.controller.statistic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.spbstu.java.Client;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.ui.dto.Month;
import ru.spbstu.java.ui.error.AlertWindow;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class MarginController implements Initializable {
    private DataBase dataBase;
    @FXML
    private TextField marginTextBox;

    @FXML
    private ComboBox<Month> monthComboBox;

    @FXML
    private ComboBox<Integer> yearComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataBase = DataBase.instance();
        Arrays.stream(Month.class.getEnumConstants()).forEach(monthComboBox.getItems()::add);
        int year = LocalDate.now().getYear();
        IntStream.iterate(year, i -> i - 1).limit(3).forEach(yearComboBox.getItems()::add);
    }

    public void close() {
        try {
            Client.replaceStageContent("ui/resources/main_page.fxml");
        } catch (IOException e) {
            AlertWindow.createAlertWindow("Failed to load main page", "Error");
        }
    }

    public void show() {
        if (check()) {
            LocalDate localDate = LocalDate.of(
                    yearComboBox.getValue(),
                    monthComboBox.getValue().ordinal() + 1,
                    1);
            try {
                Double margin = dataBase.getMarginForMonth(Date.valueOf(localDate));
                marginTextBox.setText(margin.toString());
            } catch (SQLException throwable) {
                AlertWindow.createAlertWindow("Failed to show margin", "Error");
            }
        }
    }

    private boolean check() {
        return monthComboBox.getSelectionModel() != null &&
                yearComboBox.getSelectionModel() != null;
    }
}

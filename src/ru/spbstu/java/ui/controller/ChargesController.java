package ru.spbstu.java.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.server.entity.Charge;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ChargesController implements Initializable {
    private DataBase dataBase;
    private Map<String, Long> warehouses;

    @FXML
    private Button addChargeButton;

    @FXML
    private Button updateChargeButton;

    @FXML
    private TableView<Charge> chargeTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addCharge() {

    }

    public void updateCharge() {

    }

    public void deleteCharge() {

    }

    private void createAlertWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.setContentText("All fields must not be empty!");
        alert.showAndWait();
    }

    public void addTablesRestriction(Stage currentStage) {

    }
}

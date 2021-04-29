package ru.spbstu.java.ui.error;

import javafx.scene.control.Alert;

public class AlertWindow {
    public static void createAlertWindow(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

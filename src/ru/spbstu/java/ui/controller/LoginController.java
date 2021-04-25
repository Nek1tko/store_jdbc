package ru.spbstu.java.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.spbstu.java.Client;
import ru.spbstu.java.server.database.DataBase;
import ru.spbstu.java.server.database.exeption.InvalidCredoException;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private DataBase dataBase;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataBase = DataBase.instance();
    }

    public void signIn() {
        if (!loginTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()) {
            try {
                dataBase.signIn(loginTextField.getText(), passwordTextField.getText());
                Client.replaceStageContent("ui/resources/main_page.fxml");
            } catch (SQLException | InvalidCredoException | IOException throwable) {
                loginTextField.clear();
                passwordTextField.clear();
                throwable.printStackTrace();
            }
        }
    }
}

package ru.spbstu.java.ui.controller;

import javafx.fxml.Initializable;
import ru.spbstu.java.Client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showTables() {
        try {
            Client.replaceStageContent("ui/resources/tabs/tab_page.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTop5Items() {
        try {
            Client.replaceStageContent("ui/resources/statistic/top_5_items_page.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMargin() {
        try {
            Client.replaceStageContent("ui/resources/statistic/margin_page.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

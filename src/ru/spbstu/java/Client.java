package ru.spbstu.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.spbstu.java.server.User;
import ru.spbstu.java.server.connection.OracleDBConnection;
import ru.spbstu.java.server.database.DataBase;

import java.io.IOException;

public class Client extends Application {
    private static Stage currentStage;

    @Override
    public void start(Stage stage) throws IOException {
        DataBase.init(new OracleDBConnection(
                new User(
                        "c##user",
                        "1234"
                ),
                "localhost:1521:XE")
        );
        currentStage = stage;
        replaceStageContent("ui/resources/login/login_page.fxml");
        currentStage.show();
    }

    public static void replaceStageContent(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(Client.class.getResource(fxml));
        Parent page = loader.load();
        Scene scene = currentStage.getScene();
        if (scene == null) {
            scene = new Scene(page);
            currentStage.setScene(scene);
        } else {
            currentStage.setScene(new Scene(page));
        }
    }


}

package game.View.controller;

import game.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CheatController {
    public static void startUp() throws IOException {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("cheat!!");
        Parent root = FXMLLoader.load(new URL(Main.class.getResource("/game/fxml/cheatPage.fxml").toExternalForm()));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

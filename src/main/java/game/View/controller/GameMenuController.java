package game.View.controller;

import game.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class GameMenuController {
    public Button back;
    public Button playOnline;
    public Button loadGame;
    public Button newGame;
    

    public void back(ActionEvent actionEvent) throws IOException{
        Main.changeScene("mainMenu");
    }

    public void loadGame(ActionEvent actionEvent) throws IOException {
        Main.changeScene("loadGameMenu");
    }

    public void playOnline(ActionEvent actionEvent) {
    }

    public void newGame(ActionEvent actionEvent) throws IOException{
        Main.changeScene("newGame");
    }
}

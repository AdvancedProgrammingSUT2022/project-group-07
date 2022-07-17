package game.View.controller;

import game.Controller.UserController;
import game.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainMenuController {

    public Button gameMenu;
    public Button scoreBoard;
    public Button chatMenu;
    @FXML
    private Button back;
    @FXML
    private Button logout;
    @FXML
    private Button profileMenu;

    public void logout(ActionEvent actionEvent) throws IOException {
        UserController.getCurrentUser().setLoggedIn(false);
        UserController.setCurrentUser(null);
        Main.changeScene("loginMenu");
    }

    public void goToProfile(ActionEvent actionEvent) throws IOException {
        Main.changeScene("profileMenu");
    }

    public void goToChatMenu(ActionEvent actionEvent) throws IOException {
        Main.changeScene("chatMenu");
    }

    public void goToScoreBoard(ActionEvent actionEvent) throws IOException {
        Main.changeScene("scoreboardMenu");
    }

    public void goToGameMenu(ActionEvent actionEvent) throws IOException {
        Main.changeScene("gameMenu");
    }
}

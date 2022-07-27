package game.Client.View.controller;

import game.Client.Main;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class playOnlineMenuController {

    public void openFriendsLobbyMenu() throws IOException {
        Main.changeScene("friendsLobbyMenu");
    }

    public void back() throws IOException {
        Main.changeScene("gameMenu");
    }

    public void hostGameMenu() throws IOException {
        Main.changeScene("hostGameMenu");
    }

    public void joinGameMenu() throws IOException {
        Main.changeScene("joinGameMenu");
    }
}
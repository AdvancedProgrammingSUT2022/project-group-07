package game.View.controller;

import game.Controller.UserController;
import game.Controller.game.GameController;
import game.Controller.game.LogAndNotification.NotificationController;
import game.Main;
import game.Model.Terrain;
import game.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class newGameController {
    public Button backButton;
    public Button startButton;
    public ListView<String> allPlayers;
    public ListView<String> invitedPlayers;
    public Button addButton;
    public Button removeButton;

    private ObservableList<String> allPlayersAvailable;
    private ObservableList<String> playersToStartTheGame;

    public void goBack(ActionEvent actionEvent) throws IOException {
        Main.changeScene("gameMenu");
    }

    public void start(ActionEvent actionEvent) throws IOException {
        if (playersToStartTheGame.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("no players are added!");
            alert.show();
            return;
        }
        ArrayList<User> players = new ArrayList<>();
        players.add(UserController.getCurrentUser());
        for (User user : UserController.getUsers()) {
            if (playersToStartTheGame.contains(user.getNickname()))
                players.add(user);
        }
        GameController gameController = GameController.getInstance();
        gameController.setPlayers(players);
        gameController.initialize();
        NotificationController.runNotification(gameController);
        Main.changeScene("gamePage");
    }

    public void addPlayer(ActionEvent actionEvent) {
        String selectedItem = allPlayers.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;
        allPlayersAvailable.remove(selectedItem);
        playersToStartTheGame.add(selectedItem);
    }

    public void remove(ActionEvent actionEvent) {
        String selectedItem = invitedPlayers.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;
        playersToStartTheGame.remove(selectedItem);
        allPlayersAvailable.add(selectedItem);
    }

    public void initialize() {
        ArrayList<User> users = UserController.getUsers();
        User current = UserController.getCurrentUser();
        ArrayList<String> names = new ArrayList<>();
        for (User user : users) {
            if (user.equals(current)) continue;
            names.add(user.getNickname());
        }
        allPlayersAvailable = FXCollections.observableArrayList(names);
        allPlayers.setItems(allPlayersAvailable);
        playersToStartTheGame = FXCollections.observableArrayList();
        invitedPlayers.setItems(playersToStartTheGame);
    }


    public void removeE(DragEvent dragEvent) {

    }

    public void addE(DragEvent dragEvent) {

    }
}

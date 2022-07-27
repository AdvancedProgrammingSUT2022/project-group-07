package game.Client.View.controller;

import game.Client.ClientDataController;
import game.Client.Main;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Common.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class hostGameMenuController {

    public Button backButton;
    public Button startButton;
    public ListView<String> allFriendsOnline;
    public ListView<String> invitedPlayers;
    public Button addButton;
    public Button removeButton;

    private ObservableList<String> allFriendsAvailable;
    private ObservableList<String> playersToStartTheGame;

    public void initialize() {

        try {
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_ONLINE_FRIENDS, ClientDataController.getCurrentUser()));
            ServerResponse serverResponse = Main.getClientHandler().getResponse();
            if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)){

                ArrayList<User> users = serverResponse.getUsers();
                ArrayList<String> onlineUsers = new ArrayList<>();
                for (User user : users)
                    onlineUsers.add(user.getUsername());

                allFriendsAvailable = FXCollections.observableArrayList(onlineUsers);
                allFriendsOnline.setItems(allFriendsAvailable);
                playersToStartTheGame = FXCollections.observableArrayList();
                invitedPlayers.setItems(playersToStartTheGame);
            } else {
                showAlert(Alert.AlertType.INFORMATION , serverResponse.getMessage());
            }

        } catch (IOException ioException){
            showAlert(Alert.AlertType.ERROR , "can't send requests to server ! please try again !");
        }
    }

    public void start() throws IOException {
        if (playersToStartTheGame.isEmpty()) {
            showAlert(Alert.AlertType.ERROR , "Invite at least one of your friends !");
            return;
        }

        ArrayList<String> usernamesToStartTheGame = new ArrayList<String>(playersToStartTheGame) ;
        usernamesToStartTheGame.add(0 , ClientDataController.getCurrentUser().getUsername());

        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.CREATE_GAME , usernamesToStartTheGame));
        ServerResponse serverResponse = Main.getClientHandler().getResponse();

        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK))
            showAlert(Alert.AlertType.INFORMATION , "game created! go to join menu to join the game !");
        else
            showAlert(Alert.AlertType.ERROR , serverResponse.getMessage());
    }

    public void addPlayer() {
        String selectedItem = allFriendsOnline.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;
        allFriendsAvailable.remove(selectedItem);
        playersToStartTheGame.add(selectedItem);
    }

    public void remove() {
        String selectedItem = invitedPlayers.getSelectionModel().getSelectedItem();
        if (selectedItem == null) return;
        playersToStartTheGame.remove(selectedItem);
        allFriendsAvailable.add(selectedItem);
    }

    public void goBack() throws IOException {
        Main.changeScene("playOnlineMenu");
    }

    public void showAlert (Alert.AlertType alertType , String message){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}

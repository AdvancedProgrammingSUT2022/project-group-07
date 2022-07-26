package game.Client.View.controller;

import game.Client.ClientDataController;
import game.Client.Main;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.Network.TypeOfRequestParameter;
import game.Common.Enum.Network.TypeOfResponseParameter;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.FriendshipRequest;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Common.Model.User;
import game.Server.Controller.game.GameController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class friendsLobbyMenuController implements Initializable {

    public TabPane tabPane;
    public AnchorPane sendRequestAnchorPane;
    public AnchorPane FriendsAnchorPane;
    public AnchorPane RequestHistoryAnchorPane;


    public TextField playerUsernameTextField;
    public VBox sendRequestVBox;


    public VBox friendsVBox;


    public VBox requestsSentForOtherPlayersVBox;
    public VBox requestsReceivedFromOtherPlayers;

    String lastPage = "" ;
    String selectedPage = "Send Request" ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Tab tab : tabPane.getTabs())
            tab.setOnSelectionChanged(event -> selectedPage = tabPane.getSelectionModel().getSelectedItem().getText());

        Thread updateScreen = new Thread(() -> {
            Runnable runnable = () -> {
                if (!lastPage.equals(selectedPage)) {
                    try {
                        updateScreen();
                    } catch (IOException ignored) {}
                    lastPage = selectedPage ;
                }
            } ;
            while (true) {
                Platform.runLater(runnable);
                try {Thread.sleep(500);}
                catch (InterruptedException ignored) {}
            }
        });
        updateScreen.setDaemon(true);
        updateScreen.start();
    }

    // main methods of page
    public void updateScreen () throws IOException {
        switch (selectedPage){
            case "Send Request" -> loadSendRequestPage();
            case "Friends" -> loadFriendsPage() ;
            case "Request History" -> loadFriendshipRequestsHistoryPage() ;
        }
    }

    private void loadFriendshipRequestsHistoryPage() throws IOException {
        loadRequestsSent();
        loadRequestsReceived();
    }

    private void loadRequestsSent() throws IOException {
        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername());
        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_REQUESTS_OF , params));

        ServerResponse serverResponse = Main.getClientHandler().getResponse() ;
        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)){
            requestsSentForOtherPlayersVBox.getChildren().clear();
            for (FriendshipRequest friendshipRequest : serverResponse.getFriendshipRequests())
                requestsSentForOtherPlayersVBox.getChildren().add(getRequestSentHBox(friendshipRequest));
        } else
            showAlert(Alert.AlertType.INFORMATION , "something went wrong in server !");

        System.out.println("request sent loaded fully");
    }

    private HBox getRequestSentHBox (FriendshipRequest friendshipRequest){
        HBox hBox = new HBox();
        hBox.getChildren().add(new Label("for " + friendshipRequest.getReceiver())) ;

        if (friendshipRequest.isAccepted()){
            hBox.setStyle("-fx-background-color: lightgreen");
        } else if (friendshipRequest.isRejected()){
            hBox.setStyle("-fx-background-color: red");
        } else if (!friendshipRequest.isHandled()){
            hBox.setStyle("-fx-background-color: gray");
        }

        return hBox;
    }

    private void loadRequestsReceived() throws IOException {
        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername());
        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_REQUESTS_FOR , params));

        ServerResponse serverResponse = Main.getClientHandler().getResponse() ;
        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)){
            requestsReceivedFromOtherPlayers.getChildren().clear();
            for (FriendshipRequest friendshipRequest : serverResponse.getFriendshipRequests())
                requestsReceivedFromOtherPlayers.getChildren().add(getRequestReceivedHBox(friendshipRequest));
        } else
            showAlert(Alert.AlertType.INFORMATION , "something went wrong in server !");

        System.out.println("request received loaded fully");
    }

    private HBox getRequestReceivedHBox(FriendshipRequest friendshipRequest) {
        HBox hBox = new HBox();
        hBox.getChildren().add(new Label("from " + friendshipRequest.getSender())) ;

        if (friendshipRequest.isAccepted()){
            hBox.setStyle("-fx-background-color: lightgreen");
        } else if (friendshipRequest.isRejected()){
            hBox.setStyle("-fx-background-color: red");
        } else if (!friendshipRequest.isHandled()){
            hBox.setStyle("-fx-background-color: gray");
            // accept button
            Button accept = new Button("Accept") ;
            accept.setOnMouseClicked(mouseEvent -> {
                try {
                    Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.ACCEPT_FRIENDSHIP_REQUEST, friendshipRequest));
                    ServerResponse serverResponse = Main.getClientHandler().getResponse();
                    if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK))
                        showAlert(Alert.AlertType.INFORMATION , "Accepted");
                    loadFriendshipRequestsHistoryPage();
                } catch (IOException ignored) {}
            });
            hBox.getChildren().add(accept);
            // reject button
            Button reject = new Button("Reject") ;
            reject.setOnMouseClicked(mouseEvent -> {
                try {
                    Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.REJECT_FRIENDSHIP_REQUEST, friendshipRequest));
                    ServerResponse serverResponse = Main.getClientHandler().getResponse();
                    if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK))
                        showAlert(Alert.AlertType.INFORMATION , "Rejected");
                    loadFriendshipRequestsHistoryPage();
                } catch (IOException ignored) {}
            });
            hBox.getChildren().add(reject);
        }
        return hBox;
    }

    private void loadFriendsPage() throws IOException {
        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername());
        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_FRIENDS , params));
        ServerResponse serverResponse = Main.getClientHandler().getResponse();
        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)) {
            friendsVBox.getChildren().clear();
            for (String s : ((ArrayList<String>) serverResponse.getResponseHashMap().get(TypeOfResponseParameter.USER)))
                friendsVBox.getChildren().add(addFriendHBox(s));
        }
    }

    private HBox addFriendHBox (String name){
        HBox hBox = new HBox();
        hBox.getChildren().add(new Label(name));
        return hBox ;
    }

    private void loadSendRequestPage() {
        playerUsernameTextField.clear();
    }

    public void getPlayerByUsernameForFriendRequest(MouseEvent mouseEvent) throws IOException {
        String username = playerUsernameTextField.getText() ;
        if (username.isEmpty())
            showAlert(Alert.AlertType.INFORMATION , "empty username");
        else {
            HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
            params.put(TypeOfRequestParameter.USERNAME , username);
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_USER_BY_USERNAME, params));
            ServerResponse serverResponse = Main.getClientHandler().getResponse();

            if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK) && serverResponse.getUser() != null)
                createRequestVBox(serverResponse.getUser());
            else
                showAlert(Alert.AlertType.INFORMATION , serverResponse.getMessage());
        }
    }

    private void createRequestVBox (User user) throws IOException {
        sendRequestVBox.getChildren().clear();
        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername());
        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_FRIENDS , params));
        ServerResponse serverResponse = Main.getClientHandler().getResponse();

        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)){
            HBox hBox = new HBox();
            hBox.getChildren().add(new Label(user.getUsername()));
            ArrayList<String> arrayList = (ArrayList<String>) serverResponse.getResponseHashMap().get(TypeOfResponseParameter.USER);
            if (arrayList.contains(ClientDataController.getCurrentUser().getUsername()) || user.getUsername().equals(ClientDataController.getCurrentUser().getUsername()))
                addAlreadyFriendEffect(hBox);
            else
                addNotFriendEffect(hBox , user);

            sendRequestVBox.getChildren().add(hBox);
        }else
            showAlert(Alert.AlertType.INFORMATION , "something went wrong in server ! please try again !");
    }

    public void addAlreadyFriendEffect (HBox hBox){
        hBox.setStyle("-fx-background-color: green");
    }

    public void addNotFriendEffect (HBox hBox , User receiver){
        hBox.setStyle("-fx-background-color: red");
        Button send = new Button("Send") ;
        send.setOnMouseClicked(mouseEvent -> {
            HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
            params.put(TypeOfRequestParameter.SENDER , ClientDataController.getCurrentUser().getUsername());
            params.put(TypeOfRequestParameter.RECEIVER , receiver.getUsername());
            try {
                Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.SEND_FRIENDSHIP_REQUEST, params));
                if (Main.getClientHandler().getResponse().getTypeOfResponse().equals(TypeOfResponse.OK))
                    showAlert(Alert.AlertType.INFORMATION , "Request sent!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        hBox.getChildren().add(send);
    }

    private void showAlert (Alert.AlertType alertType , String message){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }

    public void back() throws IOException {
        Main.changeScene("playOnlineMenu");
    }
}

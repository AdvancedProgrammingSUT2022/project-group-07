package game.Client.View;

import game.Client.ClientDataController;
import game.Client.Main;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class joinGameMenu {

    public VBox vBox;

    public void initialize (){

        try {
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_GAMES_FOR_PLAYER, ClientDataController.getCurrentUser()));
            ServerResponse serverResponse = Main.getClientHandler().getResponse();
            if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)){
                addOptionsToJoin(serverResponse);
            } else showAlert(Alert.AlertType.ERROR , serverResponse.getMessage());

        } catch (IOException ioException){
            System.out.println(ioException.getMessage());
            ioException.printStackTrace();
        }
    }

    public void addOptionsToJoin (ServerResponse serverResponse){
        vBox.getChildren().clear();
        for (String uuid : serverResponse.getUuids()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.getStyleClass().add("hbox") ;
            vBox.getChildren().add(hBox);
            hBox.getChildren().add(new Label(uuid)) ;
            Button join = new Button("Join");
            join.getStyleClass().add("join") ;
            hBox.getChildren().add(join) ;
            join.setOnMouseClicked(mouseEvent -> {
                try {
                    Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.JOIN_GAME, uuid));
                    if (Main.getClientHandler().getResponse().getTypeOfResponse().equals(TypeOfResponse.OK)){
                        showAlert(Alert.AlertType.INFORMATION , "Just wait till others join !");
                    }
                    Thread waitingThreadToJoinGame = new Thread(() -> {
                        try {
                            ServerResponse serverResponse1 = Main.getClientHandler().getResponse();
                            if (serverResponse1.getTypeOfResponse().equals(TypeOfResponse.START_GAME)){
                                ClientDataController.setGameController(serverResponse1.getGameControllerDecoy());
                                ClientDataController.setUuid(serverResponse1.getQueuedGameUUID());
                                Main.changeScene("gamePage");
                            } else {
                                showAlert(Alert.AlertType.ERROR , serverResponse1.getMessage());
                            }
                        } catch (IOException e) {
                            System.out.println("exception is is join game menu controller");
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                    }) ;
                    waitingThreadToJoinGame.setDaemon(true);
                    waitingThreadToJoinGame.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void showAlert (Alert.AlertType alertType , String message){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }

    public void back() throws IOException {
        Main.changeScene("playOnlineMenu");
    }
}

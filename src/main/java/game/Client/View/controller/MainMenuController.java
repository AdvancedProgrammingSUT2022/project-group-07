package game.Client.View.controller;

import game.Client.ClientDataController;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.Network.TypeOfRequestParameter;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Server.Controller.UserController;
import game.Client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.HashMap;

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
        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername()) ;

        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.LOGOUT ,params));
        ServerResponse serverResponse = Main.getClientHandler().getResponse();

        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)) {
            ClientDataController.setCurrentUser(null);
            Main.changeScene("loginMenu");
        }
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

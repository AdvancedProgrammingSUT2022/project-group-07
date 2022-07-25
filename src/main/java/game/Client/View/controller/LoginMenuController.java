package game.Client.View.controller;

import game.Client.ClientDataController;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.Network.TypeOfRequestParameter;
import game.Common.Enum.Network.TypeOfResponseParameter;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Server.Controller.UserController;
import game.Server.Controller.menu.ProfileValidation;
import game.Common.Enum.MenuName;
import game.Client.Main;
import game.Common.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.HashMap;

public class LoginMenuController {
    public ImageView headTitle;
    @FXML
    private PasswordField loginPass;
    @FXML
    private TextField loginName;
    @FXML
    private TextField username;
    @FXML
    private TextField nickname;
    @FXML
    private PasswordField password;
    @FXML
    private Button register;
    @FXML
    private Button exit;
    @FXML
    private Button login;
    @FXML
    private Text registerError;
    @FXML
    private Text loginError;

    public void exit(ActionEvent actionEvent) {
        MenuName.setCurrentMenu(MenuName.TERMINATE);
        UserController.saveUsers();
        Main.closeWindow();
    }
    public void createUser(ActionEvent actionEvent) throws IOException {
        loginError.setText(null);
        String name = username.getText();
        String pass = password.getText();
        String nick = nickname.getText();

        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , name);
        params.put(TypeOfRequestParameter.PASSWORD , pass);
        params.put(TypeOfRequestParameter.NICKNAME , nick);

        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.SIGN_UP , params));
        ServerResponse serverResponse = Main.getClientHandler().getResponse() ;

        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)) {
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.LOGIN , params));
            ServerResponse loginResponse = Main.getClientHandler().getResponse();
            if (loginResponse.getTypeOfResponse().equals(TypeOfResponse.OK)) {
                ClientDataController.setCurrentUser(loginResponse.getUser());
                Main.changeScene("mainMenu");
            }
        }
        else{
            showError(serverResponse.getMessage());
        }
    }

    public void login(ActionEvent actionEvent) throws IOException {
        String name = loginName.getText();
        String pass = loginPass.getText();

        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , name);
        params.put(TypeOfRequestParameter.PASSWORD , pass);

        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.LOGIN , params));
        ServerResponse serverResponse = Main.getClientHandler().getResponse() ;

        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)) {
            ClientDataController.setCurrentUser(serverResponse.getUser());
            Main.changeScene("mainMenu");
        }
        else
            showError(serverResponse.getMessage());
    }
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}

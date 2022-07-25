package game.Client.View.controller;

import game.Client.ClientDataController;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.Network.TypeOfRequestParameter;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Server.Controller.UserController;
import game.Server.Controller.menu.ProfileValidation;
import game.Client.Main;
import game.Common.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ProfileMenuController {

    @FXML
    private Button fileAvatar;
    @FXML
    private Text username;
    @FXML
    private Button back;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;
    @FXML
    private TextField nickname;
    @FXML
    private Button changePass;
    @FXML
    private Button changeNick;
    @FXML
    private Button chooseAvatar;
    @FXML
    private ImageView profilePic;

    private FileChooser fileChooser;
    private File filePath;

    public void chooseFileAvatar(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("choose image");

        this.filePath = fileChooser.showOpenDialog(stage);
        if (filePath != null) {
            UserController.getCurrentUser().setAvatarFilePath(filePath.getPath());
            ImagePattern pattern = new ImagePattern(new Image(filePath.getPath()));
            this.profilePic.setImage(pattern.getImage());
            showConfirm("profile avatar changed successfully!");
        }
        else
            showError("Please choose an avatar!");
    }


    public void changeNickname(ActionEvent actionEvent) throws IOException {
        String newNickname = nickname.getText();

        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>() ;
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername());
        params.put(TypeOfRequestParameter.NICKNAME , newNickname);

        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.UPDATE_PROFILE_CHANGE_NICKNAME , params));

        ServerResponse serverResponse = Main.getClientHandler().getResponse();

        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)) {
            HashMap<TypeOfRequestParameter , Object> paramss = new HashMap<>();
            paramss.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername()) ;
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.LOGOUT ,paramss));

            ServerResponse logoutResponse = Main.getClientHandler().getResponse() ;

            if (logoutResponse.getTypeOfResponse().equals(TypeOfResponse.OK)){
                ClientDataController.setCurrentUser(null);
                Main.changeScene("loginMenu");
            }
        } else
            showError(serverResponse.getMessage());
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Main.changeScene("mainMenu");
    }

    public void changePassword(ActionEvent actionEvent) throws IOException {

        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>() ;
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername());
        params.put(TypeOfRequestParameter.PASSWORD , oldPassword.getText());
        params.put(TypeOfRequestParameter.NEW_PASSWORD , newPassword.getText());

        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.UPDATE_PROFILE_CHANGE_PASSWORD , params));

        ServerResponse serverResponse = Main.getClientHandler().getResponse();

        if (serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)) {
            HashMap<TypeOfRequestParameter , Object> paramss = new HashMap<>();
            paramss.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername()) ;
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.LOGOUT ,paramss));
            if (Main.getClientHandler().getResponse().getTypeOfResponse().equals(TypeOfResponse.OK)){
                ClientDataController.setCurrentUser(null);
                Main.changeScene("loginMenu");
            }
        } else
            showError(serverResponse.getMessage());

    }

    public void initialize() {
        User currentUser = ClientDataController.getCurrentUser();
        ImagePattern profilePic;
        if (currentUser.getAvatarFilePath() == null) {
            profilePic = new ImagePattern(new Image(getClass().getResource("/game/images/avatars/" +
                    currentUser.getAvatarNumber() + ".png").toExternalForm()));
        }
        else {
            profilePic = new ImagePattern(new Image(currentUser.getAvatarFilePath()));
        }
        this.profilePic.setImage(profilePic.getImage());
    }

    public void chooseGameAvatar(ActionEvent actionEvent) throws IOException {
        UserController.getCurrentUser().setAvatarFilePath(null);
        Main.changeScene("avatarMenu");
    }
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    public void showConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.show();
    }
}

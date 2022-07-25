package game.Client.View.controller;

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


    public void changeNickname(ActionEvent actionEvent) {
        User user = UserController.getCurrentUser();
        String newNickname = nickname.getText();

        if (!ProfileValidation.nicknameIsValid(newNickname))
            showError("invalid nickname : nickname can only have letters");
        else if (ProfileValidation.nicknameIsUsed(newNickname))
            showError("user with nickname " + newNickname + " already exists");
        else {
            user.setNickname(newNickname);
            showConfirm("nickname changed successfully!");
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Main.changeScene("mainMenu");
    }

    public void changePassword(ActionEvent actionEvent) {
        String oldPass = oldPassword.getText();
        String newPass = newPassword.getText();
        User user = UserController.getCurrentUser();

        if (!user.getPassword().equals(oldPass))
            showError("old password is invalid");
        else if (oldPass.equals(newPass))
            showError("please enter a new password");
        else if (!ProfileValidation.passwordIsValid(newPass))
            showError("invalid new password : must have at least 4" +
                    " characters (a capital and a small letter) and a number");
        else {
            user.setPassword(newPass);
            showConfirm("password changed successfully!");
        }
    }

    public void initialize() {
        User currentUser = UserController.getCurrentUser();
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

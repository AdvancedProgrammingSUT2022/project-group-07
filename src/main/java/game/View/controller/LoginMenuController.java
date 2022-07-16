package game.View.controller;

import game.Controller.UserController;
import game.Controller.menu.ProfileValidation;
import game.Enum.MenuName;
import game.Main;
import game.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;

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

        if (ProfileValidation.usernameIsUsed(name))
            showError("user with username " + name + " already exists");
        else if (ProfileValidation.nicknameIsUsed(nick))
            showError("user with nickname " + nick + " already exists");
        else if (!ProfileValidation.usernameIsValid(name))
            showError("invalid username : at least 3 characters and must have at least a letter");
        else if (!ProfileValidation.passwordIsValid(pass))
            showError("invalid password : at least 4 characters (a capital and a small) and a number");
        else if (!ProfileValidation.nicknameIsValid(nick))
            showError("invalid nickname : only alphabetical characters!");
        else {
            User user = new User(name , pass , nick);
            UserController.addUser(user);
            UserController.login(user);
            UserController.setCurrentUser(user);
            Main.changeScene("mainMenu");
        }
    }

    public void login(ActionEvent actionEvent) throws IOException {
        registerError.setText(null);
        String name = loginName.getText();
        String pass = loginPass.getText();
        User user = UserController.getUser(name , pass);
        if (name.isEmpty())
            showError("Please enter your username!");
        else if (pass.isEmpty())
            showError("Please enter your password!");
        else if (user == null)
            showError("username and password didn't match!");
        else {
            UserController.login(user);
            UserController.setCurrentUser(user);
            Main.changeScene("mainMenu");
        }
    }
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}

package game.Controller.menu;

import game.Controller.UserController;
import game.Controller.menu.ProfileValidation;
import game.Enum.MenuName;
import game.Main;
import game.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Matcher;

public class LoginMenuController {

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

    public String navigateMenu(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("game.Main Menu")) {
            if (UserController.getCurrentUser() != null && UserController.getCurrentUser().isLoggedIn()) {
                MenuName.setCurrentMenu(MenuName.MAIN_MENU);
                return "entered game.Main Menu!";
            }
            else return "please login first";
        }
        else if (menuName.equals("Profile Menu") || menuName.equals("Game Menu") || menuName.equals("Login Menu")) {
            return "menu navigation is not possible";
        }
        return "invalid menu name!";
    }

    public void createUser(ActionEvent actionEvent) throws IOException {
        loginError.setText(null);
        String name = username.getText();
        String pass = password.getText();
        String nick = nickname.getText();

        if (ProfileValidation.usernameIsUsed(name))
            registerError.setText("user with username " + name + " already exists");
        else if (ProfileValidation.nicknameIsUsed(nick))
            registerError.setText("user with nickname " + nick + " already exists");
        else if (!ProfileValidation.usernameIsValid(name))
            registerError.setText("invalid username : at least 3 characters and must have at least a letter");
        else if (!ProfileValidation.passwordIsValid(pass))
            registerError.setText("invalid password : at least 4 characters (a capital and a small) and a number");
        else if (!ProfileValidation.nicknameIsValid(nick))
            registerError.setText("invalid nickname : only alphabetical characters!");
        else {
            User user = new User(name , pass , nick);
            UserController.addUser(user);
            UserController.login(user);
            UserController.setCurrentUser(user);
            registerError.setText("Successful");
            Main.changeScene("mainMenu");
        }
    }

    public void login(ActionEvent actionEvent) throws IOException {
        registerError.setText(null);
        String name = loginName.getText();
        String pass = loginPass.getText();
        User user = UserController.getUser(name , pass);
        if (name.isEmpty())
            loginError.setText("Please enter your username!");
        else if (pass.isEmpty())
            loginError.setText("Please enter your password!");
        else if (user == null)
            loginError.setText("username and password didn't match!");
        else {
            UserController.login(user);
            UserController.setCurrentUser(user);
            loginError.setText("user logged in successfully!");
            Main.changeScene("mainMenu");
        }
    }
}

package game.Controller.menu;

import game.Controller.UserController;
import game.Enum.MenuName;
import game.Main;
import game.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;

public class ProfileMenuController implements Initializable {

    @FXML
    private Text changePassError;
    @FXML
    private Text changePassSuccess;
    @FXML
    private Text changeNickSuccess;
    @FXML
    private Text changeNickError;
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
        UserController.getCurrentUser().setAvatarFilePath(filePath.getPath());
        ImagePattern pattern = new ImagePattern(new Image(filePath.getPath()));
        this.profilePic.setImage(pattern.getImage());
    }

    public String menuNavigation(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("Profile Menu")
        || menuName.equals("game.Main Menu")
        || menuName.equals("Game Menu")
        || menuName.equals("Login Menu"))
            return "menu navigation is not possible";
        return "invalid menu name";
    }

    public void changeNickname(ActionEvent actionEvent) {
        User user = UserController.getCurrentUser();
        String newNickname = nickname.getText();
        changeNickError.setText(null);
        changeNickSuccess.setText(null);

        if (!ProfileValidation.nicknameIsValid(newNickname))
            changeNickError.setText("invalid nickname : nickname can only have letters");
        else if (ProfileValidation.nicknameIsUsed(newNickname))
            changeNickError.setText("user with nickname " + newNickname + " already exists");
        else {
            user.setNickname(newNickname);
            changeNickSuccess.setText("nickname changed successfully!");
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Main.changeScene("mainMenu");
    }

    public void changePassword(ActionEvent actionEvent) {
        String oldPass = oldPassword.getText();
        String newPass = newPassword.getText();
        User user = UserController.getCurrentUser();
        changePassError.setText(null);
        changePassSuccess.setText(null);

        if (!user.getPassword().equals(oldPass))
            changePassError.setText("old password is invalid");
        else if (oldPass.equals(newPass))
            changePassError.setText("please enter a new password");
        else if (!ProfileValidation.passwordIsValid(newPass))
            changePassError.setText("invalid new password : must have at least 4" +
                    " characters (a capital and a small letter) and a number");
        else {
            user.setPassword(newPass);
            changePassSuccess.setText("Password changed successfully!");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        username.setText("\"" + UserController.getCurrentUser().getUsername() + "\"");
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
}

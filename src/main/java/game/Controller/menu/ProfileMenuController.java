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

public class ProfileMenuController  {

 public String menuNavigation(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("Profile Menu")
        || menuName.equals("game.Main Menu")
        || menuName.equals("Game Menu")
        || menuName.equals("Login Menu"))
            return "menu navigation is not possible";
        return "invalid menu name";
    }

}

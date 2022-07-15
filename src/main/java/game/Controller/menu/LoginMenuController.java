package game.Controller.menu;

import game.Controller.UserController;
import game.Enum.MenuName;
import game.Main;
import game.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Matcher;

public class LoginMenuController {

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

}

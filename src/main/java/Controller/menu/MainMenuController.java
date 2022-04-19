package Controller.menu;

import Controller.UserController;
import Enum.MenuName;

import java.util.regex.Matcher;

public class MainMenuController {
    public String exit() {
        MenuName.setCurrentMenu(MenuName.LOGIN_MENU);
        return "exited to Login Menu";
    }

    public String menuNavigation(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("Login Menu") || menuName.equals("Main Menu")) return "menu navigation is not possible";
        if (menuName.equals("Game Menu")) {
            if (UserController.getCurrentUser().isLoggedIn()) {
                MenuName.setCurrentMenu(MenuName.GAME_MENU);
                return "entered Game Menu";
            }
            else return "please login first";
        }
        if (menuName.equals("Profile Menu")) {
            if (UserController.getCurrentUser().isLoggedIn()) {
                MenuName.setCurrentMenu(MenuName.PROFILE_MENU);
                return "entered Profile Menu";
            }
            else return "please login first";
        }
        return "invalid command";
    }

    public String logout() {
        MenuName.setCurrentMenu(MenuName.LOGIN_MENU);
        UserController.getCurrentUser().setLoggedIn(false);
        return "user logged out successfully!";
    }
}

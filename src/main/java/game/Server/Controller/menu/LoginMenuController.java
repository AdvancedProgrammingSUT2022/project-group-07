package game.Server.Controller.menu;

import game.Server.Controller.UserController;
import game.Common.Enum.MenuName;

import java.util.regex.Matcher;

public class LoginMenuController {

    public String navigateMenu(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("game.Client.Main Menu")) {
            if (UserController.getCurrentUser() != null && UserController.getCurrentUser().isLoggedIn()) {
                MenuName.setCurrentMenu(MenuName.MAIN_MENU);
                return "entered game.Client.Main Menu!";
            }
            else return "please login first";
        }
        else if (menuName.equals("Profile Menu") || menuName.equals("Game Menu") || menuName.equals("Login Menu")) {
            return "menu navigation is not possible";
        }
        return "invalid menu name!";
    }

}

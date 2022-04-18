package Controller.menu;

import Enum.MenuName;

import java.util.regex.Matcher;

public class LoginMenuController {

    public String exit() {
        MenuName.setCurrentMenu(MenuName.TERMINATE);
        return "game ended!";
    }

    public String navigateMenu(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("Main menu")) {
            MenuName.setCurrentMenu(MenuName.MAIN_MENU);
            return "entered Main Menu!";
        }
        else if (menuName.equals("Profile Menu") || menuName.equals("Game Menu") || menuName.equals("Login Menu")) {
            return "menu navigation is not possible";
        }
        return "invalid menu name!";
    }


}

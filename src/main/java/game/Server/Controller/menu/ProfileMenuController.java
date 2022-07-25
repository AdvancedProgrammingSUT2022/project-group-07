package game.Server.Controller.menu;

import java.util.regex.Matcher;

public class ProfileMenuController  {

 public String menuNavigation(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("Profile Menu")
        || menuName.equals("game.Client.Main Menu")
        || menuName.equals("Game Menu")
        || menuName.equals("Login Menu"))
            return "menu navigation is not possible";
        return "invalid menu name";
    }

}

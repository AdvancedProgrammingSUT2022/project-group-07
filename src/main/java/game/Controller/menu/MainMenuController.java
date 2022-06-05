package game.Controller.menu;

import game.Controller.UserController;
import game.Controller.game.GameController;
import game.Controller.game.LogAndNotification.NotificationController;
import game.Enum.MenuName;
import game.Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class MainMenuController {
    public String exit() {
        MenuName.setCurrentMenu(MenuName.LOGIN_MENU);
        return "exited to Login Menu";
    }

    public String menuNavigation(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("Login Menu") || menuName.equals("game.Main Menu")) return "menu navigation is not possible";
        if (menuName.equals("Game Menu")) return "you have to create a game first!";
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
        UserController.setCurrentUser(null);
        return "user logged out successfully!";
    }

    public String playGame(Matcher matcher, GameController gameController) {
        String input = matcher.group();
        String[] listOfAllPlayers = input.split("--");
        HashMap<Integer , String> getPlayers = getPlayers(listOfAllPlayers);
        ArrayList<String> playerUsernames = sortPlayers(getPlayers);
        if (playerUsernames == null) return "entered wrong player numbers!";
        ArrayList<User> playerUsers = new ArrayList<>();
        playerUsers.add(UserController.getCurrentUser());
        for (String playerUsername : playerUsernames) {
            if (UserController.getUserByUsername(playerUsername) == null)
                return "user with username " + playerUsername + " does not exist!";
            playerUsers.add(UserController.getUserByUsername(playerUsername));
        }
        gameController.setPlayers(playerUsers);
        gameController.initialize();
        MenuName.setCurrentMenu(MenuName.GAME_MENU);
        NotificationController.runNotification(gameController);
        return "entered Game!";
    }


    private ArrayList<String> sortPlayers(HashMap<Integer, String> getPlayers) {
        ArrayList<String> sortPlayers = new ArrayList<>();
        for (int i = 1; i <= getPlayers.size(); i++) {
            if (getPlayers.get(i) == null) return null;
            sortPlayers.add(getPlayers.get(i));
        }
        return sortPlayers;
    }

    private HashMap<Integer, String> getPlayers(String[] listOfAllPlayers) {
        HashMap<Integer , String> getPlayers = new HashMap<>();
        for (int i = 1; i < listOfAllPlayers.length; i++) {
            getPlayers.put(Integer.parseInt(listOfAllPlayers[i].substring(6 , 7)) , listOfAllPlayers[i].substring(8).trim());
        }
        return getPlayers;
    }
}

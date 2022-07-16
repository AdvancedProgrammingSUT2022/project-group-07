package game.Controller.menu;

import game.Controller.UserController;
import game.Controller.game.GameController;
import game.Controller.game.LogAndNotification.NotificationController;
import game.Enum.MenuName;
import game.Main;
import game.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class MainMenuController {

    public Button gameMenu;
    public Button scoreBoard;
    public Button chatMenu;
    @FXML
    private Button back;
    @FXML
    private Button logout;
    @FXML
    private Button profileMenu;

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
                return "user with username  " + playerUsername + " does not exist!";
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

    public void logout(ActionEvent actionEvent) throws IOException {
        UserController.getCurrentUser().setLoggedIn(false);
        UserController.setCurrentUser(null);
        Main.changeScene("loginMenu");
    }

    public void goToProfile(ActionEvent actionEvent) throws IOException {
        Main.changeScene("profileMenu");
    }

    public void goToChatMenu(ActionEvent actionEvent) {
    }

    public void goToScoreBoard(ActionEvent actionEvent) throws IOException {
        Main.changeScene("scoreboardMenu") ;
    }

    public void goToGameMenu(ActionEvent actionEvent) throws IOException {
        Main.changeScene("gameMenu");
    }
}

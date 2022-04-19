package Controller.menu;

import Controller.game.GameController;
import Model.User;

import java.util.ArrayList;

public class GameMenuController {
    private static ArrayList<User> users = new ArrayList<>();

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<User> users) {
        GameMenuController.users = users;
    }

    GameController gameController = new GameController(users);
}

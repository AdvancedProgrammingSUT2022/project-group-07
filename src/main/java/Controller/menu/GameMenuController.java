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


    public String nextTurn(GameController gameController) {
        int index = gameController.getCivilizations().indexOf(gameController.getCurrentCivilization());
        if (index == gameController.getCivilizations().size() - 1) {
            gameController.setTurn(gameController.getTurn() + 1);
            gameController.setCurrentCivilization(gameController.getCivilizations().get(0));
            return "next player!\nnew turn!";
        }
        else {
            gameController.setCurrentCivilization(gameController.getCivilizations().get(index + 1));
            return "next player!";
        }
    }
}

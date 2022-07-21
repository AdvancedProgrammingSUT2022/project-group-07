package game.Controller.menu;

import game.Controller.game.*;
import game.Model.User;
import game.Enum.MenuName;
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
        String output;
        if (index == gameController.getCivilizations().size() - 1) {
            gameController.setTurn(gameController.getTurn() + 1);
            gameController.setCurrentCivilization(gameController.getCivilizations().get(0));
            output = "next player!\nnew turn!";
        }
        else {
            gameController.setCurrentCivilization(gameController.getCivilizations().get(index + 1));
            output = "next player!";
        }
        SelectController.selectedUnit = null;
        SelectController.selectedCity = null;
        //MapController.setMapCenter(gameController.getCurrentCivilization().getUnits().get(0).getLocation());
        CivilizationController.updateCivilizationElements(gameController);

        return output;
    }

    public String exit() {
        MenuName.setCurrentMenu(MenuName.MAIN_MENU);
        return "game ended!";
    }
}

package Controller.game;

import Model.Civilization;
import Model.Terrain;
import Model.User;

import java.util.ArrayList;

public class GameController {
    private Terrain[][] map;
    private static ArrayList<Civilization> civilizations;
    private int time;
    private int turn;

    public GameController(ArrayList<User> users) {
        //this.map = initilize();
    }

    public static ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }
}

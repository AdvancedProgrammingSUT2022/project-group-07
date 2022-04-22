package Controller.game;

import Model.Civilization;
import Model.Terrain;
import Model.User;

import java.util.ArrayList;

public class GameController {
    private Terrain[][] map;
    private static ArrayList<User> players = new ArrayList<>();
    private static ArrayList<Civilization> civilizations = new ArrayList<>();
    private int time;
    private int turn;


    public static ArrayList<User> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<User> players) {
        GameController.players = players;
    }

    public GameController(ArrayList<User> users) {
        //this.map = initilize();
    }

    public static ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }
}

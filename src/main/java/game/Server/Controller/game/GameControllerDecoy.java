package game.Server.Controller.game;

import game.Common.Enum.MapDimension;
import game.Common.Model.Civilization;
import game.Common.Model.Terrain;
import game.Common.Model.User;

import java.util.ArrayList;

public class GameControllerDecoy {
    public int mapWidth;
    public int mapHeight;
    public MapDimension mapDimension;
    public ArrayList<User> players;
    public ArrayList<Civilization> civilizations;
    public int time;
    public int turn;
    public Civilization currentCivilization;
    public Terrain[][] terrains ;

    public GameControllerDecoy (GameController gameController){
        this.mapWidth = gameController.getMapWidth() ;
        this.mapHeight = gameController.getMapHeight() ;
        this.mapDimension = gameController.getMapDimension() ;
        this.players = gameController.getPlayers() ;
        this.civilizations = gameController.getCivilizations() ;
        this.time = gameController.getTime() ;
        this.turn = gameController.getTurn() ;
        this.currentCivilization = gameController.getCurrentCivilization();
        this.terrains = gameController.getTerrains() ;

        System.out.println("current civ in decoy is " + currentCivilization);
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public MapDimension getMapDimension() {
        return mapDimension;
    }

    public ArrayList<User> getPlayers() {
        return players;
    }

    public ArrayList<Civilization> getCivilizations() {
        return civilizations;
    }

    public int getTime() {
        return time;
    }

    public int getTurn() {
        return turn;
    }

    public Civilization getCurrentCivilization() {
        return currentCivilization;
    }

    public Terrain[][] getTerrains() {
        return terrains;
    }
}
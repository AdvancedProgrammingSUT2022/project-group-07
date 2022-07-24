package game.Controller.game;

import com.google.gson.Gson;
import game.Controller.game.movement.TheShortestPath;
import game.Model.*;
import game.Enum.*;
import game.View.components.Tile;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

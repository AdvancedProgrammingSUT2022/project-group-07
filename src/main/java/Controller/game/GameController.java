package Controller.game;

import Model.Civilization;
import Model.Terrain;
import Model.User;

import java.util.ArrayList;

public class GameController {
    private Terrain[][] map;
    private ArrayList<Civilization> civilizations;
    private int time;
    private int turn;
    private Civilization currentCivilization ;

    private void initializeMap (){
        // TODO: iterate on array and create our map

    }

    private void initializeCivilizations (ArrayList<User> users){
        // TODO: create list of civilizations
        this.civilizations = new ArrayList<Civilization>();
        for (int i = 0; i < users.size(); i++)
            civilizations.add(new Civilization("c"+Integer.toString(i) , users.get(i))) ;
    }

    public GameController(ArrayList<User> users) {
        initializeCivilizations(users);
        //initializeMap();
    }




}

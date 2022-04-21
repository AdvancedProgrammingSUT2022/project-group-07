package Controller.game;

import Model.City;
import Model.Civilization;
import Model.Technology;

import java.util.ArrayList;

public class CivilizationController {
    private Civilization civilization;

    public void setCivilization(Civilization civilization){
        this.civilization=civilization;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public String research(Technology research){
        // TODO: complete this part !!
        return "ok ayoub!";
    }

    public void nextTurn(){
        // TODO: update all information and go to the next civilization
    }

    public void updateHappiness(){
        // TODO: update whole civilization happiness
    }

    public void updateFood(){
        // TODO: update whole civilization food
    }

    public void updateGold(){
        // TODO: update whole civilization gold
    }

    public void updateResearch(){
        // TODO: update civilization research status and turns
    }

    public void updateScience(){
        // TODO: 4/21/2022 update civilization total science
    }

    public void updateFogOfWar(){
        // TODO: update civilization fog of war using it's owned units across the whole map
    }


}

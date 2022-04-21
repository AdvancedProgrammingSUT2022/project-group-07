package Controller.game;

import Model.*;

import java.util.ArrayList;

public class CivilizationController {
    private Civilization civilization;

    public void setCivilization(Civilization civilization){
        this.civilization=civilization;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    /**
     * a function to add or replace current research with a new technology
     * @param research the new research
     * @return result of the command (set successfully , replaced successfully , already being researched)
     */
    public String research(Technology research){
        Technology currentResearch = civilization.getCurrentResearch();
        civilization.setCurrentResearch(research);

        if (currentResearch==null)
            return "research set successfully";

        else if (currentResearch.getTypeOfTechnology() == research.getTypeOfTechnology())
            return "you are already researching for this technology";
        else
            return "research "+currentResearch+" replaced with " + research;
    }

    public void nextTurn(){
        // TODO: update all information and go to the next civilization
    }

    /**
     * a function to update whole happiness of a civilization
     */
    public void updateHappiness(){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getHappiness();
        civilization.setHappiness(civilization.getHappiness()+sum);
    }

    /**
     * a function to update whole food of a civilization
     */
    public void updateFood(){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getFood();
        civilization.setFood(civilization.getFood()+sum);
    }

    /**
     *  a function to update whole gold of a civilization
     */
    public void updateGold(){
        ArrayList<City> cities = civilization.getCities();
        int sum = 0 ;
        for (City city : cities)
            sum += city.getGold();
        civilization.setGold(civilization.getGold()+sum);
    }

    /**
     * a function to update status of a current research
     */
    public void updateResearch(){
        Technology currentResearch = civilization.getCurrentResearch();
        currentResearch.setRemainingTurns(currentResearch.getRemainingTurns()-1);
    }

    public void updateScience(){
        // TODO: 4/21/2022 update civilization total science
    }

    public void updateFogOfWar(){
        // TODO: update civilization fog of war using it's owned units across the whole map
        // to do this , we first iterate on this civilization units and add radius 2 of
        // neighbouring cells to field (knowTerrains) of this civilization
    }


}

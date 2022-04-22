package Model;

import java.util.ArrayList;

public class Civilization {

    private User user;
    private ArrayList<City> cities ;
    private static ArrayList<Unit> units ;
    private ArrayList<Terrain> knownTerrains ;
    private ArrayList<Technology> gainedTechnologies ;
    private String name ;
    private City capital ;
    private int score ;
    private int food ;
    private int gold ;
    private int happiness ;
    private int science ;
    private Technology currentResearch ;


    public Civilization (User user , String name , City capital){
        this.user = user;
        this.name = name ;
        this.cities = new ArrayList<City>();
        this.cities.add(capital);
        units = new ArrayList<Unit>();
        this.knownTerrains = new ArrayList<Terrain>();
        this.gainedTechnologies = new ArrayList<Technology>();
        this.capital = capital;
        this.score = 0 ;
        this.gold = 0 ;
        this.food = 0 ;
        this.happiness = 0 ;
        this.science = 0 ;
        this.currentResearch = null ;
    }


    /**
     * a function to add a city to arrayList of cities
     * @param city
     */
    public void addCity(City city){
        this.cities.add(city) ;
    }

    /**
     * a function to remove a city from arrayList of cities
     * @param city
     */
    public void removeCity(City city){
        this.cities.remove(city) ;
    }

    /**
     * a function to add a Unit to arrayList of units
     * @param unit
     */
    public void addUnit(Unit unit){
        this.units.add(unit) ;
    }

    /**
     * a function to remove a Unit from arrayList of units
     * @param unit
     */
    public void removeUnit(Unit unit){
        this.units.remove(unit);
    }

    /**
     * a function to add a terrain to arrayList of know terrains
     * @param terrain
     */
    public void addKnownTerrain(Terrain terrain){
        this.knownTerrains.add(terrain);
    }

    /**
     * a function to add a technology to arrayList of knownTechnologies
     * @param technology
     */
    public void addTechnology(Technology technology){
        this.gainedTechnologies.add(technology);
    }

    /**
     * a function to get list of all cities
     * @return
     */
    public ArrayList<City> getCities(){
        return this.cities;
    }

    /**
     * a function to get list of all units
     * @return
     */
    public ArrayList<Unit> getUnits(){
        return units;
    }

    /**
     * a function to get list of knownTerrains
     * @return
     */
    public ArrayList<Terrain> getKnownTerrains(){
        return this.knownTerrains;
    }

    /**
     * a function to get list of gainedTechnologies
     * @return
     */
    public ArrayList<Technology> getGainedTechnologies(){
        return this.gainedTechnologies;
    }
    public User getUser() {
        return user;
    }
    public int getFood() {
        return food;
    }
    public int getGold() {
        return gold;
    }
    public int getHappiness() {
        return happiness;
    }
    public String getName() {
        return name;
    }
    public int getScience() {
        return science;
    }
    public Technology getCurrentResearch() {
        return currentResearch;
    }
    public City getCapital() {
        return capital;
    }
    public int getScore() {
        return score;
    }

}

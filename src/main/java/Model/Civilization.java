package Model;

import java.util.ArrayList;

public class Civilization {

    private ArrayList<City> cities ;
    private ArrayList<Unit> units ;
    // known terrain is a revealed tile , a civilization can see it's typeOfTerrain all the times
    // but it may not see it's improvements or ownership
    private ArrayList<Terrain> knownTerrains ;
    // visible terrain is a tile very close to a unit or a terrain of civilization
    // a civilization can always see all improvements and ownership while a unit is near this tile
    private ArrayList<Terrain> visibleTerrains ;
    private ArrayList<Technology> gainedTechnologies ;
    private String name ;
    private City capital ;
    private int score ;
    private int food ;
    private int gold ;
    private int happiness ;
    private int science ;
    private Technology currentResearch ;
    private User owner ;

    public Civilization (String name , User owner){
        this.owner = owner;
        this.name = name ;
        this.cities = new ArrayList<City>();
        this.units = new ArrayList<Unit>();
        this.knownTerrains = new ArrayList<Terrain>();
        this.gainedTechnologies = new ArrayList<Technology>();
        this.capital = null;
        this.score = 0 ;
        this.gold = 0 ;
        this.food = 0 ;
        this.happiness = 0 ;
        this.science = 0 ;
        this.currentResearch = null ;
    }
    public Civilization (String name , City capital , User owner){
        this.owner = owner;
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
        if (!knownTerrains.contains(terrain))
            this.knownTerrains.add(terrain);
    }

    /**
     * a function to add a technology to arrayList of knownTechnologies
     * @param technology
     */
    public void addTechonolgy(Technology technology){
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
        return this.units;
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
    public ArrayList<Technology> getGainedTechnologies(){ return this.gainedTechnologies; }

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

    public void setScore(int score) {
        this.score = score;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public void setScience(int science) {
        this.science = science;
    }

    public void setCurrentResearch(Technology currentResearch) {
        this.currentResearch = currentResearch;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public void setVisibleTerrains (ArrayList<Terrain> visibleTerrains){
        this.visibleTerrains = visibleTerrains ;
    }

    public ArrayList<Terrain> getVisibleTerrains(){
        return this.visibleTerrains;
    }
}

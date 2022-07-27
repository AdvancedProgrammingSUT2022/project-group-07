package game.Common.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import game.Common.Enum.TypeOfRelation;
import game.Common.Enum.Resources;
import game.Common.Enum.TypeOfTechnology;

public class Civilization {

    private ArrayList<City> cities ;
    private ArrayList<Unit> units ;
    // known terrain is a revealed tile , a civilization can see it's typeOfTerrain all the times
    // but it may not see it's improvements or ownership
    private ArrayList<Terrain> knownTerrains ;
    // visible terrain is a tile very close to a unit or a terrain of civilization
    // a civilization can always see all improvements and ownership while a unit is near this tile
    private ArrayList<Terrain> visibleTerrains = new ArrayList<>() ;
    private ArrayList<Technology> gainedTechnologies;
    private ArrayList<TypeOfTechnology> gainedTypeOfTechnologies = new ArrayList<>() ;
    private String name ;
    private City capital ;
    private int score ;
    private int food ;
    private int gold ;
    private int happiness ;
    private int science ;
    private Technology currentResearch ;
    private User owner ;
    private int numberOfRailroadsAndRoads;
    private ArrayList<Route> routsAboutToBeBuilt;
    private ArrayList<Improvement> improvementsAboutToBeCreated;
    private HashMap<String , TypeOfRelation> relationWithOtherCivilizations = new HashMap<>() ;
    private ArrayList<DiplomacyRequest> diplomacyRequests = new ArrayList<>() ;

    public Civilization (String name , User owner){
        this.owner = owner;
        this.name = name ;
        this.cities = new ArrayList<City>();
        this.units = new ArrayList<Unit>();
        this.knownTerrains = new ArrayList<Terrain>();
        this.gainedTechnologies = new ArrayList<Technology>();
        this.gainedTypeOfTechnologies = new ArrayList<TypeOfTechnology>();
        this.capital = null;
        this.score = 0 ;
        this.gold = 0 ;
        this.food = 0 ;
        this.happiness = 10 ;
        this.science = 0 ;
        this.currentResearch = null ;
        this.routsAboutToBeBuilt = new ArrayList<>();
        this.improvementsAboutToBeCreated = new ArrayList<>();
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
        this.routsAboutToBeBuilt = new ArrayList<>();
        this.improvementsAboutToBeCreated = new ArrayList<>();
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
    public void addTechnology(Technology technology){
        this.gainedTypeOfTechnologies.add(technology.getTypeOfTechnology());
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

    public ArrayList<TypeOfTechnology> getGainedTypeOfTechnologies() {
        return this.gainedTypeOfTechnologies ;
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
        if (currentResearch ==null)
            return;
        if (this.currentResearch != null)
            this.science += this.currentResearch.getTypeOfTechnology().getCost() ;
        this.science -= currentResearch.getTypeOfTechnology().getCost();
        this.currentResearch = currentResearch;
    }

    public void setCapital(City capital) {
        this.capital = capital;
    }

    public int getNumberOfRailroadsAndRoads() {
        return numberOfRailroadsAndRoads;
    }

    public void setNumberOfRailroadsAndRoads(int numberOfRailroadsAndRoads) {
        this.numberOfRailroadsAndRoads = numberOfRailroadsAndRoads;
    }

    public void setVisibleTerrains (ArrayList<Terrain> visibleTerrains){
        this.visibleTerrains = visibleTerrains ;
    }

    public ArrayList<Terrain> getVisibleTerrains(){
        return this.visibleTerrains;
    }

    public ArrayList<Route> getRoutsAboutToBeBuilt() {
        return routsAboutToBeBuilt;
    }

    public void setRoutsAboutToBeBuilt(ArrayList<Route> roadsAboutToBeBuilt) {
        this.routsAboutToBeBuilt = roadsAboutToBeBuilt;
    }

    public ArrayList<Improvement> getImprovementsAboutToBeCreated() {
        return improvementsAboutToBeCreated;
    }

    public void setImprovementsAboutToBeCreated(ArrayList<Improvement> improvementsAboutToBeCreated) {
        this.improvementsAboutToBeCreated = improvementsAboutToBeCreated;
    }

    public void addImprovementsAboutToBeCreated(Improvement improvement) {
        this.improvementsAboutToBeCreated.add(improvement);
    }

    public void addRoutsAboutToBeBuilt(Route rout) {
        this.routsAboutToBeBuilt.add(rout);
    }

    public void addRelationWithCivilization (TypeOfRelation typeOfRelation , Civilization civilization){
        this.relationWithOtherCivilizations.put(civilization.getName() , typeOfRelation);
    }

    public HashMap<String , TypeOfRelation> getRelationWithOtherCivilizations() {
        return relationWithOtherCivilizations;
    }

    public TypeOfRelation getRelationWithCivilization (Civilization civilization){
        return relationWithOtherCivilizations.get(civilization.getName());
    }

    public void addResource (Resources resource){
        if (cities.size() == 0 )
            return;
        Random random = new Random() ;
        ArrayList<Terrain> terrains = this.cities.get(random.nextInt(this.cities.size())).getTerrains() ;
        terrains.get(random.nextInt(terrains.size())).setResources(resource);
    }

    public void removeResource (Resources resources){
        for (City city : this.cities) {
            for (Terrain terrain : city.getTerrains()) {
                if (terrain.getResources() != null && terrain.getResources().equals(resources)) {
                    terrain.setResources(null);
                    return;
                }
            }
        }
    }

    public ArrayList<Resources> getAllAvailableResources (){
        ArrayList<Resources> out = new ArrayList<>();
        for (City city : this.cities) {
            for (Terrain terrain : city.getTerrains()) {
                if (terrain.getResources() != null && !out.contains(terrain.getResources()))
                    out.add(terrain.getResources());
            }
        }
        return out ;
    }

    public ArrayList<DiplomacyRequest> getDiplomacyRequests() {
        return diplomacyRequests;
    }
    public void addDiplomacyRequest (DiplomacyRequest diplomacyRequest){
        this.diplomacyRequests.add(diplomacyRequest);
    }
    public void removeDiplomacyRequest (DiplomacyRequest diplomacyRequest){
        this.diplomacyRequests.remove(diplomacyRequest);
    }

    public User getOwner() {
        return owner ;
    }
}

package Model;

import java.util.ArrayList;

public class City {

    private ArrayList<Terrain> terrains ;
    private ArrayList<Citizen> citizens ;
    private String name ;
    private int happiness ;
    private int food ;
    private int gold ;
    private int production ;
    private int hp ;
    private Civilization ownership ;

    public City (String name , Civilization ownership){
        this.terrains = new ArrayList<Terrain>();
        this.citizens = new ArrayList<Citizen>();
        this.citizens.add(new Citizen());
        this.name = name ;
        this.happiness = 0 ;
        this.food = 0 ;
        this.gold = 0 ;
        this.production = 0 ;
        this.hp = 0 ;
        this.ownership = ownership;
    }


    public String getName() {
        return name;
    }
    public int getHappiness() {
        return happiness;
    }
    public int getGold() {
        return gold;
    }
    public int getFood() {
        return food;
    }
    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }
    public ArrayList<Terrain> getTerrains() {
        return terrains;
    }
    public Civilization getOwnership() {
        return ownership;
    }
    public int getHp() {
        return hp;
    }

}

package Model;

import Enum.TypeOfTechnology;

public class Technology {

    private TypeOfTechnology typeOfTechnology;
    private int remainingTurns ;

    private Location location;

    public Technology(TypeOfTechnology typeOfTechnology){
        this.typeOfTechnology = typeOfTechnology;
        this.remainingTurns = typeOfTechnology.getTurnsNeeded() ;
    }

    public TypeOfTechnology getTypeOfTechnology(){return typeOfTechnology;}

    public int getRemainingTurns() {return remainingTurns;}

    public void setRemainingTurns(int turns){
        this.remainingTurns = turns;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

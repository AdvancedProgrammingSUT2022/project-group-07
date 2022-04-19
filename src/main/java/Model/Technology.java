package Model;

import Enum.TypeOfTechnology;

public class Technology {

    private TypeOfTechnology typeOfTechnology;
    private int remainingTurns ;

    public Technology(TypeOfTechnology typeOfTechnology){
        this.typeOfTechnology = typeOfTechnology;
        this.remainingTurns = typeOfTechnology.getTurnsNeeded() ;
    }

    public TypeOfTechnology getTypeOfTechnology(){return typeOfTechnology;}

    public int getRemainingTurns() {return remainingTurns;}

}

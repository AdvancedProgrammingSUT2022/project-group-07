package Model;

public class Technology {

    private TypeOfTechnology typeOfTechnology;
    private int remainingTurns ;

    public Technology(TypeOfTechnology typeOfTechnology){
        this.typeOfTechnology = typeOfTechnology;
        this.remainingTurns = typeOfTechnology.turnsNeeded ;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }
}

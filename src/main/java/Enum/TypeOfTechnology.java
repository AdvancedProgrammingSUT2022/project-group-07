package Enum;

public enum TypeOfTechnology {

    AGRICULTURE("Agriculture" , 20 , new TypeOfTechnology[]{} , 8 , 2) ,
    ANIMAL_HUSBANDRY("Animal Husbandry" , 35 , new TypeOfTechnology[]{AGRICULTURE} , 8 , 2 ) ,
    MINING("Mining" , 35 , new TypeOfTechnology[]{AGRICULTURE} , 10 , 2) ,
    POTTERY("Pottery" , 35 , new TypeOfTechnology[]{AGRICULTURE} , 10 , 2) ,
    ARCHERY("Archery" , 35 , new TypeOfTechnology[]{AGRICULTURE} , 10 , 2) ,
    BRONZE_WORKING("Bronze Working" , 55 , new TypeOfTechnology[]{MINING} , 8 , 2) ,
    CALENDAR("Calendar" , 70 , new TypeOfTechnology[]{POTTERY} , 10 , 2 ) ,
    MASONRY("Masonry" , 55 , new TypeOfTechnology[]{MINING} , 10 , 2) ,
    THE_WHEEL("The Wheel" , 55 , new TypeOfTechnology[]{ANIMAL_HUSBANDRY} , 10 , 2) ,
    TRAPPING("Trapping" , 55 , new TypeOfTechnology[]{ANIMAL_HUSBANDRY} , 10 , 2) ,
    WRITING("Writing" , 55 , new TypeOfTechnology[]{POTTERY} , 10 , 2) ,
    CONSTRUCTION("Construction" , 100 , new TypeOfTechnology[]{MASONRY} , 12 , 2) ,
    HORSEBACK_RIDING("Horseback Riding" , 100 , new TypeOfTechnology[]{THE_WHEEL} , 10 , 2) ,
    IRON_WORKING("Iron Working" , 150 , new TypeOfTechnology[]{BRONZE_WORKING} , 10 , 2 ) ,
    MATHEMATICS("Mathematics" , 100 , new TypeOfTechnology[]{ARCHERY , THE_WHEEL} , 10 , 2) ,
    PHILOSOPHY("Philosophy" , 100 , new TypeOfTechnology[]{WRITING} , 10 , 2) ,
    CIVIL_SERVICE("Civil Service" , 400 , new TypeOfTechnology[]{PHILOSOPHY , TRAPPING} , 10 , 2) ,
    CURRENCY("Currency" , 250 , new TypeOfTechnology[]{MATHEMATICS} , 10 , 2) ,
    CHIVALRY("Chivalry" , 440 , new TypeOfTechnology[]{CIVIL_SERVICE , HORSEBACK_RIDING, CURRENCY} , 12, 2) ,
    ENGINEERING("Engineering" , 250 , new TypeOfTechnology[]{MATHEMATICS , CONSTRUCTION} , 10 , 2) ,
    MACHINERY("Machinery" , 440 , new TypeOfTechnology[]{ENGINEERING} , 10 ,2) ,
    METAL_CASTING("Metal Casting" , 240 , new TypeOfTechnology[]{IRON_WORKING} , 10 , 2) ,
    PHYSICS("Physics" , 440 , new TypeOfTechnology[]{ENGINEERING , METAL_CASTING} , 10 , 2) ,
    STEEL("Steel" , 440 , new TypeOfTechnology[]{METAL_CASTING} , 10 , 2) ,
    THEOLOGY("Theology" , 250 , new TypeOfTechnology[]{CALENDAR , PHILOSOPHY} , 10 , 2) ,
    EDUCATION("Education" , 440 , new TypeOfTechnology[]{THEOLOGY} , 10 , 2) ,
    ACOUSTICS("Acoustics" , 650 , new TypeOfTechnology[]{EDUCATION} , 12 , 2) ,
    ARCHAEOLOGY("Archaeology" , 1300 , new TypeOfTechnology[]{ACOUSTICS} , 12 , 2) ,
    BANKING("Banking" , 650 , new TypeOfTechnology[]{EDUCATION , CHIVALRY} , 12 , 2) ,
    GUNPOWDER("Gunpowder" , 680 , new TypeOfTechnology[]{PHYSICS , STEEL} , 12 , 2) ,
    CHEMISTRY("Chemistry" , 900 , new TypeOfTechnology[]{GUNPOWDER} , 12 , 2) ,
    PRINTING_PRESS("Printing Press" , 650 , new TypeOfTechnology[]{MACHINERY , PHYSICS} , 12 , 2) ,
    ECONOMICS("Economics" , 900 , new TypeOfTechnology[]{BANKING , PRINTING_PRESS} , 12 , 2) ,
    FERTILIZER("Fertilizer" , 1300 , new TypeOfTechnology[]{CHEMISTRY} , 12 , 2) ,
    METALLURGY("Metallurgy" , 900 , new TypeOfTechnology[]{GUNPOWDER} , 12 , 2) ,
    MILITARY_SCIENCE("Military Science" , 1300 , new TypeOfTechnology[]{ECONOMICS , CHEMISTRY} , 12 ,2 ) ,
    RIFLING("Rifling" , 1425 , new TypeOfTechnology[]{METALLURGY} , 12 , 2)  ,
    SCIENTIFIC_THEORY("Scientific Theory" , 1300 , new TypeOfTechnology[]{ACOUSTICS} , 12 ,2) ,
    BIOLOGY("Biology" , 1680 , new TypeOfTechnology[]{ARCHAEOLOGY , SCIENTIFIC_THEORY} , 14 , 2) ,
    STEAM_POWER("Steam Power" , 1680 , new TypeOfTechnology[]{SCIENTIFIC_THEORY , MILITARY_SCIENCE} , 14 , 2) ,
    DYNAMITE("Dynamite" , 1900 , new TypeOfTechnology[]{FERTILIZER , RIFLING} , 14 , 2) ,
    ELECTRICITY("Electricity" , 1900 , new TypeOfTechnology[]{BIOLOGY , STEAM_POWER} , 14 , 2) ,
    RADIO("Radio" , 2200 , new TypeOfTechnology[]{ELECTRICITY} , 14 , 2) ,
    RAILROAD("Railroad" , 1900 , new TypeOfTechnology[]{STEAM_POWER} , 14 , 2) ,
    REPLACEABLE_PARTS("Replaceable Parts" , 1900 , new TypeOfTechnology[]{STEAM_POWER} , 14 , 2) ,
    COMBUSTION("Combustion" , 2200 , new TypeOfTechnology[]{REPLACEABLE_PARTS , RAILROAD , DYNAMITE} , 14 , 2) ,
    TELEGRAPH("Telegraph" , 2200 , new TypeOfTechnology[]{ELECTRICITY} , 14 , 2) ;


    TypeOfTechnology(String name , int cost , TypeOfTechnology[] prerequisiteTech,
                     int turnsNeeded , int scienceNeeded){
        this.name = name ;
        this.cost = cost ;
        this.prerequisiteTech = prerequisiteTech;
        this.turnsNeeded = turnsNeeded;
        this.scienceNeeded = scienceNeeded;
    }

    private final String name ;
    private final int cost ;
    private final TypeOfTechnology[] prerequisiteTech ;
    private final int turnsNeeded;
    private final int scienceNeeded;

    public String getName(){return name;}

    public int getCost() {
        return cost;
    }

    public TypeOfTechnology[] getPrerequisiteTech() {return prerequisiteTech;}

    public int getTurnsNeeded() {return turnsNeeded;}

    public int getScienceNeeded() {return scienceNeeded;}


}

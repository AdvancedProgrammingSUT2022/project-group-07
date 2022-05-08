package Controller.game;

import Model.*;

import java.util.ArrayList;

public class Information {

    public static void researchInformation (Civilization civilization){
        String civilizationScience = Integer.toString(civilization.getScience());
        String currentResearch = ResearchController.showCurrentResearch(civilization) ;
        String ownedResearches = ResearchController.showOwnedResearch(civilization) ;
        if (ownedResearches.equalsIgnoreCase("no technology"))
            ownedResearches = "\n" + ownedResearches;
        ownedResearches = ownedResearches
                .replace("list of owned technologies : \n" , "")
                .replace("\n" , "\n\t");
        String availableResearches = ResearchController.showAvailableResearch(civilization);
        availableResearches = availableResearches
                .replace("list of available researches :" , "")
                .replace("\n" , "\n\t") ;

        System.out.println("Research Information Panel of civilization "+ civilization.getName());
        System.out.println("total science : " + civilizationScience);
        System.out.println("current research :\n\t" + currentResearch);
        System.out.println("list of achieved technologies :\t" + ownedResearches);
        System.out.println("list of available technologies to research :\t" + availableResearches);
    }

    public static void unitsInformation (Civilization civilization){
        ArrayList<Unit> units = civilization.getUnits();
        System.out.println("Units Information Panel of civilization "+ civilization.getName());
        for (Unit unit : units) {
            System.out.printf("Unit %s : \n" , unit.getTypeOfUnit().getName());
            System.out.printf("\tLocation : (%d,%d)\n" , unit.getLocation().getX() , unit.getLocation().getY());
            System.out.printf("\tStatus : %s\n" , unit.getUnitStatus());
            System.out.printf("\tHp : %d\n" , unit.getHp());
            System.out.printf("\tMp : %d\n" , unit.getMp());
        }
    }

    public static void citiesInformation (Civilization civilization){
        System.out.println("Cities Information Panel of civilization "+ civilization.getName());
        ArrayList<City> cities = civilization.getCities() ;
        for (City city : cities) {
            System.out.printf("\tcity %s\n" , city.getName());
            System.out.printf("\t\tfood : %d\n" , city.getFood());
            System.out.printf("\t\tgold : %d\n" , city.getGold());
            System.out.printf("\t\tproduction : %d\n" , city.getProduction());
            System.out.printf("\t\tpopulation : %d\n" , city.getCitizens().size());
            System.out.printf("\t\tturns till city growth : %d\n" , city.getTurnsTillGrowth());
        }
    }
}

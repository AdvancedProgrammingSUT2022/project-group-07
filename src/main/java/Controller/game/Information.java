package Controller.game;

import Controller.game.LogAndNotification.NotificationController;
import Model.*;
import Enum.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Information {

    public static void researchInfo (Civilization civilization) {
        ArrayList<TypeOfTechnology> ownedTypeOfTechnologies = civilization.getGainedTypeOfTechnologies();
        String civilizationScience = Integer.toString(civilization.getScience());
        String currentResearch = ResearchController.showCurrentResearch(civilization);
        System.out.println("total science : " + civilization.getScience());
        System.out.println("current research :\n" + currentResearch);
        System.out.println("list of owned technologies : ");
        for (TypeOfTechnology ownedTypeOfTechnology : ownedTypeOfTechnologies) {
            System.out.printf("\t%s\n", ownedTypeOfTechnology.getName());
            System.out.println("\t\tunlocked buildings : ");
            for (Building building : Building.values()) {
                if (building.getTechnologyRequired() == null
                        || ownedTypeOfTechnologies.contains(building.getTechnologyRequired()))
                    System.out.printf("\t\t\t%s\n", building);
            }
        }
        System.out.println("list of available technologies : ");
        for (TypeOfTechnology typeOfTechnology : TypeOfTechnology.values()) {
            if (ownedTypeOfTechnologies.contains(typeOfTechnology))
                continue;
            TypeOfTechnology[] needed = typeOfTechnology.getPrerequisiteTech();
            if (needed == null) {
                String turns = civilization.getScience() != 0 ? Integer.toString(typeOfTechnology.getCost() / civilization.getScience()) : "Somewhere around 30'th century";
                System.out.printf("\t%-10s : %s\n", typeOfTechnology.getName(), turns);
                continue;
            }
            boolean condition = true;
            for (TypeOfTechnology technology : needed)
                condition &= ownedTypeOfTechnologies.contains(technology);
            if (condition) {
                String turns = civilization.getScience() != 0 ? Integer.toString(typeOfTechnology.getCost() / civilization.getScience()) : "Somewhere around 30'th century";
                System.out.printf("\t%-10s : %s\n", typeOfTechnology.getName(), turns);
            }
        }
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
            System.out.printf("\tTimes Moved This Turn : %d\n" , unit.getTimesMovedThisTurn());
        }
    }

    public static void citiesInformation (Civilization civilization){
        System.out.println("Cities Information Panel of civilization "+ civilization.getName());
        ArrayList<City> cities = civilization.getCities() ;
        for (City city : cities) {
            System.out.printf("\tCity %s\n" , city.getName());
            System.out.printf("\t\tFood : %d\n" , city.getFood());
            System.out.printf("\t\tGold : %d\n" , civilization.getGold());
            System.out.printf("\t\tProduction : %d\n" , city.getProduction());
            System.out.printf("\t\tPopulation : %d\n" , city.getCitizens().size());
            System.out.printf("\t\tTurns till city growth : %d\n" , city.getTurnsTillGrowth());
            System.out.printf("\t\tScience achievement : +%d\n" , city.getCitizens().size()+5);
        }
    }

    public static void demographicsInformation (ArrayList<Civilization> civilizations , Civilization civilization){
        System.out.println("Demographics Information Panel of civilization " + civilization.getName());
        ArrayList<City> cities = civilization.getCities();
        int numberOfTilesOwned = 0 ;
        for (City city : cities)
            numberOfTilesOwned += city.getTerrains().size();
        float progress = ((float) numberOfTilesOwned*100) / (float)(GameController.getMapHeight()*GameController.getMapWidth()) ;

        System.out.println("\tTotal gold : " + civilization.getGold());
        System.out.println("\tTotal food : " + civilization.getFood());
        System.out.println("\tHappiness index : " + civilization.getHappiness());
        System.out.println("\tCities : " + cities.size());
        System.out.println("\tTiles Owned : " + numberOfTilesOwned);
        System.out.println("\tRoads and Railroads : " + civilization.getNumberOfRailroadsAndRoads());
        System.out.printf("\tTotal Progress : %.2f%%\n" , progress);

        HashMap<Civilization , Float> progressMap = new HashMap<>();
        for (Civilization civilization1 : civilizations) {
            cities = civilization1.getCities();
            numberOfTilesOwned = 0 ;
            for (City city : cities)
                numberOfTilesOwned += city.getTerrains().size();
            progressMap.put(civilization1
                    , ((float) numberOfTilesOwned*100) / (float)(GameController.getMapHeight()*GameController.getMapWidth()));
        }
        Map<Civilization, Float> sortedMap =
                progressMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        System.out.println("Ranking : ");
        AtomicInteger i = new AtomicInteger(sortedMap.size()+1);
        sortedMap.forEach((key, value) ->
                System.out.printf("%d\tCivilization %s : %.2f%%\n" , i.decrementAndGet() , key.getName() , value)
        );
    }

    public static void militaryInformation(Civilization civilization){
        System.out.println("Military Information Panel of civilization " + civilization.getName());
        for (Unit unit : civilization.getUnits()) {
            if (unit.getTypeOfUnit()==TypeOfUnit.WORKER || unit.getTypeOfUnit()==TypeOfUnit.SETTLER)
                continue;
            String unitType = unit.getTypeOfUnit().getName() ;
            int hp = unit.getHp() ;
            int mp = unit.getHp() ;
            UnitStatus status = unit.getUnitStatus();
            Location location = unit.getLocation();
            System.out.printf("\tmilitary unit %s : \n" , unitType);
            System.out.printf("\t\tLocation : (%d,%d)\n" , location.getX() , location.getY());
            System.out.println("\t\tStatus : " + status);
            System.out.println("\t\tHp : " + hp);
            System.out.println("\t\tMp : " + mp);
        }
    }

    public static void notificationHistory(int currentTurn , Civilization civilization){
        ArrayList<Notification> currentCivilizationNotifications = NotificationController.getNotifications().get(civilization) ;
        for (int i=currentCivilizationNotifications.size()-1 ; i>=0 ; i--) {
            Notification currentCivilizationNotification = currentCivilizationNotifications.get(i);
            System.out.printf("%s\n\t%3d turns ago at %s\n"
                    , currentCivilizationNotification.getMessage()
                    , currentTurn - currentCivilizationNotification.getTurnOfCreation()
                    , currentCivilizationNotification.getRealTimeCreated());
        }
    }

}

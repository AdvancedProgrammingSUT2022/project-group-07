package Controller.game.LogAndNotification;

import Controller.game.GameController;
import Model.*;
import Enum.TypeOfTechnology;
import Enum.TypeOfUnit;
import Enum.TypeOfImprovement;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationController {
    private static GameController gameController ;
    private static final HashMap<Civilization , ArrayList<Notification>> notifications = new HashMap<>() ;

    public static void runNotification (final GameController gameController){
        NotificationController.gameController = gameController ;
        for (Civilization civilization : gameController.getCivilizations())
            notifications.put(civilization , new ArrayList<>());
    }

    public static void logResearchStarted (final  TypeOfTechnology technology , final  Civilization civilization){
        String message = "research " + technology.getName() + " set successfully !";
        setNotification(civilization , message);
    }

    public static void logResearchReplaced (final  TypeOfTechnology current , final TypeOfTechnology last , final Civilization civilization){
        String message = "research " + last.getName() + " replaced with " + current.getName() + " successfully !";
        setNotification(civilization , message);
    }

    public static void logResearchCompleted(final TypeOfTechnology technology , final Civilization civilization){
        String message = "Technology "+technology.getName()+" was achieved !" ;
        setNotification(civilization , message);
    }

    public static void logUnitCreationStarted (final  TypeOfUnit typeOfUnit , final Civilization civilization){
        String message = "Unit " + typeOfUnit.getName() + " set successfully !";
        setNotification(civilization , message);
    }

    public static void logUnitCreated (final TypeOfUnit typeOfUnit , final Civilization civilization){
        String message = "Unit " + typeOfUnit.getName() + " was created !" ;
        setNotification(civilization , message);
    }

    public static void logCityFounded (final City city , final Civilization civilization){
        String message = "City " + city.getName() + " was founded !" ;
        setNotification(civilization , message);
    }

    public static void logUnitStatusChanged (final Unit unit){
        String message = "Unit " + unit.getTypeOfUnit().getName() + " changed status to " + unit.getUnitStatus() ;
        setNotification(unit.getCivilization() , message);
    }

    public static void logNewTileAddedToCity (final Terrain tile , City city){
        String message = "Tile " + tile.getLocation().getX() + "," + tile.getLocation().getY()
                + " added to city " + city.getName() + " !";
        setNotification(city.getOwnership() , message);
    }

    public static void logNewCitizenAddedToCity (final City city){
        String message = "New citizen added to city " + city.getName() + " !" ;
        setNotification(city.getOwnership() , message);
    }

    public static void logScienceLossBecauseOfGoldDebt (final int amountOfLoss , final Civilization civilization){
        String message = "Lost " + amountOfLoss + " of science dut to gold debt !";
        setNotification(civilization,message);
    }

    public static void logNewImprovementStarted(final TypeOfImprovement typeOfImprovement , final Civilization civilization){
        String message = "Workers started working on " + typeOfImprovement.getName() + " !";
        setNotification(civilization,message);
    }

    public static void logImprovementCreated (final TypeOfImprovement typeOfImprovement , final Civilization civilization){
        String message = "Improvement " + typeOfImprovement.getName() + " created successfully !";
        setNotification(civilization,message);
    }

    public static HashMap<Civilization, ArrayList<Notification>> getNotifications() {
        return notifications;
    }

    public static void setNotification (Civilization civilization , String message ){
        notifications.get(civilization).add(new Notification(message , gameController.getTurn()));
    }

}

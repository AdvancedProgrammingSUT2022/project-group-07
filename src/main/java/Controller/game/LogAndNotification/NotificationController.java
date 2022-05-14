package Controller.game.LogAndNotification;

import Controller.game.GameController;
import Model.City;
import Model.Civilization;
import Model.Notification;
import Enum.TypeOfTechnology;
import Enum.TypeOfUnit;

import java.util.ArrayList;
import java.util.HashMap;

public class NotificationController {
    private static GameController gameController ;
    private static HashMap<Civilization , ArrayList<Notification>> notifications = new HashMap<>() ;

    public static void runNotification (final GameController gameController){
        NotificationController.gameController = gameController ;
        for (Civilization civilization : gameController.getCivilizations())
            notifications.put(civilization , new ArrayList<>());
    }

    public static void logResearchCompleted(final TypeOfTechnology technology , final Civilization civilization){
        String message = "Technology "+technology.getName()+" was achieved !" ;
        notifications.get(civilization).add(new Notification(message , gameController.getTurn()));
    }

    public static void logUnitCreated (final TypeOfUnit typeOfUnit , final Civilization civilization){
        String message = "Unit " + typeOfUnit.getName() + " was created !" ;
        notifications.get(civilization).add(new Notification(message , gameController.getTurn()));
    }

    public static void logCityFounded (final City city , final Civilization civilization){
        String message = "City " + city.getName() + " was founded !" ;
        notifications.get(civilization).add(new Notification(message , gameController.getTurn()));
    }

    public static HashMap<Civilization, ArrayList<Notification>> getNotifications() {
        return notifications;
    }
}

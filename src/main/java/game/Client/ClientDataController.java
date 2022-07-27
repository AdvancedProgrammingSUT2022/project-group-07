package game.Client;

import game.Client.View.components.Tile;
import game.Common.Model.Civilization;
import game.Common.Model.User;
import game.Server.Controller.Chat.Message;
import game.Server.Controller.game.*;
import game.Server.Controller.game.movement.TheShortestPath;

import java.util.Map;

public class ClientDataController {

    public static User currentUser ;
    public static Civilization thisCivilization ;

    public static GameController gameController ;
    public static String uuid ;

    public static Message lastMessageToAttend ;

    public static void setLastMessageToAttend(Message lastMessageToAttend) {
        ClientDataController.lastMessageToAttend = lastMessageToAttend;
    }

    public static GameController getGameController() {
        return gameController;
    }

    public static void setGameController(GameController gameController) {
        ClientDataController.gameController = gameController;
    }

    public static void setUuid (String uuid){
        ClientDataController.uuid = uuid ;
    }

    public static void updateThisCivilization (){
        for (Civilization civilization : gameController.getCivilizations()) {
            if (civilization.getOwner().getUsername().equals(currentUser.getUsername()))
                thisCivilization = civilization ;
        }
    }

    public static Civilization getThisCivilization() {
        return thisCivilization;
    }

    public static void setGameController(GameControllerDecoy gameControllerDecoy){
        if (gameController == null) gameController = new GameController();

        gameController.mapDimension = gameControllerDecoy.getMapDimension() ;
        gameController.mapWidth = gameControllerDecoy.getMapWidth() ;
        gameController.mapHeight = gameControllerDecoy.getMapHeight();
        gameController.players = gameControllerDecoy.getPlayers() ;
        gameController.civilizations = gameControllerDecoy.getCivilizations() ;
        gameController.time = gameControllerDecoy.getTime() ;
        gameController.turn = gameControllerDecoy.getTurn() ;
        gameController.currentCivilization = gameControllerDecoy.getCurrentCivilization();
        gameController.terrains = gameControllerDecoy.getTerrains() ;
        ClientDataController.updateThisCivilization();
        loadTilesFromTerrains();
        CivilizationController.updateFogOfWar(ClientDataController.getThisCivilization() , gameController.map , gameController.getMapWidth() , gameController.getMapHeight());
    }

    private static void loadTilesFromTerrains(){
        gameController.map = new Tile[gameController.getMapHeight()][gameController.getMapWidth()];
        for (int y = 0; y < gameController.getMapHeight(); y++) {
            for (int x = 0; x < gameController.getMapWidth(); x++)
                gameController.map[y][x] = TileController.createTile(gameController.getTerrains()[y][x] , x , y);
        }
        CivilizationController.updateFogOfWar(ClientDataController.getThisCivilization() , gameController.map , gameController.getMapWidth() , gameController.getMapHeight());
        TheShortestPath.run();
        MapController.setBackgroundsInClientSize(gameController , gameController.map);
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}

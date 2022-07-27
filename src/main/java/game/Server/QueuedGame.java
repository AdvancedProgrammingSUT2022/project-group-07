package game.Server;

import game.Common.Enum.TypeOfResponse;
import game.Common.Model.ServerResponse;
import game.Common.Model.User;
import game.Server.Controller.UserController;
import game.Server.Controller.game.GameController;
import game.Server.Controller.game.GameControllerDecoy;
import game.Server.Controller.game.LogAndNotification.NotificationController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class QueuedGame extends Thread {

    private final String UUID;
    HashSet<String> playersUsernames = new HashSet<>() ;
    HashSet<String> playersJoinedTheGame = new HashSet<>() ;

    GameController gameController ;

    public QueuedGame (ArrayList<String> players){
        this.playersUsernames = new HashSet<>(players);
        this.UUID = String.valueOf(java.util.UUID.randomUUID());
    }

    public String getUUID() {
        return UUID;
    }

    public HashSet<String> getPlayersJoinedTheGame() {
        return playersJoinedTheGame;
    }

    public HashSet<String> getPlayersUsernames() {
        return playersUsernames;
    }

    public void addPlayer (String username){
        playersJoinedTheGame.add(username);
    }

    public void removePlayer (String username){
        playersJoinedTheGame.remove(username);
    }

    @Override
    public void run() {
        while (!playersJoinedTheGame.containsAll(playersUsernames)) {
            try {Thread.sleep(1000);}
            catch (Exception ignored) {}
        }

        System.out.println("all players have joined the queued game with UUID : " + UUID);

        ArrayList<User> players = new ArrayList<>();
        for (String playersUsername : playersUsernames)
            players.add(UserController.getUserByUsername(playersUsername));

        gameController = new GameController();
        GameController.setInstance(gameController);
        gameController.setPlayers(players);
        gameController.initialize();
        NotificationController.runNotification(gameController);

        System.out.println("a game created in server side");
        GameControllerDecoy gameControllerDecoy = new GameControllerDecoy(gameController);
        System.out.println("decoy of it is created in server side");

    for (String playersUsername : playersUsernames) {
            // send a notification to all players to start the game
            try {
                ServerMain.getServerHandlerByUsername(playersUsername).sendResponse(new ServerResponse(TypeOfResponse.START_GAME, gameControllerDecoy, UUID));
                System.out.println("a copy of decoy was sent to " + playersUsername);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public GameController getGameController() {
        return gameController;
    }
}

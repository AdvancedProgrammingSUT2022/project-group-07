package game.Server;

import java.util.ArrayList;

public class QueuedGamesController {

    public static ArrayList<QueuedGame> queuedGames = new ArrayList<>();

    public static void addQueuedGame (QueuedGame queuedGame){
        queuedGames.add(queuedGame);
    }

    public static ArrayList<QueuedGame> getQueuedGames() {
        return queuedGames;
    }

    public static ArrayList<QueuedGame> getQueuedGamesForPlayer (String username){
        ArrayList<QueuedGame> out = new ArrayList<>();
        for (QueuedGame queuedGame : queuedGames) {
            if (queuedGame.getPlayersUsernames().contains(username))
                out.add(queuedGame);
        }
        return out ;
    }

    public static QueuedGame getQueuedGameByUUID (String UUID){
        for (QueuedGame queuedGame : queuedGames) {
            if (queuedGame.getUUID().equals(UUID))
                return queuedGame;
        }
        return null ;
    }
}

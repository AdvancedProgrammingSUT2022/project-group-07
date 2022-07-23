package game.View.controller;

import game.Controller.game.GameController;
import game.Controller.game.LogAndNotification.NotificationController;
import game.Main;

import java.io.IOException;

public class loadGameMenu {

    public void loadSample() throws IOException {

//        GameController gameController = GameController.getInstance();
        GameController gameController = GameController.getInstance() ;
        gameController.loadGame("sample");

        gameController.setPlayers(gameController.getPlayers());
//        gameController.initialize();

        NotificationController.runNotification(gameController);
        Main.changeScene("gamePage");

//        GameController.getInstance().loadGame("sample");
//        Main.changeScene("gamePage");
    }

}

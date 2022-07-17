import game.Controller.game.GameController;
import game.Model.User;

import java.util.ArrayList;

public class mapTest {
    public static void main(String[] args) {
        System.out.println("salam ayoub");
        ArrayList<User> users = new ArrayList<User>() ;
        users.add(new User("mahan" , "mahan1234" , "aqa mahan")) ;
        users.add(new User("na mahan" , "mahan4321" , "kie")) ;
        GameController gameController = GameController.getInstance();
        gameController.setPlayers(users);
        gameController.initialize();
//        gameController.printMap();
    }
}

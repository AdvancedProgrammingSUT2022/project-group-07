import Controller.game.GameController;
import Model.User;

import java.util.ArrayList;

public class mapTest {
    public static void main(String[] args) {
        System.out.println("salam ayoub");
        ArrayList<User> users = new ArrayList<User>() ;
        users.add(new User("mahan" , "mahan1234" , "aqa mahan")) ;
        GameController gameController = new GameController(users) ;
        gameController.printMap();
    }
}

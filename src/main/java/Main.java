import Controller.UserController;
import Controller.game.GameController;
import Controller.menu.GameMenuController;
import Controller.menu.LoginMenuController;
import Controller.menu.MainMenuController;
import Controller.menu.ProfileMenuController;
import View.*;
import Enum.MenuName;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        LoginMenu loginMenu = new LoginMenu(scanner , new LoginMenuController());
        GameController gameController = new GameController();
        MainMenu mainMenu = new MainMenu(scanner , new MainMenuController() , gameController);
        ProfileMenu profileMenu = new ProfileMenu(scanner , new ProfileMenuController());
        GameMenu gameMenu = new GameMenu(scanner , new GameMenuController() , gameController);
        UserController.loadUsers();

        while(MenuName.getCurrentMenu() != MenuName.TERMINATE) {
            loginMenu.run();
            mainMenu.run();
            profileMenu.run();
            gameMenu.run();
        }
        System.out.println("khaste nabashi ayoub jan!");
    }
}

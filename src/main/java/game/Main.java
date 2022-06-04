package game;

import game.Controller.UserController;
import game.Controller.game.GameController;
import game.Controller.menu.GameMenuController;
import game.Controller.menu.LoginMenuController;
import game.Controller.menu.MainMenuController;
import game.Controller.menu.ProfileMenuController;
import game.View.*;
import game.Enum.MenuName;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args){
        launch();
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

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }
}

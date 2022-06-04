package game;

import game.Controller.UserController;
import game.Controller.game.GameController;
import game.Controller.menu.GameMenuController;
import game.Controller.menu.LoginMenuController;
import game.Controller.menu.MainMenuController;
import game.Controller.menu.ProfileMenuController;
import game.Enum.MenuName;
import game.View.GameMenu;
import game.View.LoginMenu;
import game.View.MainMenu;
import game.View.ProfileMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Main extends Application {

    private static Stage mainStage;

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
        mainStage = stage;
        mainStage.setResizable(false);
        Parent root = loadFXML("loginPage");
        stage.setTitle("game");
        Scene scene = new Scene(root);
        UserController.loadUsers();
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String name){
        try {
            URL address = new URL(Main.class.getResource("/game/fxml/" + name + ".fxml").toExternalForm());
            return FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeWindow() {
        mainStage.close();
    }

    public static void changeScene(String fxmlName) throws IOException {
        Parent root = loadFXML(fxmlName);
        mainStage.getScene().setRoot(root);
    }
}

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
        MainMenu mainMenu = new MainMenu(scanner , new MainMenuController());
        ProfileMenu profileMenu = new ProfileMenu(scanner , new ProfileMenuController());
        GameMenu gameMenu = new GameMenu(scanner , new GameMenuController());

        while(MenuName.getCurrentMenu() != MenuName.TERMINATE) {
            LoginMenu.run();
            MainMenu.run();
            ProfileMenu.run();
            GameMenu.run();
        }
        System.out.println("khaste nabashi ayoub jan!");
    }
}

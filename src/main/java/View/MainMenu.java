package View;

import Controller.menu.MainMenuController;
import Enum.MenuName;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private final MainMenuController mainMenuController;
    private static Scanner scanner;
    public MainMenu(Scanner scanner , MainMenuController mainMenuController) {
        MainMenu.scanner = scanner;
        this.mainMenuController = mainMenuController;
    }

    public static void run() {
        String input;
        Matcher matcher;

        while (MenuName.getCurrentMenu() == MenuName.MAIN_MENU) {
            //TODO commands!
        }
    }
}

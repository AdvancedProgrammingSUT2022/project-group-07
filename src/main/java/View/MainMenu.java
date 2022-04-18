package View;

import Controller.menu.MainMenuController;
import Enum.MenuName;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Menu{
    private final MainMenuController mainMenuController;

    public MainMenu(Scanner scanner , MainMenuController mainMenuController) {
        super(scanner);
        this.mainMenuController = mainMenuController;
    }

    public void run() {
        String input;
        Matcher matcher;
        while (MenuName.getCurrentMenu() == MenuName.MAIN_MENU) {
            input = scanner.nextLine();
            //TODO commands!
        }
    }
}

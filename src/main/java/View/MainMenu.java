package View;

import Controller.menu.MainMenuController;
import Enum.MenuName;
import Enum.regexes.MainMenuCommands;

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

            if (MainMenuCommands.getMatcher(input , MainMenuCommands.EXIT) != null) {

            }
            else if ((matcher = MainMenuCommands.getMatcher(input , MainMenuCommands.MENU_NAVIGATION)) != null) {

            }
            else if ((matcher = MainMenuCommands.getMatcher(input , MainMenuCommands.SHOW_CURRENT_MENU)) != null) {

            }
            else if ((matcher = MainMenuCommands.getMatcher(input , MainMenuCommands.LOGOUT)) != null) {

            }
            else if ((matcher = MainMenuCommands.getMatcher(input , MainMenuCommands.PLAY_GAME)) != null) {

            }
            else System.out.println("invalid command from ayoub");
        }
    }
}

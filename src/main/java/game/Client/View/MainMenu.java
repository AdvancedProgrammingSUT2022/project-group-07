package game.Client.View;

import game.Server.Controller.game.GameController;
import game.Server.Controller.menu.MainMenuController;
import game.Common.Enum.MenuName;
import game.Common.Enum.regexes.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu extends Menu{
    private final MainMenuController mainMenuController;
    private GameController gameController;
    public MainMenu(Scanner scanner, MainMenuController mainMenuController, GameController gameController) {
        super(scanner);
        this.mainMenuController = mainMenuController;
        this.gameController = gameController;
    }

    public void run() {
        String input;
        Matcher matcher;
        while (MenuName.getCurrentMenu() == MenuName.MAIN_MENU) {
            input = scanner.nextLine();

            if (MainMenuCommands.getMatcher(input , MainMenuCommands.EXIT) != null) {
//                String result = mainMenuController.exit();
//                System.out.println(result);
            }
            else if ((matcher = MainMenuCommands.getMatcher(input , MainMenuCommands.MENU_NAVIGATION)) != null) {
                String result = mainMenuController.menuNavigation(matcher);
                System.out.println(result);
            }
            else if ((matcher = MainMenuCommands.getMatcher(input , MainMenuCommands.SHOW_CURRENT_MENU)) != null) {
                System.out.println("game.Client.Main Menu");
            }
//            else if ((matcher = MainMenuCommands.getMatcher(input , MainMenuCommands.LOGOUT)) != null) {
//                String result = mainMenuController.logout();
//                System.out.println(result);
//            }
            else if ((matcher = MainMenuCommands.getMatcher(input , MainMenuCommands.PLAY_GAME)) != null) {
                String result = mainMenuController.playGame(matcher , gameController);
                System.out.println(result);
            }
            else System.out.println("invalid command from ayoub");
        }
    }
}
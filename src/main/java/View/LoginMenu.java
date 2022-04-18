package View;
import Controller.menu.GameMenuController;
import Controller.menu.LoginMenuController;
import Enum.MenuName;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {

    private final LoginMenuController loginMenuController;
    private static Scanner scanner;
    public LoginMenu(Scanner scanner , LoginMenuController loginMenuController) {
        this.scanner = scanner;
        this.loginMenuController = loginMenuController;
    }

    public static void run() {
        String input;
        Matcher matcher;

        while (MenuName.getCurrentMenu() == MenuName.LOGIN_MENU) {
            input = scanner.nextLine();
            //TODO menu commands
        }
    }
}

package View;
import Controller.menu.GameMenuController;
import Controller.menu.LoginMenuController;
import Enum.MenuName;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends Menu{

    private final LoginMenuController loginMenuController;
    public LoginMenu(Scanner scanner , LoginMenuController loginMenuController) {
        super(scanner);
        this.loginMenuController = loginMenuController;
    }

    public  void run() {
        String input;
        Matcher matcher;

        while (MenuName.getCurrentMenu() == MenuName.LOGIN_MENU) {
            input = scanner.nextLine();
            //TODO menu commands
        }
    }
}

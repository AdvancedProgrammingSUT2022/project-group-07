package View;
import Controller.menu.GameMenuController;
import Controller.menu.LoginMenuController;
import Enum.MenuName;
import Enum.regexes.LoginMenuCommands;

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
            if (LoginMenuCommands.getMatcher(input , LoginMenuCommands.EXIT) != null) {
                String result = loginMenuController.exit();
                System.out.println(result);
            }
            else if (LoginMenuCommands.getMatcher(input , LoginMenuCommands.SHOW_CURRENT_MENU) != null){
                System.out.println("Login Menu");
            }
            else if ((matcher = LoginMenuCommands.getMatcher(input , LoginMenuCommands.MENU_NAVIGATION)) != null) {
                String result = loginMenuController.navigateMenu(matcher);
                System.out.println(result);
            }
            // create user :

            else if ((matcher = LoginMenuCommands.getMatcher(input , LoginMenuCommands.CREATE_USER_1)) != null
            || (matcher = LoginMenuCommands.getMatcher(input , LoginMenuCommands.CREATE_USER_2)) != null
            || (matcher = LoginMenuCommands.getMatcher(input , LoginMenuCommands.CREATE_USER_3)) != null
            || (matcher = LoginMenuCommands.getMatcher(input , LoginMenuCommands.CREATE_USER_4)) != null
            || (matcher = LoginMenuCommands.getMatcher(input , LoginMenuCommands.CREATE_USER_5)) != null
            || (matcher = LoginMenuCommands.getMatcher(input , LoginMenuCommands.CREATE_USER_6)) != null) {

            }
            // login :

            else if ((matcher = LoginMenuCommands.getMatcher(input , LoginMenuCommands.LOGIN_1)) != null
            || (matcher = LoginMenuCommands.getMatcher(input , LoginMenuCommands.LOGIN_2)) != null) {

            }
            else System.out.println("invalid command from ayoub");

        }
    }
}

package View;

import Controller.menu.ProfileMenuController;
import Controller.menu.ProfileValidation;
import Enum.MenuName;
import Enum.regexes.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Menu{
    private final ProfileMenuController profileMenuController;

    public ProfileMenu(Scanner scanner, ProfileMenuController profileMenuController){
        super(scanner);
        this.profileMenuController = profileMenuController;
    }

    public void run() {
        String input;
        Matcher matcher;

        while (MenuName.getCurrentMenu() == MenuName.PROFILE_MENU) {
            input = scanner.nextLine();
            if (ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.EXIT) != null) {
                String result = profileMenuController.exit();
                System.out.println(result);
            }
            else if (ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.SHOW_CURRENT_MENU) != null){
                System.out.println("Profile Menu");
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.MENU_NAVIGATION)) != null) {
                String result = profileMenuController.menuNavigation(matcher);
                System.out.println(result);
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.CHANGE_NICKNAME)) != null) {
                String result = profileMenuController.changeNickname(matcher);
                System.out.println(result);
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.CHANGE_PASSWORD_1)) != null
            || (matcher = ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.CHANGE_PASSWORD_2)) != null) {
                String result = profileMenuController.changePassword(matcher);
                System.out.println(result);
            }
            else System.out.println("invalid command from ayoub");
        }
    }
}

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
        // TODO errors for changing pass and nickname
        while (MenuName.getCurrentMenu() == MenuName.PROFILE_MENU) {
            input = scanner.nextLine();
            if (ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.EXIT) != null) {

            }
            else if (ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.SHOW_CURRENT_MENU) != null){

            }
            else if ((matcher = ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.MENU_NAVIGATION)) != null) {

            }
            else if ((matcher = ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.CHANGE_NICKNAME)) != null) {
                if (ProfileValidation.nicknameIsValid(matcher.group("nickname"))
                        && !ProfileValidation.nicknameIsUsed(matcher.group("nickname"))) {
                    // TODO change nickname
                }
                else System.out.println("invalid nickname");
            }
            else if ((matcher = ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.CHANGE_PASSWORD_1)) != null
            || (matcher = ProfileMenuCommands.getMatcher(input , ProfileMenuCommands.CHANGE_PASSWORD_2)) != null) {
                if (ProfileValidation.passwordIsValid(matcher.group("newPassword"))) {
                    // TODO see if oldPass is correct and update
                }
                else System.out.println("password is not valid!");
            }
            else System.out.println("invalid command from ayoub");
        }
    }
}

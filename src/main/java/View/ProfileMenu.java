package View;

import Controller.menu.ProfileMenuController;
import Enum.MenuName;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Menu{
    private final ProfileMenuController profileMenuController;
    private static Scanner scanner;
    public ProfileMenu(Scanner scanner, ProfileMenuController profileMenuController){
        super(scanner);
        this.profileMenuController = profileMenuController;
    }

    public void run() {
        String input;
        Matcher matcher;
        while (MenuName.getCurrentMenu() == MenuName.PROFILE_MENU) {
            input = scanner.nextLine();
        }
    }
}

package View;

import Controller.menu.ProfileMenuController;

import java.util.Scanner;

public class ProfileMenu {
    private final ProfileMenuController profileMenuController;
    private Scanner scanner;
    public ProfileMenu(Scanner scanner, ProfileMenuController profileMenuController){
        this.scanner = scanner;
        this.profileMenuController = profileMenuController;
    }

    public static void run() {

    }
}

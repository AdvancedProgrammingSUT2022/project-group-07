package Controller.menu;

import Controller.UserController;
import Enum.MenuName;
import Model.User;

import java.util.regex.Matcher;

public class ProfileMenuController {
    public String exit() {
        MenuName.setCurrentMenu(MenuName.MAIN_MENU);
        return "entered Main Menu";
    }

    public String menuNavigation(Matcher matcher) {
        String menuName = matcher.group("menuName");
        if (menuName.equals("Profile Menu")
        || menuName.equals("Main Menu")
        || menuName.equals("Game Menu")
        || menuName.equals("Login Menu"))
            return "menu navigation is not possible";
        return "invalid menu name";
    }

    public String changeNickname(Matcher matcher) {
        User user = UserController.getCurrentUser();
        String newNickname = matcher.group("nickname");
        if (!ProfileValidation.nicknameIsValid(newNickname))
            return "invalid nickname : nickname can only have letters";
        if (ProfileValidation.nicknameIsUsed(newNickname))
            return "user with nickname " + newNickname + " already exists";
        user.setNickname(newNickname);
        return "nickname changed successfully!";
    }

    public String changePassword(Matcher matcher) {
        String currentPassword = matcher.group("currentPassword");
        String newPassword = matcher.group("newPassword");
        User user = UserController.getCurrentUser();

        if (!user.getPassword().equals(currentPassword))
            return "current password is invalid";
        if (currentPassword.equals(newPassword))
            return "please enter a new password";
        if (!ProfileValidation.passwordIsValid(newPassword))
            return "new password is invalid : password must have at least 4 characters and a capital letter, small letter and a number";
        user.setPassword(newPassword);
        return "password changed successfully!";
    }
}

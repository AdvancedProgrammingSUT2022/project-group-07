package game.Controller.menu;

import game.Controller.UserController;
import game.Model.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileValidation {
    // Note: username --> 4 or more  chars and should contain at least a letter!
    //       nickname --> 3 or more english chars
    //       pass --> bishtar az 4 char + 1 or more harfe bozorge english , harfe kuchike english va number (dige no limits)

    public static boolean usernameIsValid(String username) {
        Pattern usernamePattern = Pattern.compile("(?=.*[a-zA-Z].*)[a-zA-Z]{4,}");
        Matcher usernameMatcher = usernamePattern.matcher(username);
        return usernameMatcher.matches();
    }

    public static boolean passwordIsValid(String password) {
        Pattern passwordPattern = Pattern.compile("(?=.*[a-z].*)(?=.*[A-Z].*)(?=.*[0-9].*)[a-zA-Z0-9]{4,}");
        Matcher passwordMatcher = passwordPattern.matcher(password);
        return passwordMatcher.matches();
    }

    public static boolean nicknameIsValid(String nickname) {
        Pattern nicknamePattern = Pattern.compile("[A-Za-z]{3,}");
        Matcher nicknameMatcher = nicknamePattern.matcher(nickname);
        return nicknameMatcher.matches();
    }

    public static boolean usernameIsUsed(String username) {
        ArrayList<User> users = UserController.getUsers();
        if (users == null) return false;
        for (User user : users) {
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }

    public static boolean nicknameIsUsed(String nickname) {
        ArrayList<User> users = UserController.getUsers();
        if (users == null) return false;
        for (User user : users) {
            if (user.getNickname().equals(nickname)) return true;
        }
        return false;
    }

}

package Controller.menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileValidation {
    // Note: username --> 4 or more english chars hatman and whatever
    //       nickname --> 3 or more english chars
    //       pass --> bishtar az 4 char + 1 or more harfe bozorge english , harfe kuchike english va number (dige no limits)
    public static Boolean usernameIsValid(String username) {
        Pattern usernamePattern = Pattern.compile("(?=.*[a-zA-Z]{4,}.*)");
        Matcher usernameMatcher = usernamePattern.matcher(username);
        return usernameMatcher.matches();
    }

    public static Boolean passwordIsValid(String password) {
        return (password.length() >= 4 &&
                hasRegex(password, "[A-Z]") &&
                hasRegex(password, "[a-z]") &&
                hasRegex(password, "[0-9]"));
    }

    public static Boolean nicknameIsValid(String nickname) {
        Pattern nicknamePattern = Pattern.compile("[A-Za-z]{3,}");
        Matcher nicknameMatcher = nicknamePattern.matcher(nickname);
        return nicknameMatcher.matches();
    }

    public static Boolean usernameIsUsed(String username) {
        // TODO check
        return false;
    }

    public static Boolean nicknameIsUsed(String nickname) {
        // TODO check
        return false;
    }
    // TODO : didn't have this method in UML!! HANDLE later
    private static boolean hasRegex(String password, String regex) {
        return password.matches(".*" + regex + ".*");
    }
}

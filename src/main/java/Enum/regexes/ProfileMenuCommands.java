package Enum.regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    // TODO : -n ya -p nadashtebashim?
    EXIT("menu exit"),
    MENU_NAVIGATION("enter menu (?<menuName>\\S+)"),
    SHOW_CURRENT_MENU("menu show-current"),
    CHANGE_NICKNAME("profile change --nickname (?<nickname>\\S+)"),
    CHANGE_PASSWORD_1("profile change --password (--current|-c) (?<currentPassword>\\S+) (--new|-n) (?<newPassword>\\S+)"),
    CHANGE_PASSWORD_2("profile change --password (--new|-n) (?<newPassword>\\S+) (--current|-c) (?<currentPassword>\\S+)");

    private String regex;

    public String getRegex() {
        return regex;
    }

    ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input , ProfileMenuCommands command) {
        Matcher matcher = Pattern.compile(command.getRegex()).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}

package game.Common.Enum.regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    EXIT("menu exit"),
    MENU_NAVIGATION("enter menu (?<menuName>.+)"),
    SHOW_CURRENT_MENU("menu show-current"),
    CHANGE_NICKNAME("profile change (--nickname|-n) (?<nickname>\\S+)"),
    CHANGE_PASSWORD_1("profile change --password (--current|-c) (?<currentPassword>\\S+) (--new|-n) (?<newPassword>\\S+)"),
    CHANGE_PASSWORD_2("profile change --password (--new|-n) (?<newPassword>\\S+) (--current|-c) (?<currentPassword>\\S+)"),
    CHANGE_USERNAME("profile change (--username|-u) \\S+");
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

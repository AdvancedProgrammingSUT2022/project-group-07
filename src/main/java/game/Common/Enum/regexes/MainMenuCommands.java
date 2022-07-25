package game.Common.Enum.regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {

    EXIT("menu exit"),
    MENU_NAVIGATION("enter menu (?<menuName>.+)"),
    SHOW_CURRENT_MENU("menu show-current"),
    LOGOUT("user logout"),
    PLAY_GAME("play game( --player\\d+ \\S+)+");

    private String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static Matcher getMatcher(String input , MainMenuCommands command) {
        Matcher matcher = Pattern.compile(command.getRegex()).matcher(input);
        if(matcher.matches()) return matcher;
        return null;
    }
}

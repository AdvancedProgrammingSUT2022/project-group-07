package Enum.regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CityMenuCommands {

    EXIT("exit"),
    EMPLOY_CITIZEN_TO_TILE("assign citizen(?<number>\\d+) to terrain (?<X>\\d+) (?<Y>\\d+)"),
    UNLOCK_CITIZEN("unlock citizen(?<number>\\d+)"),
    CREATE_UNIT("create unit (?<unit>[a-zA-Z]+)"),
    BUY_UNIT_WITH_GOLD("buy unit with gold"),
    CHANGE_UNIT_CONSTRUCTION("change unit construction (?<first>\\d+) with (?<second>\\d+)"),
    REMOVE_UNIT_CONSTRUCTION("remove unit construction (?<number>\\d+)");

    private String regex;

    CityMenuCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
    public static Matcher getMatcher(String input , CityMenuCommands command) {
        Matcher matcher = Pattern.compile(command.getRegex()).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}

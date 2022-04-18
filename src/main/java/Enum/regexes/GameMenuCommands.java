package Enum.regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {

    // menu commands : !!!
    EXIT("menu exit"),
    MENU_NAVIGATION("enter menu (?<menuName>\\S+)"),
    SHOW_CURRENT_MENU("menu show-current"),
    //info commands : !!!!!

    INFO_RESEARCH("info research"),
    INFO_UNITS("info units"),
    INFO_CITIES("info cities"),
    INFO_DIPLOMACY("info diplomacy"),
    INFO_VICTORY("info victory"),
    INFO_DEMOGRAPHICS("info demographics"),
    INFO_NOTIFICATIONS("info notifications"),
    INFO_MILITARY("info military"),
    INFO_ECONOMIC("info economic"),
    INFO_DIPLOMATIC("info diplomatic"),
    INFO_DEALS("info deals"),

    // selection commands : !!!!

    SELECT_UNIT_COMBAT("select unit combat (?<X>\\d+) (?<Y>\\d+)"),
    SELECT_UNIT_NONCOMBAT("select unit noncombat (?<X>\\d+) (?<Y>\\d+)"),
    SELECT_CITY("select city (?<cityName>[a-zA-Z]+)|((?<X>\\d+) (?<Y>\\d+))"), // ham mitoone ba esm ham ba location select kone!

    // unit commands : !!!

    UNIT_MOVE_TO("unit move to (?<X>\\d+) (?<Y>\\d+)"),
    UNIT_SLEEP("unit sleep"),
    UNIT_ALERT("unit alert"),
    UNIT_FORTIFY("unit fortify ?(heal)"), // mitoone ta full shodan heal fortify beshe ya nashe!!
    UNIT_GARRISON("unit garrison"),
    UNIT_SETUP_RANGED("unit setup ranged"),
    UNIT_ATTACK("unit attack (?<X>\\d+) (?<Y>\\d+)"),
    UNIT_FOUND_CITY("unit found city"),
    UNIT_CANCEL_MISSION("unit cancel mission"),
    UNIT_WAKE("unit wake"),
    UNIT_DELETE("unit delete"),

                   // az inja male kargaras !!!
    UNIT_BUILD_IMPROVEMENT("unit build (?<improvement>[a-zA-Z]+)"), // baraye zirbakhsh be khode typeOfImrpovement morajeE shavad!
    UNIT_REMOVE_JUNGLE("unit remove jungle"),
    UNIT_REMOVE_ROUTE("unit remove route"),
    UNIT_REPAIR("unit repair"),

    // map commands : !!!!

    MAP_SHOW("map show (?<cityName>[a-zA-Z]+)|((?<X>)\\d+ (?<Y>\\d+))"), // ham mishe city ham location ro neshoon dad!
    MAP_MOVE("map move (?<direction>[RLUD])");

    private String regex;

    GameMenuCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public static Matcher getMatcher(String input , GameMenuCommands command) {
        Matcher matcher = Pattern.compile(command.getRegex()).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}

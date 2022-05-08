package Enum.regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {

    // menu commands : !!!
    EXIT("menu exit"),
    MENU_NAVIGATION("enter menu (?<menuName>.+)"),
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
    SELECT_CITY_BY_LOCATION("select city (?<X>\\d+) (?<Y>\\d+)"), // ham mitoone ba esm ham ba location select kone!
    SELECT_CITY_BY_NAME("select city (?<cityName>[a-zA-Z0-9\\s]+)") ,

    // city commands : !!!
    CITY_BUY_TILE("city buy tile (?<X>\\d+) (?<Y>\\d+)") ,
    CITY_SHOW_TILES_OWNED("city show tiles owned") ,
    CITY_SHOW_TILES_AVAILABLE("city show tiles available") ,

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
    UNIT_SIEGE("siege units (?<X>\\d+) (?<Y>\\d+)"),

    // az inja male kargaras !!!
    UNIT_BUILD_IMPROVEMENT("unit build (?<improvement>[a-zA-Z]+)"), // baraye zirbakhsh be khode typeOfImrpovement morajeE shavad!
    CREATE_UNIT("create unit (?<unit>[a-zA-Z]+)"),
    BUY_UNIT_WITH_GOLD("buy unit (?<unitNumber>\\d+) with gold"),
    UNIT_REMOVE_JUNGLE("unit remove (?<feature>jungle)"),
    UNIT_REMOVE_FOREST("unit remove (?<feature>forest)"),
    UNIT_REMOVE_ROUTE("unit remove route"),
    UNIT_REPAIR("unit repair"),
    UNIT_BUILD_ROAD("unit build (?<name>road)"),
    UNIT_BUILD_RAILROAD("unit build (?<name>railroad)"),
    UNIT_REMOVE_MARSH("unit remove (?<feature>marsh)"),
    // research commands : !!!
    RESEARCH_TECHNOLOGY("research technology (?<technologyName>\\w+)") ,
    RESEARCH_SHOW_CURRENT("research show current") ,
    RESEARCH_SHOW_AVAILABLE("research show available") ,
    RESEARCH_SHOW_OWNED("research show owned") ,

    // map commands : !!!!

    MAP_SHOW_CITY("map show (?<cityName>[a-zA-Z]+)"), // ham mishe city ham location ro neshoon dad!
    MAP_SHOW_LOCATION("map show (?<X>\\d+) (?<Y>\\d+)") ,
    MAP_MOVE("map move (?<direction>[RLUD])"),

    // cheats : !!
    INCREASE_TURN("increase turn (?<amount>\\d+)"),
    INCREASE_GOLD("increase gold (?<amount>\\d+)"),
    INCREASE_SCIENCE("increase science (?<amount>\\d+)"),
    INCREASE_PRODUCTION("increase production (?<amount>\\d+)"),
    INCREASE_FOOD("increase food (?<amount>\\d+)"),
    INCREASE_HAPPINESS("increase happiness (?<amount>\\d+)"),
    // next turn : !!
    NEXT_TURN("next turn");

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

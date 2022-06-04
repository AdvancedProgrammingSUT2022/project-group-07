package game.Enum.regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {

    EXIT("menu exit"),
    MENU_NAVIGATION("enter menu (?<menuName>.+)"),
    SHOW_CURRENT_MENU("menu show-current"),

    // user creating commands !!!!

    CREATE_USER_1("user create (--username|-u) (?<username>\\S+) (--nickname|-n) (?<nickname>\\S+) (--password|-p) (?<password>\\S+)"),
    CREATE_USER_2("user create (--username|-u) (?<username>\\S+) (--password|-p) (?<password>\\S+) (--nickname|-n) (?<nickname>\\S+)"),
    CREATE_USER_3("user create (--password|-p) (?<password>\\S+) (--username|-u) (?<username>\\S+) (--nickname|-n) (?<nickname>\\S+)"),
    CREATE_USER_4("user create (--password|-p) (?<password>\\S+) (--nickname|-n) (?<nickname>\\S+) (--username|-u) (?<username>\\S+)"),
    CREATE_USER_5("user create (--nickname|-n) (?<nickname>\\S+) (--password|-p) (?<password>\\S+) (--username|-u) (?<username>\\S+)"),
    CREATE_USER_6("user create (--nickname|-n) (?<nickname>\\S+) (--username|-u) (?<username>\\S+) (--password|-p) (?<password>\\S+)"),

    // user login commands !!!

    LOGIN_1("user login (--username|-u) (?<username>\\S+) (--password|-p) (?<password>\\S+)"),
    LOGIN_2("user login (--password|-p) (?<password>\\S+) (--username|-u) (?<username>\\S+)");


    private String regex;

    public String getRegex() {
        return regex;
    }

    LoginMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input , LoginMenuCommands command){
        Matcher matcher = Pattern.compile(command.getRegex()).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}

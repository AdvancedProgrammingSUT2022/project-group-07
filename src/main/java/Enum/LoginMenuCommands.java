package Enum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenuCommands {

     //TODO : complete commands;

    private String regex;

    LoginMenuCommands(String regex) {this.regex = regex;}

    public String getRegex() {
        return regex;
    }

    public static Matcher getMatcher(String input , LoginMenuCommands command) {
        Matcher matcher = Pattern.compile(command.getRegex()).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}

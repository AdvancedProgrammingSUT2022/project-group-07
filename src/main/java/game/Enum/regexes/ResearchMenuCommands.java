package game.Enum.regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ResearchMenuCommands {
    EXIT("exit");


    private String regex;

    ResearchMenuCommands(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
    public static Matcher getMatcher(String input , ResearchMenuCommands command) {
        Matcher matcher = Pattern.compile(command.getRegex()).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}

package View;

import java.util.Scanner;
import java.util.regex.Matcher;

public abstract class Menu {

    protected Scanner scanner;
    public abstract void run();

    public Menu(Scanner scanner) {this.scanner = scanner;}
}

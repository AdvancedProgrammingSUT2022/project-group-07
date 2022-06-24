package game.View;

import java.util.Scanner;

public class CityCaptureMenu {
    public int getCommand() {
        System.out.println("enter 1 for capturing city\nenter 2 for ruining it!");
        return new Scanner(System.in).nextInt();
    }
}

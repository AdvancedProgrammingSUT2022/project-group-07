package View;

import Controller.game.SelectController;
import Model.City;

import java.util.Scanner;

public class CityMenu extends Menu{
    private City city;

    public CityMenu(Scanner scanner) {
        super(scanner);
        this.city = SelectController.selectedCity;
    }

    @Override
    public void run() {

    }
}

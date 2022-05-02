package View;

import Controller.game.City.CreateUnit;
import Controller.game.CityController;
import Controller.game.GameController;
import Controller.game.SelectController;
import Controller.game.UnitController;
import Controller.game.citizen.CitizenController;
import Enum.regexes.CityMenuCommands;
import Model.City;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CityMenu extends Menu {
    private City city;
    private GameController gameController;
    public CityMenu(Scanner scanner , GameController gameController) {
        super(scanner);
        this.city = SelectController.selectedCity;
        this.gameController = gameController;
    }

    @Override
    public void run() {
        String input;
        Matcher matcher;

        while (true) {
            input = scanner.nextLine();
            if (CityMenuCommands.getMatcher(input, CityMenuCommands.EXIT) != null)
                break;
            else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.CREATE_UNIT)) != null) {
                String result = CreateUnit.checkRequiredTechsAndResourcesToCreateUnit(matcher, gameController);
                System.out.println(result);
            } else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.BUY_UNIT_WITH_GOLD)) != null) {
                String result = CreateUnit.buyUnitWithGold(gameController);
                System.out.println(result);
            } else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.EMPLOY_CITIZEN_TO_TILE)) != null) {
                String result = CitizenController.lock(city , matcher , gameController);
                System.out.println(result);
            } else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.UNLOCK_CITIZEN)) != null) {
                String result = CitizenController.unlock(city , matcher);
                System.out.println(result);
            } else System.out.println("invalid command from ayoub");
        }
    }
}
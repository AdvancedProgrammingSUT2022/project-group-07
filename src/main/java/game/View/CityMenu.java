package game.View;

import game.Controller.game.City.CreateUnit;
import game.Controller.game.CityController;
import game.Controller.game.GameController;
import game.Controller.game.MapController;
import game.Controller.game.SelectController;
import game.Controller.game.citizen.CitizenController;
import game.Enum.regexes.CityMenuCommands;
import game.Enum.regexes.GameMenuCommands;
import game.Model.City;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CityMenu extends Menu {
    private City city;
    private GameController gameController;

    public CityMenu(Scanner scanner, GameController gameController) {
        super(scanner);
        this.city = SelectController.selectedCity;
        this.gameController = gameController;
    }

    @Override
    public void run() {
        String input;
        Matcher matcher;

        while (true) {
            MapController.printMap(GameController.getInstance().getMap() , gameController.getCurrentCivilization() , gameController.getCivilizations());
            input = scanner.nextLine();
            if (CityMenuCommands.getMatcher(input, CityMenuCommands.EXIT) != null)
                break;
            else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.CREATE_UNIT)) != null) {
//                String result = CreateUnit.checkRequiredTechsAndResourcesToCreateUnit(matcher, gameController);
//                System.out.println(result);
            } else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.BUY_UNIT_WITH_GOLD)) != null) {
                String result = CreateUnit.buyUnitWithGold(gameController);
                System.out.println(result);
            } else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.CHANGE_UNIT_CONSTRUCTION)) != null) {
//                String result = CreateUnit.changeUnitConstruction(matcher);
//                System.out.println(result);
            } else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.REMOVE_UNIT_CONSTRUCTION)) != null) {
                String result = CreateUnit.removeUnitConstruction(matcher);
                System.out.println(result);
            } else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.EMPLOY_CITIZEN_TO_TILE)) != null) {
//                String result = CitizenController.lock(city, matcher, gameController);
//                System.out.println(result);
            } else if ((matcher = CityMenuCommands.getMatcher(input, CityMenuCommands.UNLOCK_CITIZEN)) != null) {
//                String result = CitizenController.unlock(city, matcher);
//                System.out.println(result);
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.CITY_SHOW_TILES_OWNED)) != null) {
                String result = CityController.showTilesOwned();
                System.out.println(result);
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.CITY_SHOW_TILES_AVAILABLE)) != null) {
//                String result = CityController.showTilesAvailable(GameController.getInstance().getMap(),
//                        GameController.getInstance().getMapWidth(), GameController.getInstance().getMapHeight());
//                System.out.println(result);
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.CITY_BUY_TILE)) != null) {
//                String result = CityController.buyTile(matcher, GameController.getInstance().getMap(),
//                        GameController.getInstance().getMapWidth(), GameController.getInstance().getMapHeight(),
//                        gameController.getCurrentCivilization());
//                System.out.println(result);
            }
            else if ((CityMenuCommands.getMatcher(input , CityMenuCommands.SHOW_UNEMPLOYED_CITIZENS)) != null) {
                String result = CityController.showUnemployedCitizens(city);
                System.out.print(result);
            }
            else System.out.println("invalid command from ayoub");
        }
    }
}

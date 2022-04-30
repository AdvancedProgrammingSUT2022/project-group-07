package View;

import Controller.cheat.Cheat;
import Controller.game.GameController;
import Controller.game.SelectController;
import Controller.game.UnitController;
import Controller.game.units.Settler;
import Controller.menu.GameMenuController;
import Enum.MenuName;
import Enum.regexes.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {
    private final GameMenuController gameMenuController;
    private GameController gameController;

    public GameMenu(Scanner scanner, GameMenuController gameMenuController, GameController gameController) {
        super(scanner);
        this.gameMenuController = gameMenuController;
        this.gameController = gameController;
    }

    public void run() {
        String input;
        Matcher matcher;

        while (MenuName.getCurrentMenu() == MenuName.GAME_MENU) {
            gameController.printMap();
            input = scanner.nextLine();
            // menu commands ::::
            if (GameMenuCommands.getMatcher(input, GameMenuCommands.EXIT) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.MENU_NAVIGATION)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SHOW_CURRENT_MENU)) != null) {

            }
            // info commands :::

            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_RESEARCH)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_UNITS)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_CITIES)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_DIPLOMACY)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_VICTORY)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_DEMOGRAPHICS)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_NOTIFICATIONS)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_MILITARY)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_MILITARY)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_ECONOMIC)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_DIPLOMATIC)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INFO_DEALS)) != null) {

            }
            // selection commands :::::

            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_UNIT_COMBAT)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_UNIT_NONCOMBAT)) != null) {
                String result = SelectController.selectNonCombatUnit(matcher, gameController);
                System.out.println(result);
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.SELECT_CITY)) != null) {

            }

            // unit commands ::

            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_MOVE_TO)) != null) {
                String result = UnitController.moveUnit(matcher, gameController);
                System.out.println(result);
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_SLEEP)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_ALERT)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_FORTIFY)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_GARRISON)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_SETUP_RANGED)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_ATTACK)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_FOUND_CITY)) != null) {
                String result = Settler.foundCity(matcher, gameController);
                System.out.println(result);
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_CANCEL_MISSION)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_WAKE)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_DELETE)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_BUILD_IMPROVEMENT)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_REMOVE_JUNGLE)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_REMOVE_ROUTE)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.UNIT_REPAIR)) != null) {

            }

            // map commands :::


            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.MAP_SHOW)) != null) {

            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.MAP_MOVE)) != null) {

            }
            // cheats :::

            else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INCREASE_TURN)) != null) {
                String result = Cheat.turnCheat(matcher, gameController);
                System.out.println(result);
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.INCREASE_GOLD)) != null) {
                String result = Cheat.goldCheat(matcher, gameController);
                System.out.println(result);
            } else if ((matcher = GameMenuCommands.getMatcher(input, GameMenuCommands.NEXT_TURN)) != null) {
                String result = gameMenuController.nextTurn(gameController);
                System.out.println(result);
            } else System.out.println("invalid command ayoub");
        }

    }
}

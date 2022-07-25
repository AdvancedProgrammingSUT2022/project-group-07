package game.Client.View;

import game.Server.Controller.game.GameController;
import game.Server.Controller.game.ResearchController;
import game.Common.Enum.regexes.GameMenuCommands;
import game.Common.Enum.regexes.ResearchMenuCommands;
import game.Common.Model.Civilization;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ResearchMenu extends Menu{
    private Civilization civilization ;
    private GameController gameController;
    public ResearchMenu(Scanner scanner , GameController gameController) {
        super(scanner);
        this.civilization = gameController.getCurrentCivilization();
        this.gameController = gameController;
    }

    @Override
    public void run() {
        String input;
        Matcher matcher;

        while (true) {
            input = scanner.nextLine();
            if (ResearchMenuCommands.getMatcher(input, ResearchMenuCommands.EXIT) != null)
                break;
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_SHOW_CURRENT)) != null){
                String result = ResearchController.showCurrentResearch(gameController.getCurrentCivilization());
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_SHOW_AVAILABLE)) != null) {
                String result = ResearchController.showAvailableResearch(gameController.getCurrentCivilization());
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_SHOW_OWNED)) != null) {
                String result = ResearchController.showOwnedResearch(gameController.getCurrentCivilization());
                System.out.println(result);
            }
            else if ((matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_TECHNOLOGY)) != null) {
                String result = ResearchController.researchTechnology(matcher , gameController.getCurrentCivilization());
                System.out.println(result);
            }
            else System.out.println("invalid command from ayoub");
        }
    }
}

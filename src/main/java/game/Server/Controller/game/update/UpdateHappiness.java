package game.Server.Controller.game.update;

import game.Server.Controller.game.GameController;
import game.Common.Model.City;
import game.Common.Model.Civilization;
import game.Common.Enum.Building;
import game.Common.Enum.Resources;
import game.Common.Model.Terrain;
import game.Common.Enum.TypeOfResource;

import java.util.ArrayList;

public class UpdateHappiness {

    public static void update(Civilization civilization, GameController gameController) {
        rawCity(civilization);
        numberOfCities(civilization);
        luxuryResources(civilization);
    }

    private static void rawCity(Civilization civilization) {
        int citizens = 0;
        int number;
        for (City city : civilization.getCities()) {
            citizens += city.getCitizens().size();
            if (!city.getBuildings().contains(Building.MONUMENT)) {
                if (citizens % 10 == 0) {
                    number = citizens / 10;
                    civilization.setHappiness(civilization.getHappiness() - number);
                }
            }
        }
    }

    private static void numberOfCities(Civilization civilization) {
        int numberOfCities = civilization.getCities().size();
        int number;
        if (numberOfCities % 2 == 0) {
            number = numberOfCities / 2;
            civilization.setHappiness(civilization.getHappiness() - number);
        }
    }

    private static void luxuryResources(Civilization civilization) {
        int number;
        ArrayList<Resources> luxuryResources = new ArrayList<>();
        for (City city : civilization.getCities()) {
            for (Terrain terrain : city.getTerrains()) {
                if (terrain.getResources() == null) continue;
                if (terrain.getResources().getTypeOfResource() == TypeOfResource.LUXURY) {
                    if (!luxuryResources.contains(terrain.getResources())) {
                        luxuryResources.add(terrain.getResources());
                    }
                }
            }
        }
        if (luxuryResources.size() % 3 == 0) {
            number = luxuryResources.size() / 3;
            civilization.setHappiness(civilization.getHappiness() + number);
        }
    }

    public static void annexedCity(int turn) {
    }
}

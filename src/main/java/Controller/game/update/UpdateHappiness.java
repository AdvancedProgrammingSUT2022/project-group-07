package Controller.game.update;

import Controller.game.GameController;
import Model.City;
import Model.Civilization;
import Enum.Building;
import Enum.Resources;
import Model.Terrain;
import Enum.TypeOfResource;

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
        luxuryResources.add(civilization.getCities().get(0).getTerrains().get(0).getResources());
        for (City city : civilization.getCities()) {
            for (Terrain terrain : city.getTerrains()) {
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
}

package game.Controller.game.citizen;

import game.Controller.game.*;
import game.Model.*;
import game.View.components.Tile;
import game.View.controller.CityPanelController;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static game.View.controller.CityPanelController.showConfirm;
import static game.View.controller.CityPanelController.showError;

public class CitizenController {

    public static void unlock(City city, String message) {
        int citizenNumber = Integer.parseInt(message);
        if (city.getCitizens().size() <= citizenNumber)
            showError("this citizen does not exist in the city selected!");
        if (isCitizenUnemployed(city.getCitizens().get(citizenNumber - 1)))
            showError("this citizen is unemployed already!");
        city.getCitizens().get(citizenNumber - 1).removeWork();
        showConfirm("citizen " + citizenNumber + " was unlocked from his tile successfully!");
    }

    public static void lock(City city, String[] message) {
        int citizenNumber = Integer.parseInt(message[0]);
        int tileNumber = Integer.parseInt(message[1]);
        ArrayList<Tile> tiles = Tile.getTiles();
        Terrain terrain = tiles.get(tileNumber - 1).getTerrain();

        if (city.getCitizens().size() <= citizenNumber)
            showError("this citizen does not exist in the city selected!");
        if (!isCitizenUnemployed(city.getCitizens().get(citizenNumber - 1)))
            showError("this is not an unemployed citizen!");
        if (terrain == null)
            showError("location selected is out of bound!");
        if (!CityController.isTileInCity(city , terrain))
            showError("this area does not belong to the selected city!");
        if (isSomeBodyAlreadyWorkingThere(terrain , GameController.getInstance()))
            showError("this terrain is already occupied by someone else!");

        city.getCitizens().get(citizenNumber - 1).assignWork(terrain);
        showConfirm("citizen " + citizenNumber + " assigned to work successfully!");
    }

    private static boolean isSomeBodyAlreadyWorkingThere(Terrain terrain , GameController gameController) {
        for (Civilization civilization : gameController.getCivilizations()) {
            for (City city : civilization.getCities()) {
                for (Citizen citizen : city.getCitizens()) {
                    if (citizen.getTerrain() == null) continue;
                    if (citizen.getTerrain().equals(terrain))
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean isCitizenUnemployed(Citizen citizen) {
        return citizen.getTerrain() == null;
    }
}

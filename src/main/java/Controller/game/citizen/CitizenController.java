package Controller.game.citizen;

import Controller.game.CityController;
import Controller.game.GameController;
import Controller.game.TerrainController;
import Model.*;

import java.util.regex.Matcher;

public class CitizenController {

    public static String unlock(City city, Matcher matcher) {
        int citizenNumber = Integer.parseInt(matcher.group("number"));
        if (city.getCitizens().size() < citizenNumber) return "this citizen does not exist in the city selected!";
        if (isCitizenUnemployed(city.getCitizens().get(citizenNumber - 1))) return "this citizen is unemployed already!";
        city.getCitizens().get(citizenNumber - 1).removeWork();
        return "citizen was unlocked from his tile successfully!";
    }

    public static String lock(City city, Matcher matcher , GameController gameController) {
        int citizenNumber = Integer.parseInt(matcher.group("number"));
        Location location = new Location(Integer.parseInt(matcher.group("X")) , Integer.parseInt(matcher.group("Y")));
        if (city.getCitizens().size() < citizenNumber) return "this citizen does not exist in the city selected!";
        if (!isCitizenUnemployed(city.getCitizens().get(citizenNumber - 1))) return "this is not an unemployed citizen!";
        Terrain terrain = TerrainController.getTerrainByLocation(location);
        if (terrain == null) return "location selected is out of bound!";
        if (!CityController.isTileInCity(city , terrain)) return "this area does not belong to the selected city!";
        if (isSomeBodyAlreadyWorkingThere(terrain , gameController)) return "this terrain is already occupied by someone else!";
        city.getCitizens().get(citizenNumber - 1).assignWork(terrain);
        return "citizen assigned to work successfully!";
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

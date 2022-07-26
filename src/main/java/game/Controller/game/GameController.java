package game.Controller.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Controller.game.movement.TheShortestPath;
import game.Enum.*;
import game.Model.*;
import game.View.components.Tile;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class GameController {
    public int mapWidth;
    public int mapHeight;
    public MapDimension mapDimension;
    public Tile[][] map;
    public Terrain[][] terrains = new Terrain[mapHeight][mapWidth] ;
    public ArrayList<User> players = new ArrayList<>();
    public ArrayList<Civilization> civilizations;
    public int time;
    public int turn;
    public Civilization currentCivilization;

    private static GameController instance;

    public static GameController getInstance() {
        if (instance == null) instance = new GameController();
        return instance;
    }

    private GameController() {
        this.turn = 0;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * a function to generate settler units locations while initializing civilizations
     *
     * @param locations all used locations
     * @return random available location
     */
    private Location generateSettlerUnitLocation(ArrayList<Location> locations) {
        Random rand = new Random();
        int x = rand.nextInt(mapWidth), y = rand.nextInt(mapHeight);
        while (locations.contains(new Location(x, y))
                || map[y][x].getTerrain().getTypeOfTerrain() == TypeOfTerrain.OCEAN
                || map[y][x].getTerrain().getTypeOfTerrain() == TypeOfTerrain.MOUNTAIN) {
            x = rand.nextInt(mapWidth);
            y = rand.nextInt(mapHeight);
        }
        return new Location(x, y);
    }

    /**
     * a function to initialize civilizations at the very beginning of the game
     *
     * @param users list of logged-in users who wants to play
     */
    private void initializeCivilizations(ArrayList<User> users) {
        // TODO: create some real civilization names
        civilizations = new ArrayList<Civilization>();
        for (int i = 0; i < users.size(); i++)
            civilizations.add(new Civilization("c" + Integer.toString(i + 1), users.get(i)));
        Random rand = new Random();
        ArrayList<Location> locations = new ArrayList<Location>();
        Location newUnitLocation;
        for (Civilization civilization : civilizations) {
            newUnitLocation = generateSettlerUnitLocation(locations);
            locations.add(newUnitLocation);
            civilization.addUnit(new Unit(TypeOfUnit.SETTLER, UnitStatus.ACTIVE, newUnitLocation, TypeOfUnit.SETTLER.getHp(), civilization, 0));
            civilization.getKnownTerrains().add(TerrainController.getTerrainByLocation(newUnitLocation));
            civilization.addTechnology(new Technology(TypeOfTechnology.AGRICULTURE));
        }
    }

    public ArrayList<User> getPlayers() {
        return this.players;
    }

    public void setPlayers(ArrayList<User> players) {
        this.players = players;
    }

    public Tile[][] getMap() {
        return map;
    }

    public void initialize() {
        mapDimension = chooseRandomMap();
        mapWidth = mapDimension.getX();
        mapHeight = mapDimension.getY();
        map = new Tile[mapHeight][mapWidth];
        map = MapController.createMap(mapWidth, mapHeight);
        initializeCivilizations(players);
        setCurrentCivilization(civilizations.get(0));
        setStartingRelations () ;
        CivilizationController.updateFogOfWar(currentCivilization , map , mapWidth , mapHeight);
        TheShortestPath.run();
        MapController.setBackgrounds(map);
        createTerrainsFromMap();
    }

    private void setStartingRelations() {
        for (Civilization civilization1 : civilizations) {
            for (Civilization civilization2 : civilizations) {
                if (civilization1.equals(civilization2))
                    continue;
                civilization1.addRelationWithCivilization(TypeOfRelation.NEUTRAL , civilization2);
            }
        }
    }

    private static MapDimension chooseRandomMap() {
        int players = GameController.getInstance().getPlayers().size();
        if (players <= 3)
            return MapDimension.SMALL;
        if (players <= 5)
            return MapDimension.STANDARD;
        return MapDimension.LARGE;
    }

    public void run() {

    }

    public ArrayList<Civilization> getCivilizations() {
        return this.civilizations;
    }


    public void showCity(String cityName) {

    }


    public Civilization getCurrentCivilization() {
        return this.currentCivilization;
    }

    public void setCurrentCivilization(Civilization currentCivilization) {
        this.currentCivilization = currentCivilization;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void nextTurn(GameController gameController) {
        int index = gameController.getCivilizations().indexOf(gameController.getCurrentCivilization());
        if (index == gameController.getCivilizations().size() - 1) {
            gameController.setTurn(gameController.getTurn() + 1);
            gameController.setCurrentCivilization(gameController.getCivilizations().get(0));
        }
        else
            gameController.setCurrentCivilization(gameController.getCivilizations().get(index + 1));
        SelectController.selectedUnit = null;
        SelectController.selectedCity = null;
        CivilizationController.updateCivilizationElements(gameController);
    }

    // save stuff
    public void saveData (GameController gameController , String saveName){
        Thread thread = new Thread(() -> {
            try {
                GameControllerDecoy gameControllerDecoy = new GameControllerDecoy(gameController);
                FileWriter fileWriter = new FileWriter("./src/main/resources/game/database/games/"+saveName+".json");
                fileWriter.write(new Gson().toJson(gameControllerDecoy));
                fileWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }) ;
        thread.start();
    }

    public void loadGame (String name){
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/game/database/games/"+name+".json")));
            GameControllerDecoy gameControllerDecoy = new Gson().fromJson(json , new TypeToken<GameControllerDecoy>(){}.getType());
            deepCopy(gameControllerDecoy);
        }
        catch (IOException e){e.printStackTrace();}
    }

    public MapDimension getMapDimension() {
        return mapDimension;
    }

    public void deepCopy (GameControllerDecoy gameController){
        this.mapDimension = gameController.getMapDimension() ;
        this.mapWidth = gameController.getMapWidth() ;
        this.mapHeight = gameController.getMapHeight();
        this.players = gameController.getPlayers() ;
        this.civilizations = gameController.getCivilizations() ;
        this.time = gameController.getTime() ;
        this.turn = gameController.getTurn() ;
        this.currentCivilization = gameController.getCurrentCivilization();
        this.terrains = gameController.getTerrains() ;
        loadTilesFromTerrains();
    }

    public Civilization getCivilizationByName (String name){
        for (Civilization civilization : civilizations) {
            if (civilization.getName().equals(name))
                return civilization;
        }
        return null ;
    }

    private void createTerrainsFromMap (){
        terrains = new Terrain[mapHeight][mapWidth] ;
        for (int row=0 ; row<mapHeight ; row++){
            for (int col=0 ; col<mapWidth ; col++)
                terrains[row][col] = map[row][col].getTerrain() ;
        }
    }


    private void loadTilesFromTerrains (){
        this.map = new Tile[mapHeight][mapWidth];
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++)
                map[y][x] = TileController.createTile(terrains[y][x] , x , y);
        }
        CivilizationController.updateFogOfWar(currentCivilization , map , mapWidth , mapHeight);
        TheShortestPath.run();
        MapController.setBackgrounds(map);
    }

    public Terrain[][] getTerrains() {
        return terrains;
    }
}

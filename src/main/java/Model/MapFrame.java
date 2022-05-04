package Model;

import javax.swing.*;
import java.awt.* ;
import java.lang.Math ;
import java.util.ArrayList;
import java.util.HashMap;

import Enum.MapDimension;
import Enum.RiverSide;
import Enum.Resources ;
import Enum.TypeOfTechnology;
import Model.Improvement;
import Enum.TypeOfImprovement;

public class MapFrame extends JFrame {
    private final Terrain[][] map;
    private final int hexagonA;
    private final int rad3over2;
    private final Color[] colors = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.ORANGE};
    private final int width;
    private final int height;
    private final ArrayList<Civilization> civilizations;
    private final Civilization currentCivilization;
    private final Location center;


    public MapFrame(MapDimension mapDimension, Terrain[][] map, Location mapCenter, ArrayList<Civilization> civilizations, Civilization currentCivilization) {
        this.width = mapDimension.getX();
        this.height = mapDimension.getY();
        this.map = map;
        this.center = mapCenter;
        this.civilizations = civilizations;
        this.currentCivilization = currentCivilization;
        setSize(1080, 720);
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.hexagonA = 50;
        rad3over2 = (int) (((double) hexagonA) * Math.sqrt(3) / 2);
    }


        /**
         * a function to get all units of the game
         * @return all units of the game
         */
        private ArrayList<Unit> getUnits () {
            ArrayList<Unit> allUnits = new ArrayList<Unit>();
            for (Civilization civilization : civilizations)
                allUnits.addAll(civilization.getUnits());
            return allUnits;
        }

        /**
         * a function to generate mapFrame hexagon
         * @param x center x of hexagon
         * @param y center y of hexagon
         * @return polygon
         */
        private Polygon generateHexagon ( int x, int y){
            int[] xs = {x - rad3over2, x - rad3over2, x, x + rad3over2, x + rad3over2, x};
            int[] ys = {y + hexagonA / 2, y - hexagonA / 2, y - hexagonA, y - hexagonA / 2, y + hexagonA / 2, y + hexagonA};
            return new Polygon(xs, ys, 6);
        }

        /**
         * a function to draw a hexagon on g2d graphics2d
         * @param g2d graphics2d component
         * @param polygon hexagon shape
         */
        private void drawHexagon (Graphics2D g2d, Polygon polygon ,int row, int col){
            HashMap<Terrain, Civilization> civilizationsTerrains = new HashMap<>();
            for (Civilization civilization : civilizations) {
                for (City city : civilization.getCities()) {
                    for (Terrain terrain : city.getTerrains())
                        civilizationsTerrains.put(terrain, civilization);
                }
            }
            g2d.setColor(Color.WHITE);
            Civilization owner = civilizationsTerrains.get(map[row][col]);
            if (owner != null)
                g2d.setColor(colors[civilizations.indexOf(owner)]);
            g2d.fillPolygon(polygon);
            g2d.setColor(Color.BLACK);
            g2d.drawPolyline(polygon.xpoints, polygon.ypoints, 6);
        }

        /**
         * a function to draw needed information on each tile
         * @param g2d graphics2d object
         * @param centerLocation center of polygon
         * @param row row
         * @param col col
         */
        private void drawInformationOnHexagon (Graphics2D g2d, Location centerLocation ,int row, int col){
            int x = centerLocation.getX();
            int y = centerLocation.getY();
            String loc = "(" + col + "," + row + ")";
            String typeOfTerrain = map[row][col].getTypeOfTerrain().getName().substring(0, 3);
            String typeOfTerrainFeature = map[row][col].getTerrainFeatures() != null ? map[row][col].getTerrainFeatures().toString().substring(0, 3) : "";
            g2d.setColor(Color.BLACK);
            Font myFont = new Font("Sans Serif", Font.BOLD, 15);
            g2d.setFont(myFont);
            if (currentCivilization.getKnownTerrains().contains(map[row][col])) {
                g2d.drawString(typeOfTerrainFeature, x - hexagonA/3, y - hexagonA/2);
                g2d.drawString(typeOfTerrain, x - 2*hexagonA/3 , y);
            }
            g2d.drawString(loc, x - hexagonA/2, y + 2*hexagonA/3);
        }

        /**
         * a function to draw resources on this tile
         * @param g2d graphics2d object
         * @param centerLocation center of polygon
         * @param row row
         * @param col col
         */
        private void drawResourceOnHexagon (Graphics2D g2d , Location centerLocation , int row , int col){
            if (!this.currentCivilization.getKnownTerrains().contains(map[row][col]))
                return;
            int x = centerLocation.getX();
            int y = centerLocation.getY();
            Resources tileResource = map[row][col].getResources() ;
            if (tileResource==null)
                return;
            String resourceName = tileResource.getName().substring(0,3) ;
            TypeOfTechnology technologyNeeded = tileResource.getTechnologyNeeded();
            if (technologyNeeded==null
                    || this.currentCivilization.getGainedTypeOfTechnologies().contains(technologyNeeded)) {
                g2d.drawString(resourceName, x + hexagonA / 3, y);
            }
        }

        private void drawImprovementOnHexagon (Graphics2D g2d , Location centerLocation , int row , int col){
            int x = centerLocation.getX();
            int y = centerLocation.getY();
            if (this.currentCivilization.getKnownTerrains().contains(map[row][col])
                    && this.currentCivilization.getVisibleTerrains().contains(map[row][col]))
                return;
            Improvement tileImprovement = map[row][col].getImprovement();
            if (tileImprovement==null)
                return;
//            String improvementName = tileImprovement.getImprovement().getName().substring(0,3);
//            g2d.drawString(improvementName , x-hexagonA/4 , y+hexagonA/4);
        }

        /**
         * a function to draw units on map
         * @param g2d graphics2d object
         * @param centerLocation center location of hexagon
         * @param row row
         * @param col col
         * @param units all units of the game
         */
        private void drawUnits ( final Graphics2D g2d, final Location centerLocation, final int row, final int col,
        final ArrayList<Unit> units ){
            int x = centerLocation.getX();
            int y = centerLocation.getY();
            for (final Unit unit : units) {
                if (unit.getLocation() != null
                        && unit.getLocation().getY() == row
                        && unit.getLocation().getX() == col) {
                    String unitName = unit.getTypeOfUnit().getName().substring(0,1).toUpperCase();
                    g2d.setColor(colors[civilizations.indexOf(unit.getCivilization())]);
                    g2d.fillOval(x - hexagonA / 2, y, hexagonA / 2, hexagonA / 2);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(x - hexagonA / 2, y, hexagonA / 2, hexagonA / 2);
                    g2d.drawString(unitName , x-hexagonA/3 , y+hexagonA/3);
                    x += hexagonA / 2;
                }
            }
        }

        /**
     * a function to draw rivers on a tile
     * @param g2d graphics 2d object
     * @param terrain tile
     * @param polygon used for coordinates
     */
        private void drawRivers ( final Graphics2D g2d , final Terrain terrain , Polygon polygon){
            ArrayList<RiverSide> riverSides = terrain.getRiverSides();
            int[] x = polygon.xpoints;
            int[] y = polygon.ypoints;
            g2d.setColor(Color.blue);
            g2d.setStroke(new BasicStroke(BasicStroke.CAP_SQUARE));
            for (RiverSide riverSide : riverSides) {
                switch (riverSide){
                    case UPPER_RIGHT -> {
                        g2d.drawLine(x[2],y[2] , x[3],y[3]);
                    }
                    case UPPER_LEFT -> {
                        g2d.drawLine(x[1],y[1] , x[2],y[2]);
                    }
                    case RIGHT -> {
                        g2d.drawLine(x[3],y[3] , x[4],y[4]);
                    }
                    case LEFT -> {
                        g2d.drawLine(x[0],y[0] , x[1],y[1]);
                    }
                    case LOWER_RIGHT -> {
                        g2d.drawLine(x[4],y[4] , x[5],y[5]);
                    }
                    case LOWER_LEFT -> {
                        g2d.drawLine(x[0],y[0] , x[5],y[5]);
                    }
                }
            }
        }

        private void paintRawMap( Graphics2D g2d , int firstRow , int lastRow , int firstCol , int lastCol){
            int y = 100;
            ArrayList<Unit> units = getUnits();
            for (int row = firstRow; row <= lastRow; row++) {
                int x = 100;
                if (row % 2 == 1)
                    x += rad3over2;
                for (int col = firstCol; col <= lastCol; col++) {
                    Polygon p = generateHexagon(x, y);
                    drawHexagon(g2d, p, row, col);
                    drawInformationOnHexagon(g2d, new Location(x, y), row, col);
                    drawResourceOnHexagon(g2d , new Location(x,y) , row , col);
                    drawImprovementOnHexagon(g2d , new Location(x,y) , row , col);
                    drawUnits(g2d, new Location(x, y), row, col, units);
                    x += rad3over2 * 2;
                }
                y += 1.5 * hexagonA;
            }
        }

        private void paintRivers (Graphics2D g2d , int firstRow , int lastRow , int firstCol , int lastCol){
            int y = 100 ;
            for (int row = firstRow; row <= lastRow; row++) {
                int x = 100;
                if (row % 2 == 1)
                    x += rad3over2;
                for (int col = firstCol; col <= lastCol; col++) {
                    Polygon p = generateHexagon(x, y);
                    if (currentCivilization.getKnownTerrains().contains(map[row][col]))
                        drawRivers(g2d , map[row][col] , p);
                    x += rad3over2 * 2;
                }
                y += 1.5 * hexagonA;
            }
        }

        public void paint (Graphics g){
            Graphics2D g2d = (Graphics2D) g;
            int firstRow = center.getY() >= 3 ? center.getY() - 3 : 0;
            int lastRow = center.getY() + 3 < height ? center.getY() + 3 : height - 1;
            int firstCol = center.getX() >= 3 ? center.getX() - 3 : 0;
            int lastCol = center.getX() + 3 < width ? center.getX() + 3 : width - 1;
            paintRawMap(g2d , firstRow , lastRow , firstCol , lastCol);
            paintRivers(g2d , firstRow , lastRow , firstCol , lastCol);
        }

}


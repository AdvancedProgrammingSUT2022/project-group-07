package Model;

import javax.swing.*;
import java.awt.* ;
import java.lang.Math ;
import java.util.ArrayList;
import java.util.HashMap;

import Controller.game.GameController;
import java.util.HashMap;
import Enum.TypeOfTerrain ;

public class MapFrame extends JFrame {
    private Terrain[][] map ;
    private int startingX ;
    private int startingY ;
    private int a ;
    private Color[] colors ;
    private int width ;
    private int height ;
    private ArrayList<Civilization> civilizations ;

    public MapFrame(int startingX , int startingY , int a , int mapWidth , int mapHeight , Terrain[][] map , ArrayList<Civilization> civilizations){
        this.map = map ;
        this.civilizations = civilizations ;
        this.setSize(1080,720) ;
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE) ;
        this.startingX = startingX ;
        this.startingY = startingY ;
        this.a = a ;
        this.width = mapWidth ;
        this.height = mapHeight ;
        colors = new Color[]{Color.RED , Color.BLUE , Color.GREEN , Color.YELLOW , Color.cyan , Color.orange} ;
    }

    public void paint(Graphics g){
        // getting all units of the game
        ArrayList<Unit> allUnits = new ArrayList<>();
        for (Civilization civilization : civilizations)
            allUnits.addAll(civilization.getUnits());
        Graphics2D g2d = (Graphics2D) g ;
        int rad3over2 = (int) (((double)a)*Math.sqrt(3)/2 );
        int y = startingY ;
        for (int row=0 ; row<height ; row++){
            int x = startingX ;
            if (row%2==1)
                x += rad3over2 ;
            for (int col=0 ; col<width ; col++){
                int[] xs = {x-rad3over2 , x-rad3over2 , x , x+rad3over2 , x+rad3over2 , x} ;
                int[] ys = {y+a/2 , y-a/2 , y-a , y-a/2 , y+a/2 , y+a} ;
                Polygon p = new Polygon(xs,ys,6);
                g2d.setColor(Color.WHITE);
                g2d.fillPolygon(p);
                g2d.setColor(Color.BLACK);
                g2d.drawPolyline(xs , ys , 6);
                String loc = "(" + Integer.toString(col) + "," + Integer.toString(row) + ")" ;
                String feature = map[row][col].getTerrainFeatures()!=null ? map[row][col].getTerrainFeatures().toString().substring(0,3) : "" ;
                String terrainType = map[row][col].getTypeOfTerrain().getName().substring(0,3) ;
                g2d.setColor(Color.BLACK);
                Font myFont = new Font ("Sans Serif", Font.BOLD, 10);
                g2d.setFont(myFont);
                g2d.drawString( terrainType, x-a/4 , y-a/2);
                g2d.drawString(feature , x-a/2 , y);
                g2d.drawString(loc, x-a/2, y+a/2);
                for (Unit oneUnit : allUnits) {
                    if (oneUnit.getLocation().getY()==row && oneUnit.getLocation().getX()==col) {
                        g2d.setColor(colors[civilizations.indexOf(oneUnit.getCivilization())]);
                        g2d.fillOval(x, y, a / 2, a / 2);
                    }
                }
                x += rad3over2*2 ;
            }
            y += 1.5 * a ;
        }
    }

}


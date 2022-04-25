package Model;

import javax.swing.*;
import java.awt.* ;
import java.lang.Math ;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.HashMap;

import Controller.game.GameController;
=======
import java.util.HashMap;

>>>>>>> main
import Enum.TypeOfTerrain ;

public class MapFrame extends JFrame {
    private Terrain[][] map ;
    private int startingX ;
    private int startingY ;
    private int a ;
    private HashMap<TypeOfTerrain,Color> colors ;
    private int width ;
    private int height ;

    public MapFrame(int startingX , int startingY , int a , int mapWidth , int mapHeight , Terrain[][] map){
        this.map = map ;
        this.setSize(1080,720) ;
        this.setVisible(true);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE) ;
        this.startingX = startingX ;
        this.startingY = startingY ;
        this.a = a ;
        this.width = mapWidth ;
        this.height = mapHeight ;
        colors = new HashMap<TypeOfTerrain, Color>() ;
        colors.put(TypeOfTerrain.PLAIN , Color.GREEN) ;
        colors.put(TypeOfTerrain.DESERT , Color.ORANGE) ;
        colors.put(TypeOfTerrain.MOUNTAIN , new Color(0x4B521C)) ;
        colors.put(TypeOfTerrain.OCEAN , Color.BLUE) ;
        colors.put(TypeOfTerrain.SNOW , Color.WHITE) ;
        colors.put(TypeOfTerrain.TUNDRA , Color.CYAN) ;
        colors.put(TypeOfTerrain.GRASSLAND , new Color(0x32A844)) ;
        colors.put(TypeOfTerrain.HILL , new Color(0xF0E68C)) ;
    }

    public void paint(Graphics g){
<<<<<<< HEAD
        // getting all units of the game
        ArrayList<Unit> allUnits = new ArrayList<>();
        for (Civilization civilization : GameController.getCivilizations())
            allUnits.addAll(civilization.getUnits());

=======
>>>>>>> main
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
                g2d.setColor(colors.get(map[row][col].getTypeOfTerrain()));
                g2d.fillPolygon(p);
                g2d.setColor(Color.BLACK);
                g2d.drawPolyline(xs , ys , 6);
                String loc = "(" + Integer.toString(col) + "," + Integer.toString(row) + ")" ;
                String feature = map[row][col].getTerrainFeatures()!=null ? map[row][col].getTerrainFeatures().toString().substring(0,3) : "" ;
                g2d.setColor(Color.BLACK);
                Font myFont = new Font ("Sans Serif", Font.BOLD, 10);
                g2d.setFont(myFont);
                g2d.drawString(feature , x-a/2 , y-a/3);
                g2d.drawString(loc, x-a/2, y+a/3);
                for (Unit oneUnit : allUnits) {
                    if (oneUnit.getLocation().getY()==row && oneUnit.getLocation().getX()==col)
                        g2d.fillOval(x , y , a/4 , a/4);
                }
                x += rad3over2*2 ;
            }
            y += 1.5 * a ;
        }
    }

}


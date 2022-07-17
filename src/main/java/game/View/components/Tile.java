package game.View.components;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;

public class Tile extends Polygon {

        private static final double r = 75;
        private static final double n = Math.sqrt(r * r * 0.75);
        private final static double TILE_HEIGHT = 2 * r;
        private final static double TILE_WIDTH = 2 * n;

        public static double getTileHeight() {return TILE_HEIGHT;}
        public static double getTileWidth() {
            return TILE_WIDTH;
        }
        public static double getR(){
            return r;
        }
        public static double getN(){
            return n;
        }
        public Tile(double x, double y) {
            // creates the polygon using the corner coordinates
            getPoints().addAll(
                    x,y,
                    x + 0.5*r , y + n,
                    x + 1.5*r , y + n,
                    x + TILE_HEIGHT , y ,
                    x + 1.5 * r , y - n ,
                    x + 0.5 * r , y - n
//                    x, y,
//                    x, y + r,
//                    x + n, y + r * 1.5,
//                    x + TILE_WIDTH, y + r,
//                    x + TILE_WIDTH, y,
//                    x + n, y - r * 0.5
            );
            setStrokeWidth(1);
            setStroke(Color.TRANSPARENT);
        }

    public void setBackground(String address) {
            setFill(new ImagePattern((new Image(getClass().getResource("/game/assets/civAsset/map/Tiles/terrainsAndFeatures/"
                    + address).toExternalForm()))));
    }

}

package Enum;

public enum MapDimension {
    SMALL(20 , 16) ,
    STANDARD( 24, 18) ,
    LARGE(28 , 22) ;
    MapDimension(int x , int y){
        this.x = x ;
        this.y = y ;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private final int x ;
    private final int y ;
}

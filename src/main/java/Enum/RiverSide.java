package Enum;

public enum RiverSide {
    UPPER_RIGHT(0,-1),
    UPPER_LEFT (0,-1),
    RIGHT (1,0),
    LEFT (-1,0),
    LOWER_RIGHT (0,1),
    LOWER_LEFT (0,1);

    RiverSide (int xEffect , int yEffect) {
        this.xEffect = xEffect;
        this.yEffect = yEffect;
    }

    public int getxEffect() {
        return xEffect;
    }

    public int getyEffect() {
        return yEffect;
    }

    final int xEffect ;
    final int yEffect ;

}

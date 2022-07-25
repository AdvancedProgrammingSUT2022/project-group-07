package game.Common.Model;

import game.Common.Enum.TypeOfTrade;
import game.Common.Enum.Resources;

public class TradingAsset {

    TypeOfTrade typeOfTrade ;
    int gold ;
    Resources resource ;

    public TradingAsset (int gold){
        typeOfTrade = TypeOfTrade.GOLD ;
        this.gold = gold ;
    }

    public TradingAsset (Resources resource){
        typeOfTrade = TypeOfTrade.RESOURCE ;
        this.resource = resource ;
    }

    public TypeOfTrade getTypeOfTrade() {
        return typeOfTrade;
    }

    public int getGold() {
        return gold;
    }

    public Resources getResource() {
        return resource;
    }
}

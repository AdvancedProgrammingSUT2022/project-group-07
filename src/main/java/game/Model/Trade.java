package game.Model;

import game.Enum.Resources;
import game.Enum.TypeOfTrade;

public class Trade {

    Civilization sender ;
    Civilization receiver ;

    TradingAsset senderTradingAsset ;
    TradingAsset receiverTradingAsset ;

    public Trade(Civilization sender, TradingAsset senderTradingAsset, Civilization receiver, TradingAsset receiverTradingAsset) {
        this.sender = sender;
        this.receiver = receiver;
        this.senderTradingAsset = senderTradingAsset;
        this.receiverTradingAsset = receiverTradingAsset;
    }

    public void handle () {
        if (receiverTradingAsset == null) {
            if (senderTradingAsset.getTypeOfTrade().equals(TypeOfTrade.GOLD)) {
                sender.setGold(sender.getGold() + senderTradingAsset.getGold());
                receiver.setGold(receiver.getGold() - senderTradingAsset.getGold());
            } else if (senderTradingAsset.getTypeOfTrade().equals(TypeOfTrade.RESOURCE)) {
                moveResourceFromAtoB(receiver , sender , senderTradingAsset.getResource());
            }
        } else {
            if (senderTradingAsset.getTypeOfTrade().equals(TypeOfTrade.GOLD)) {
                if (receiverTradingAsset.getTypeOfTrade().equals(TypeOfTrade.GOLD)) {
                    sender.setGold(sender.getGold() + senderTradingAsset.getGold());
                    sender.setGold(sender.getGold() - receiverTradingAsset.getGold());
                    receiver.setGold(receiver.getGold() + receiverTradingAsset.getGold());
                    receiver.setGold(receiver.getGold() - senderTradingAsset.getGold());
                }
                else if (receiverTradingAsset.getTypeOfTrade().equals(TypeOfTrade.RESOURCE)) {
                    sender.setGold(sender.getGold() + senderTradingAsset.getGold());
                    moveResourceFromAtoB(sender, receiver, receiverTradingAsset.getResource());
                }
            }
            else if (senderTradingAsset.getTypeOfTrade().equals(TypeOfTrade.RESOURCE)) {
                if (receiverTradingAsset.getTypeOfTrade().equals(TypeOfTrade.GOLD)) {
                    moveResourceFromAtoB(receiver , sender , senderTradingAsset.getResource());
                    receiver.setGold(receiver.getGold() + receiverTradingAsset.getGold());
                }
                else if (receiverTradingAsset.getTypeOfTrade().equals(TypeOfTrade.RESOURCE)) {
                    moveResourceFromAtoB(sender, receiver, receiverTradingAsset.getResource());
                    moveResourceFromAtoB(receiver, sender, senderTradingAsset.getResource());
                }
            }
        }
    }

    public void moveResourceFromAtoB (Civilization a , Civilization b , Resources resource){
        a.removeResource(resource);
        b.addResource(resource);
    }

    public TradingAsset getSenderTradingAsset() {
        return senderTradingAsset;
    }

    public TradingAsset getReceiverTradingAsset() {
        return receiverTradingAsset;
    }
}


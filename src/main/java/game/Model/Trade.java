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
                sender.addResource(senderTradingAsset.getResource());
                receiver.removeResource(senderTradingAsset.getResource());
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
        System.out.println(resource + " moved from " + a.getName() + " to " + b.getName());
    }

    //    TypeOfTrade senderTypeOfTrade ;
//    TypeOfTrade receiverTypeOfTrade ;
//
//    private int senderDemandedGold ;
//    private int receiverDemandedGold ;
//
//    private Resources senderDemandedResource ;
//    private Resources receiverDemandedResource ;
//
//    private boolean isDemand ;
//
//    public Trade (Civilization sender , Civilization receiver){
//        this.sender = sender ;
//        this.receiver = receiver ;
//    }
//
//    public void setSenderDemandedGold(int senderDemandedGold) {
//        this.senderDemandedGold = senderDemandedGold;
//        this.senderTypeOfTrade = TypeOfTrade.GOLD ;
//    }
//    public void setSenderDemandedResource(Resources senderDemandedResource) {
//        this.senderDemandedResource = senderDemandedResource;
//        this.senderTypeOfTrade = TypeOfTrade.RESOURCE ;
//    }
//
//    public void setReceiverDemandedGold(int receiverDemandedGold) {
//        this.receiverDemandedGold = receiverDemandedGold;
//        this.receiverTypeOfTrade = TypeOfTrade.GOLD ;
//    }
//
//    public void setReceiverDemandedResource(Resources receiverDemandedResource) {
//        this.receiverDemandedResource = receiverDemandedResource;
//        this.receiverTypeOfTrade = TypeOfTrade.RESOURCE ;
//    }
//
//    public void handle (){
//
//        if (isDemand){
//            switch (senderTypeOfTrade){
//                case GOLD -> {
//                    sender.setGold(sender.getGold() + senderDemandedGold);
//                    receiver.setGold(receiver.getGold() - senderDemandedGold);
//                }
//                case RESOURCE -> {
//                    sender.addResource(senderDemandedResource);
//                    receiver.removeResource(senderDemandedResource);
//                }
//            }
//            return;
//        }
//
//        if (senderTypeOfTrade.equals(TypeOfTrade.GOLD) && receiverTypeOfTrade.equals(TypeOfTrade.GOLD)){
//            sender.setGold(sender.getGold() + senderDemandedGold);
//            receiver.setGold(receiver.getGold() + receiverDemandedGold);
//        }
//        else if (senderTypeOfTrade.equals(TypeOfTrade.GOLD) && receiverTypeOfTrade.equals(TypeOfTrade.RESOURCE)){
//            sender.setGold(sender.getGold() + senderDemandedGold);
//            sender.removeResource(receiverDemandedResource);
//            receiver.addResource(receiverDemandedResource);
//        }
//        else if (senderTypeOfTrade.equals(TypeOfTrade.RESOURCE) && receiverTypeOfTrade.equals(TypeOfTrade.GOLD)){
//            sender.addResource(senderDemandedResource);
//            receiver.setGold(receiver.getGold() + receiverDemandedGold);
//            receiver.removeResource(senderDemandedResource);
//        }
//        else if (senderTypeOfTrade.equals(TypeOfTrade.RESOURCE) && receiverTypeOfTrade.equals(TypeOfTrade.RESOURCE)){
//            sender.addResource(senderDemandedResource);
//            receiver.addResource(receiverDemandedResource);
//            sender.removeResource(receiverDemandedResource);
//            receiver.removeResource(senderDemandedResource);
//        }
//    }

}


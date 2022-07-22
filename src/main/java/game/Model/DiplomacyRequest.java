package game.Model;

import game.Enum.TypeOfDiplomacy;
import game.Enum.TypeOfRelation;

public class DiplomacyRequest {

    private Civilization sender ;
    private Civilization receiver ;
    private TypeOfDiplomacy typeOfDiplomacy ;
    private boolean isAcceptedByReceiver ;
    private boolean isRejectedByReceiver ;
    private Trade trade ;
    private boolean isHandled = false ;

    public DiplomacyRequest (Civilization sender , Civilization receiver , TypeOfDiplomacy typeOfDiplomacy){
        this.sender = sender ;
        this.receiver = receiver ;
        this.typeOfDiplomacy = typeOfDiplomacy ;
        isAcceptedByReceiver = false ;
        isRejectedByReceiver = false ;
    }

    public Civilization getSender() {
        return sender;
    }

    public void setSender(Civilization sender) {
        this.sender = sender;
    }

    public Civilization getReceiver() {
        return receiver;
    }

    public void setReceiver(Civilization receiver) {
        this.receiver = receiver;
    }

    public TypeOfDiplomacy getTypeOfDiplomacy() {
        return typeOfDiplomacy;
    }

    public void setTypeOfDiplomacy(TypeOfDiplomacy typeOfDiplomacy) {
        this.typeOfDiplomacy = typeOfDiplomacy;
    }

    public boolean isAcceptedByReceiver() {
        return isAcceptedByReceiver;
    }

    public void setAcceptedByReceiver(boolean acceptedByReceiver) {
        isAcceptedByReceiver = acceptedByReceiver;
    }

    public void setAcceptedByReceiver() {
        isAcceptedByReceiver = true;
    }

    public boolean isRejectedByReceiver() {
        return isRejectedByReceiver;
    }

    public void setRejectedByReceiver(boolean rejectedByReceiver) {
        isRejectedByReceiver = rejectedByReceiver;
    }

    public void setRejectedByReceiver() {
        isRejectedByReceiver = true;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public Trade getTrade() {
        return trade;
    }

    public void handle (){
        switch (typeOfDiplomacy){
            case BREAK_PEACE -> {
                sender.addRelationWithCivilization(TypeOfRelation.NEUTRAL , receiver);
                receiver.addRelationWithCivilization(TypeOfRelation.NEUTRAL , sender);
                isHandled = true ;
            }
            case PEACE -> {
                sender.addRelationWithCivilization(TypeOfRelation.ALLY , receiver);
                receiver.addRelationWithCivilization(TypeOfRelation.ALLY , sender);
                isHandled = true ;
            }
            case DECLARE_WAR -> {
                sender.addRelationWithCivilization(TypeOfRelation.ENEMY , receiver);
                receiver.addRelationWithCivilization(TypeOfRelation.ENEMY , sender);
                isHandled = true ;
            }
            case TRADE, DEMAND -> {
                trade.handle() ;
                isHandled = true ;
            }
        }
    }

    public boolean isStillPending (){
        return !isAcceptedByReceiver && !isRejectedByReceiver;
    }

    public boolean isHandled() {
        return isHandled;
    }
}

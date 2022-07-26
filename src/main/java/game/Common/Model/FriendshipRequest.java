package game.Common.Model;

import game.Server.Controller.UserController;

import java.util.Objects;

public class FriendshipRequest {

    String sender ;
    String receiver ;
    boolean isAccepted ;
    boolean isRejected ;
    boolean isHandled ;

    public FriendshipRequest (String sender , String receiver){
        this.sender = sender ;
        this.receiver = receiver ;
        isAccepted = false ;
        isRejected = false ;
        isHandled = false ;
    }

    public void setAccepted() {
        isAccepted = true;
        isHandled = true ;

        UserController.getUserByUsername(sender).addFriend(receiver);
        UserController.getUserByUsername(receiver).addFriend(sender);
    }

    public void setRejected() {
        isRejected = true;
        isHandled = true ;
    }

    public boolean isHandled() {
        return isHandled;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

}

package game.Controller.Chat;

import game.Model.User;

public class Message {
    private final User sender ;
    private final User receiver ;
    private final String message ;

    public Message (User sender , User receiver , String message){
        this.sender = sender ;
        this.receiver = receiver ;
        this.message = message ;
    }

    public User getReceiver() {
        return receiver;
    }

    public User getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}

package game.Controller.Chat;

import game.Model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private final String sender ;
    private final String receiver ;
    private final String message ;
    private MessageType messageType ;
    private String creationTime ;

    public Message (String  sender , String  receiver  , MessageType messageType , String message){
        this.sender = sender ;
        this.receiver = receiver ;
        this.messageType = messageType ;
        this.message = message ;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        creationTime = dtf.format(now);
    }

    public String getReceiver() {
        return receiver;
    }

    public String  getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getCreationTime() {
        return creationTime;
    }
}

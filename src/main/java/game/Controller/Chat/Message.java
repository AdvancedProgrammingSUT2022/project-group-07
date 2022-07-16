package game.Controller.Chat;

import game.Model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private final String sender ;
    private final String receiver ;
    private String message ;
    private MessageType messageType ;
    private String creationTime ;
    private String lastTimeEdited ;

    public Message (String  sender , String  receiver  , MessageType messageType , String message){
        this.sender = sender ;
        this.receiver = receiver ;
        this.messageType = messageType ;
        this.message = message ;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        creationTime = dtf.format(now);
        lastTimeEdited = creationTime ;
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

    public void setMessage (String newMessage){
        this.message = newMessage ;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        lastTimeEdited = dtf.format(now);
    }

    public String getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return ((Objects.equals(sender, message1.sender) && Objects.equals(receiver, message1.receiver))
                || (Objects.equals(sender, message1.receiver) && Objects.equals(receiver, message1.sender)))
                && Objects.equals(message, message1.message)
                && messageType == message1.messageType
                && Objects.equals(creationTime, message1.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver, message, messageType, creationTime);
    }
}

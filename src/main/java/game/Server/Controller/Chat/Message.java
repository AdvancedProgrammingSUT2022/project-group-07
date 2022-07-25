package game.Server.Controller.Chat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message {
    private final String sender ;
    private final String receiver ;
    private String message ;
    private String senderVersionMessage ;
    private final MessageType messageType ;
    private final String creationTime ;
    private String lastTimeEdited ;
    private boolean isSent ;
    private boolean isSeen ;

    public Message (String  sender , String  receiver  , MessageType messageType , String message){
        this.sender = sender ;
        this.receiver = receiver ;
        this.messageType = messageType ;
        this.message = message ;
        this.senderVersionMessage = message ;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        creationTime = dtf.format(now);
        lastTimeEdited = creationTime ;
        isSent = false ;
        isSeen = false ;
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

    public String getSenderVersionMessage() {
        return senderVersionMessage;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessage (String newMessage){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        lastTimeEdited = dtf.format(now);
        this.message = newMessage ;
        setSenderVersionMessage(newMessage);
    }

    public void setSenderVersionMessage (String newMessage){
        this.senderVersionMessage = newMessage ;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public void setSeen (){
        isSeen = true ;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public void setSent (){
        isSent = true ;
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

package Model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Notification {
    private String message ;
    private int turnOfCreation ;
    private String realTimeCreated ;

    public Notification (String message , int turnOfCreation){
        this.message = message ;
        this.turnOfCreation = turnOfCreation;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        realTimeCreated = dtf.format(now) ;
    }

    public int getTurnOfCreation() {
        return turnOfCreation;
    }

    public String getMessage() {
        return message;
    }

    public String getRealTimeCreated() {
        return realTimeCreated;
    }
}

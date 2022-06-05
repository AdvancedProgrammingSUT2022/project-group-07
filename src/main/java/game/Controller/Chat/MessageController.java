package game.Controller.Chat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MessageController {
    private static ArrayList<Message> messages = new ArrayList<>() ;

    public static void loadMessages (){
        try {
            ArrayList<Message> messageArrayList = new ArrayList<>();
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/game/database/messages.json")));
            messageArrayList = new Gson().fromJson(json , new TypeToken<List<Message>>(){}.getType());
            if (messageArrayList != null)
                messages = messageArrayList;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveMessages (){
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/game/database/messages.json");
            fileWriter.write(new Gson().toJson(messages));
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMessage (Message message){
        messages.add(message);
    }

    public static void removeMessage (Message message){
        messages.remove(message);
    }

    public static ArrayList<Message> getMessages() {
        return messages;
    }

    public static ArrayList<Message> getMessages (User first , User second){
        ArrayList<Message> out = new ArrayList<>();
        for (Message message : messages) {
            if ((message.getReceiver().getUsername().equals(first.getUsername()) && message.getSender().getUsername().equals(second.getUsername()))
                    || (message.getReceiver().getUsername().equals(second.getUsername()) && message.getSender().getUsername().equals(first.getUsername()))
            )
                out.add(message);
        }
        return out;
    }

}

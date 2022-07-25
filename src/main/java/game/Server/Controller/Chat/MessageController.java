package game.Server.Controller.Chat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Common.Model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MessageController {
    private static ArrayList<Message> messages = new ArrayList<>() ;
    private static ArrayList<ChatGroup> chatGroups = new ArrayList<>();

    private static Message lastMessageToAttend ;

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
        message.setSent();
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
            if ((message.getReceiver().equals(first.getUsername()) && message.getSender().equals(second.getUsername()))
                    || (message.getReceiver().equals(second.getUsername()) && message.getSender().equals(first.getUsername()))
            )
                out.add(message);
        }
        return out;
    }

    public static void loadChatGroups (){
        try {
            ArrayList<ChatGroup> chatGroupArrayList = new ArrayList<>();
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/game/database/chatGroups.json")));
            chatGroupArrayList = new Gson().fromJson(json , new TypeToken<List<ChatGroup>>(){}.getType());
            if (chatGroupArrayList != null)
                chatGroups = chatGroupArrayList;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveChatGroups (){
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/game/database/chatGroups.json");
            fileWriter.write(new Gson().toJson(chatGroups));
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addChatGroup (ChatGroup chatGroup){
        chatGroups.add(chatGroup);
    }

    public static void removeChatGroup (ChatGroup chatGroup){
        chatGroups.remove(chatGroup);
    }

    public static ArrayList<ChatGroup> getChatGroups() {
        return chatGroups;
    }

    public static ArrayList<Message> getPublicMessages() {
        ArrayList<Message> out = new ArrayList<>();
        for (Message message : messages) {
            if (message.getMessageType().equals(MessageType.PUBLIC))
                out.add(message);
        }
        return out;
    }

    public static void setLastMessageToAttend(Message lastMessageToAttend) {
        MessageController.lastMessageToAttend = lastMessageToAttend;
    }

    public static Message getLastMessageToAttend() {
        return lastMessageToAttend;
    }
}

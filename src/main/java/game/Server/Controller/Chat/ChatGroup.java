package game.Server.Controller.Chat;

import game.Common.Model.User;

import java.util.ArrayList;

public class ChatGroup {
    String name ;
    ArrayList<String > users ;
    ArrayList<Message> messages ;

    public ChatGroup (String name , ArrayList<String> users){
        this.messages = new ArrayList<>();
        this.name = name ;
        this.users = users ;
    }

    public ChatGroup ( ArrayList<User> users , String name){
        this.messages = new ArrayList<>();
        this.name = name ;
        this.users = new ArrayList<>();
        for (User user : users)
            this.users.add(user.getUsername());
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}

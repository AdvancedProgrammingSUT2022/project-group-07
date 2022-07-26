package game.Server.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Common.Model.FriendshipRequest;
import game.Common.Model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FriendshipRequestController {

    public static ArrayList<FriendshipRequest> friendshipRequests = new ArrayList<>() ;

    public static void loadFriendshipRequests (){
        try {
            ArrayList<FriendshipRequest> loadFriendshipRequest = new ArrayList<>();
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/game/database/friendshipRequests.json")));
            loadFriendshipRequest = new Gson().fromJson(json , new TypeToken<List<FriendshipRequest>>(){}.getType());
            if (loadFriendshipRequest != null) friendshipRequests = loadFriendshipRequest;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void saveFriendshipRequests (){
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/game/database/friendshipRequests.json");
            fileWriter.write(new Gson().toJson(friendshipRequests));
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<FriendshipRequest> getRequestsOfPlayer (String username){
        User user = UserController.getUserByUsername(username);
        ArrayList<FriendshipRequest> out = new ArrayList<>();
        if (user==null) return out;
        for (FriendshipRequest friendshipRequest : friendshipRequests) {
            if (friendshipRequest.getSender().equals(username))
                out.add(friendshipRequest);
        }
        return out ;
    }

    public static ArrayList<FriendshipRequest> getRequestsForPlayer (String username){
        User user = UserController.getUserByUsername(username);
        ArrayList<FriendshipRequest> out = new ArrayList<>();
        if (user==null) return out;
        for (FriendshipRequest friendshipRequest : friendshipRequests) {
            if (friendshipRequest.getReceiver().equals(username))
                out.add(friendshipRequest);
        }
        return out ;
    }

    public static FriendshipRequest getFriendshipRequest (FriendshipRequest friendshipRequest){
        for (FriendshipRequest request : friendshipRequests) {
            if (request.getReceiver().equals(friendshipRequest.getReceiver()) && request.getSender().equals(friendshipRequest.getSender()))
                return request;
        }
        return null ;
    }

    public static void addFriendshipRequest (FriendshipRequest friendshipRequest){
        friendshipRequests.add(friendshipRequest);
        saveFriendshipRequests();
        loadFriendshipRequests();
    }

    public static ArrayList<FriendshipRequest> getFriendshipRequests() {
        return friendshipRequests;
    }
}

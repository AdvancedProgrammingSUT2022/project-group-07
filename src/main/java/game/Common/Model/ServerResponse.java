package game.Common.Model;

import game.Common.Enum.Network.TypeOfResponseParameter;
import game.Common.Enum.TypeOfResponse;
import game.Server.Controller.Chat.Message;
import game.Server.Controller.game.GameControllerDecoy;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerResponse {

    TypeOfResponse typeOfResponse ;
    String message ;
    HashMap<TypeOfResponseParameter , Object> responseHashMap = new HashMap<>() ;
    int imageSize ;

    User user ;
    ArrayList<User> users ;

    ArrayList<FriendshipRequest> friendshipRequests = new ArrayList<>() ;

    GameControllerDecoy gameControllerDecoy ;
    String queuedGameUUID ;

    ArrayList<String> uuids ;
    ArrayList<Message> messagesArrayList = new ArrayList<>() ;


    public void setUuids(ArrayList<String> uuids) {
        this.uuids = uuids;
    }

    public ArrayList<String> getUuids() {
        return uuids;
    }

    public ServerResponse (TypeOfResponse typeOfResponse , GameControllerDecoy gameControllerDecoy , String UUID){
        this.typeOfResponse = typeOfResponse ;
        this.gameControllerDecoy = gameControllerDecoy ;
        this.queuedGameUUID = UUID ;
    }

    public ServerResponse (TypeOfResponse typeOfResponse , ArrayList<Message> messages , String message){
        this.typeOfResponse = typeOfResponse ;
        this.messagesArrayList = messages ;
    }

    public String getQueuedGameUUID() {
        return queuedGameUUID;
    }

    public ServerResponse (TypeOfResponse typeOfResponse , ArrayList<FriendshipRequest> friendshipRequests , boolean isFriendship){
        this.typeOfResponse = typeOfResponse ;
        this.friendshipRequests = friendshipRequests ;
    }

    public ServerResponse (TypeOfResponse typeOfResponse , int imageSize){
        this.typeOfResponse = typeOfResponse ;
        this.imageSize = imageSize ;
    }

    public ServerResponse (TypeOfResponse typeOfResponse){
        this.typeOfResponse = typeOfResponse ;
    }

    public ServerResponse (TypeOfResponse typeOfResponse , String message){
        this.typeOfResponse = typeOfResponse ;
        this.message = message ;
    }

    public ServerResponse(TypeOfResponse typeOfResponse, String message, HashMap<TypeOfResponseParameter, Object> responseHashMap) {
        this.typeOfResponse = typeOfResponse;
        this.message = message;
        this.responseHashMap = responseHashMap;
        if (responseHashMap.containsKey(TypeOfResponseParameter.USER))
            this.user = (User) responseHashMap.get(TypeOfResponseParameter.USER);
    }

    public ServerResponse(TypeOfResponse typeOfResponse, HashMap<TypeOfResponseParameter, Object> responseHashMap) {
        this.typeOfResponse = typeOfResponse;
        this.responseHashMap = responseHashMap;
    }

    public ServerResponse (TypeOfResponse typeOfResponse , User user){
        this.typeOfResponse = typeOfResponse ;
        this.user = user ;
    }

    public ServerResponse (TypeOfResponse typeOfResponse , ArrayList<User> users){
        this.typeOfResponse = typeOfResponse ;
        this.users = users ;
    }

    public TypeOfResponse getTypeOfResponse() {
        return typeOfResponse;
    }

    public String getMessage() {
        return message;
    }

    public HashMap<TypeOfResponseParameter, Object> getResponseHashMap() {
        return responseHashMap;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public int getImageSize() {
        return imageSize;
    }

    public ArrayList<FriendshipRequest> getFriendshipRequests() {
        return friendshipRequests;
    }

    public GameControllerDecoy getGameControllerDecoy() {
        return gameControllerDecoy;
    }

    public ArrayList<Message> getMessagesArrayList() {
        return messagesArrayList ;
    }
}

package game.Common.Model.Network;

import game.Client.ClientDataController;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.Network.TypeOfRequestParameter;
import game.Common.Model.FriendshipRequest;
import game.Common.Model.User;
import game.Server.Controller.Chat.Message;
import game.Server.Controller.game.GameControllerDecoy;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientRequest {

    private Message message ;
    private final TypeOfRequest typeOfRequest ;

    private GameControllerDecoy gameControllerDecoy;

    private HashMap<TypeOfRequestParameter, Object> params = new HashMap<>() ;
    byte[] imageByteArray ;
    int imageSize ;
    FriendshipRequest friendshipRequest ;
    ArrayList<String> usernames = new ArrayList<>() ;
    String gameUUID ;

    String newText ;

    public String getNewText() {
        return newText;
    }

    public ClientRequest(TypeOfRequest typeOfRequest, String username, String username1) {
        this.typeOfRequest = typeOfRequest ;
        usernames.add(username);
        usernames.add(username1);
    }

    public Message getMessage() {
        return message;
    }

    public ClientRequest(TypeOfRequest typeOfRequest, Message message) {
        this.typeOfRequest = typeOfRequest ;
        this.message = message ;
    }

    public ClientRequest(TypeOfRequest typeOfRequest, Message message , String text) {
        this.typeOfRequest = typeOfRequest ;
        this.message = message ;
        this.newText = text ;
    }

    public GameControllerDecoy getGameControllerDecoy() {
        return gameControllerDecoy;
    }

    public ClientRequest (TypeOfRequest typeOfRequest , FriendshipRequest friendshipRequest){
        this.typeOfRequest = typeOfRequest ;
        this.friendshipRequest = friendshipRequest;
    }

    public ClientRequest (TypeOfRequest typeOfRequest , int imageSize){
        this.typeOfRequest = typeOfRequest ;
        this.imageSize = imageSize ;
        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername());
        this.params = params ;
    }

    public ClientRequest (TypeOfRequest typeOfRequest , User user){
        this.typeOfRequest = typeOfRequest ;
        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , user.getUsername());
        this.params = params ;
    }

    public ClientRequest (TypeOfRequest typeOfRequest){
        this.typeOfRequest = typeOfRequest ;
    }

    public ClientRequest (TypeOfRequest typeOfRequest , String uuid){
        this.typeOfRequest = typeOfRequest ;
        this.gameUUID = uuid ;
        this.params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername());
    }

    public ClientRequest (TypeOfRequest typeOfRequest , ArrayList<String> usernames){
        this.typeOfRequest = typeOfRequest ;
        this.usernames = usernames ;
    }

    public ClientRequest(TypeOfRequest typeOfRequest, HashMap<TypeOfRequestParameter, Object> params) {
        this.typeOfRequest = typeOfRequest;
        this.params = params;
    }

    public ClientRequest (TypeOfRequest typeOfRequest , byte[] imageByteArray){
        this.typeOfRequest = typeOfRequest ;
        this.imageByteArray = imageByteArray ;
        HashMap<TypeOfRequestParameter , Object> params = new HashMap<>();
        params.put(TypeOfRequestParameter.USERNAME , ClientDataController.getCurrentUser().getUsername());
        this.params = params ;
    }

    public ClientRequest(TypeOfRequest typeOfRequest, GameControllerDecoy gameControllerDecoy, String uuid) {
        this.typeOfRequest = typeOfRequest ;
        this.gameControllerDecoy = gameControllerDecoy ;
        this.gameUUID = uuid ;
    }

    public TypeOfRequest getTypeOfRequest() {
        return typeOfRequest;
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public HashMap<TypeOfRequestParameter, Object> getParams() {
        return params;
    }

    public int getImageSize() {
        return imageSize ;
    }

    public FriendshipRequest getFriendshipRequest() {
        return friendshipRequest;
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public String getGameUUID() {
        return gameUUID;
    }
}

package game.Common.Model.Network;

import game.Client.ClientDataController;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.Network.TypeOfRequestParameter;
import game.Common.Model.FriendshipRequest;
import game.Common.Model.User;

import java.net.Socket;
import java.util.HashMap;

public class ClientRequest {

    private final TypeOfRequest typeOfRequest ;
    private HashMap<TypeOfRequestParameter, Object> params = new HashMap<>() ;
    byte[] imageByteArray ;
    int imageSize ;
    FriendshipRequest friendshipRequest ;

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
}

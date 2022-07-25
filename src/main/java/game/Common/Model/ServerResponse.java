package game.Common.Model;

import game.Common.Enum.Network.TypeOfResponseParameter;
import game.Common.Enum.TypeOfResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerResponse {

    TypeOfResponse typeOfResponse ;
    String message ;
    HashMap<TypeOfResponseParameter , Object> responseHashMap ;

    User user ;
    ArrayList<User> users ;

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
}

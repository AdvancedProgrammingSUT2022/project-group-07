package game.Server;

import game.Common.Enum.Network.TypeOfRequestParameter;
import game.Common.Enum.Network.TypeOfResponseParameter;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Common.Model.User;
import game.Server.Controller.UserController;
import game.Server.Controller.menu.ProfileValidation;

import java.util.HashMap;
import java.util.Objects;

public class RequestHandler {

    public static ServerResponse handle (ClientRequest clientRequest){
        ServerResponse response = new ServerResponse(TypeOfResponse.OK , "sample response") ;

        switch (clientRequest.getTypeOfRequest()){
            case SIGN_UP -> response = handleSignup (clientRequest);
            case LOGIN -> response = handleLogin (clientRequest);
            case LOGOUT -> response = handleLogout(clientRequest);
            case GET_SCOREBOARD -> response = handleGetScoreBoard(clientRequest);
        }

        System.out.println("handled");
        return response ;
    }

    private static ServerResponse handleSignup(ClientRequest clientRequest) {
        ServerResponse out ;
        HashMap<TypeOfRequestParameter , Object> params = clientRequest.getParams() ;

        String name = (String) params.get(TypeOfRequestParameter.USERNAME);
        String pass = (String) params.get(TypeOfRequestParameter.PASSWORD);
        String nick = (String) params.get(TypeOfRequestParameter.NICKNAME);

        if (ProfileValidation.usernameIsUsed(name))
            out = new ServerResponse(TypeOfResponse.FORBIDDEN ,"user with username " + name + " already exists");
        else if (ProfileValidation.nicknameIsUsed(nick))
            out = new ServerResponse(TypeOfResponse.FORBIDDEN ,"user with nickname " + nick + " already exists");
        else if (!ProfileValidation.usernameIsValid(name))
            out = new ServerResponse(TypeOfResponse.FORBIDDEN ,"invalid username : at least 3 characters and must have at least a letter");
        else if (!ProfileValidation.passwordIsValid(pass))
            out = new ServerResponse(TypeOfResponse.FORBIDDEN ,"invalid password : at least 4 characters (a capital and a small) and a number");
        else if (!ProfileValidation.nicknameIsValid(nick))
            out = new ServerResponse(TypeOfResponse.FORBIDDEN ,"invalid nickname : only alphabetical characters!");
        else {
            User user = new User(name , pass , nick);
            UserController.addUser(user);
            out = new ServerResponse(TypeOfResponse.OK , "user created!") ;
        }
        return out ;
    }

    public static ServerResponse handleLogin (ClientRequest clientRequest){
        HashMap<TypeOfRequestParameter , Object> params = clientRequest.getParams() ;

        String name = (String) params.get(TypeOfRequestParameter.USERNAME);
        String pass = (String) params.get(TypeOfRequestParameter.PASSWORD);

        if (name.isEmpty())
            return new ServerResponse(TypeOfResponse.FORBIDDEN ,"Please enter your username!");
        else if (pass.isEmpty())
            return new ServerResponse(TypeOfResponse.FORBIDDEN ,"Please enter your password!");

        User user = UserController.getUserByUsername(name);

        if (user == null)
            return new ServerResponse(TypeOfResponse.FORBIDDEN ,"No user with this username!");

        if (!user.getPassword().equals(pass))
            return new ServerResponse(TypeOfResponse.FORBIDDEN ,"username and password didn't match!");
        else {
            UserController.login(user);
            return new ServerResponse(TypeOfResponse.OK , user) ;
        }
    }

    private static ServerResponse handleLogout(ClientRequest clientRequest) {
        User user = UserController.getUserByUsername((String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME)) ;
        if (user != null) user.setLoggedIn(false);
        return new ServerResponse(TypeOfResponse.OK) ;
    }

    private static ServerResponse handleGetScoreBoard(ClientRequest clientRequest) {
        return new ServerResponse(TypeOfResponse.OK , UserController.getUsers()) ;
    }
}

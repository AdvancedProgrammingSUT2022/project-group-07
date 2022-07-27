package game.Server;

import game.Common.Enum.Network.TypeOfRequestParameter;
import game.Common.Enum.Network.TypeOfResponseParameter;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.FriendshipRequest;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Common.Model.User;
import game.Server.Controller.Chat.Message;
import game.Server.Controller.Chat.MessageController;
import game.Server.Controller.FriendshipRequestController;
import game.Server.Controller.UserController;
import game.Server.Controller.game.GameController;
import game.Server.Controller.game.GameControllerDecoy;
import game.Server.Controller.menu.ProfileValidation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestHandler {

    public static ServerResponse handle (ClientRequest clientRequest) throws IOException {
        ServerResponse response = new ServerResponse(TypeOfResponse.OK , "sample response") ;

        switch (clientRequest.getTypeOfRequest()){
            case SIGN_UP -> response = handleSignup (clientRequest);
            case LOGIN -> response = handleLogin (clientRequest);
            case LOGOUT -> response = handleLogout(clientRequest);
            case GET_SCOREBOARD -> response = handleGetScoreBoard(clientRequest);
            case UPDATE_PROFILE_CHANGE_NICKNAME -> response = handleUpdateProfileChangeNickname(clientRequest);
            case UPDATE_PROFILE_CHANGE_PASSWORD -> response = handleUpdateProfileChangePassword(clientRequest);
            case UPDATE_PROFILE_CHANGE_AVATAR -> response = handleUpdateProfileChangeAvatar(clientRequest);
            case GET_USER_BY_USERNAME -> response = handleGetUserByUsername(clientRequest);
            case GET_FRIENDS -> response = handleGetFriends(clientRequest);
            case SEND_FRIENDSHIP_REQUEST -> response = handleSendFriendshipRequest(clientRequest);
            case GET_REQUESTS_FOR -> response = handleGetRequestsFor(clientRequest);
            case GET_REQUESTS_OF -> response = handleGetRequestsOf(clientRequest);
            case ACCEPT_FRIENDSHIP_REQUEST -> response = handleAcceptFriendshipRequest(clientRequest);
            case REJECT_FRIENDSHIP_REQUEST -> response = handleRejectFriendshipRequest(clientRequest);
            case GET_ONLINE_FRIENDS -> response = handleGetOnlineFriends(clientRequest);
            case CREATE_GAME -> response = handleCreateGame(clientRequest);
            case GET_GAMES_FOR_PLAYER -> response = handleGetGamesForPlayer(clientRequest);
            case JOIN_GAME -> response = handleJoinGame(clientRequest);
            case NEXT_TURN -> response = handleNextTurn(clientRequest);
            case NEW_MESSAGE -> response = handleNewMessage(clientRequest);
            case GET_ALL_USERS -> response = handleGetAllUsers(clientRequest);
            case GET_PUBLIC_CHAT -> response = handleGetPublicChat(clientRequest);
            case GET_PRIVATE_CHAT -> response = handleGetPrivateChat(clientRequest);
            case REMOVE_MESSAGE -> response = handleRemoveMessage(clientRequest);
            case EDIT_MESSAGE_FOR_ONE_SIDE -> response = handleEditMessageForOneSide(clientRequest);
            case EDIT_MESSAGE_FOR_BOTH_SIDES -> response = handleEditMessageForBothSides(clientRequest);
        }
        System.out.println("handled "+ clientRequest.getTypeOfRequest() +" in a proper way");
        return response ;
    }

    private static ServerResponse handleEditMessageForBothSides(ClientRequest clientRequest) {
        Message message = clientRequest.getMessage() ;
        MessageController.editMessageForBothSides(message , clientRequest.getNewText());
        return new ServerResponse(TypeOfResponse.OK);
    }

    private static ServerResponse handleEditMessageForOneSide(ClientRequest clientRequest) {
        Message message = clientRequest.getMessage() ;
        MessageController.editMessageForOneSide(message , clientRequest.getNewText());
        return new ServerResponse(TypeOfResponse.OK);
    }
    

    private static ServerResponse handleRemoveMessage(ClientRequest clientRequest) {
        MessageController.removeMessage(clientRequest.getMessage());
        return new ServerResponse(TypeOfResponse.OK);
    }

    private static ServerResponse handleGetPrivateChat(ClientRequest clientRequest) {
        ArrayList<String> users = clientRequest.getUsernames() ;
        return new ServerResponse(TypeOfResponse.OK , MessageController.getMessages(users.get(0) , users.get(1)) , "message");
    }

    private static ServerResponse handleGetPublicChat(ClientRequest clientRequest) {
        return new ServerResponse(TypeOfResponse.OK , MessageController.getPublicMessages() , "messages") ;
    }

    private static ServerResponse handleGetAllUsers(ClientRequest clientRequest) {
        return new ServerResponse(TypeOfResponse.OK , UserController.getUsers());
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

    private static ServerResponse handleUpdateProfileChangeNickname(ClientRequest clientRequest) {
        String newNickname = (String) clientRequest.getParams().get(TypeOfRequestParameter.NICKNAME) ;

        if (!ProfileValidation.nicknameIsValid(newNickname))
            return new ServerResponse(TypeOfResponse.FORBIDDEN , "invalid nickname : nickname can only have letters");
        else if (ProfileValidation.nicknameIsUsed(newNickname))
            return new ServerResponse(TypeOfResponse.FORBIDDEN , "user with nickname " + newNickname + " already exists");
        else {
            User user = UserController.getUserByUsername((String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME)) ;
            assert user != null;
            user.setNickname(newNickname);
            return new ServerResponse(TypeOfResponse.OK);
        }
    }

    private static ServerResponse handleUpdateProfileChangePassword(ClientRequest clientRequest) {
        String username = (String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME);
        String oldPass = (String) clientRequest.getParams().get(TypeOfRequestParameter.PASSWORD);
        String newPass = (String) clientRequest.getParams().get(TypeOfRequestParameter.NEW_PASSWORD);

        User user = UserController.getUserByUsername(username) ;

        assert user != null;
        if (!user.getPassword().equals(oldPass))
            return new ServerResponse(TypeOfResponse.FORBIDDEN ,"old password is invalid");
        else if (oldPass.equals(newPass))
            return new ServerResponse(TypeOfResponse.FORBIDDEN ,"please enter a new password");
        else if (!ProfileValidation.passwordIsValid(newPass))
            return new ServerResponse(TypeOfResponse.FORBIDDEN ,"invalid new password : must have at least 4" +
                    " characters (a capital and a small letter) and a number");
        else {
            user.setPassword(newPass);
            return new ServerResponse(TypeOfResponse.OK);
        }
    }

    private static ServerResponse handleUpdateProfileChangeAvatar(ClientRequest clientRequest) {
        User user = UserController.getUserByUsername((String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME)) ;
        if (user != null) return new ServerResponse(TypeOfResponse.WAITING , clientRequest.getImageSize());
        else return new ServerResponse(TypeOfResponse.BAD_REQUEST_FORMAT);
    }

    private static ServerResponse handleGetUserByUsername(ClientRequest clientRequest) {
        String username = (String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME);
        User user = UserController.getUserByUsername(username);
        if (user == null) return new ServerResponse(TypeOfResponse.NOT_FOUND , "No user with username " + username + " !");
        return new ServerResponse(TypeOfResponse.OK , user);
    }

    private static ServerResponse handleGetFriends(ClientRequest clientRequest) {
        String username = (String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME);
        User user = UserController.getUserByUsername(username);
        if (user == null) return new ServerResponse(TypeOfResponse.NOT_FOUND , "No user with username " + username + " !");

        HashMap<TypeOfResponseParameter, Object> responseHashMap = new HashMap<>() ;
        responseHashMap.put(TypeOfResponseParameter.USER , user.getFriends());
        return new ServerResponse(TypeOfResponse.OK , responseHashMap);
    }

    private static ServerResponse handleSendFriendshipRequest(ClientRequest clientRequest) {
        String sender = (String) clientRequest.getParams().get(TypeOfRequestParameter.SENDER);
        String receiver = (String) clientRequest.getParams().get(TypeOfRequestParameter.RECEIVER);

        FriendshipRequestController.addFriendshipRequest(new FriendshipRequest(sender , receiver));
        return new ServerResponse(TypeOfResponse.OK) ;
    }

    private static ServerResponse handleGetRequestsFor(ClientRequest clientRequest) {
        return new ServerResponse(TypeOfResponse.OK , FriendshipRequestController.getRequestsForPlayer((String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME)) , true);
    }

    private static ServerResponse handleGetRequestsOf(ClientRequest clientRequest) {
        return new ServerResponse(TypeOfResponse.OK , FriendshipRequestController.getRequestsOfPlayer((String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME)) , true);
    }

    private static ServerResponse handleRejectFriendshipRequest(ClientRequest clientRequest) {
        FriendshipRequest friendshipRequest = clientRequest.getFriendshipRequest();
        FriendshipRequest real = FriendshipRequestController.getFriendshipRequest(friendshipRequest);
        if (real == null) return new ServerResponse(TypeOfResponse.BAD_REQUEST_FORMAT);
        real.setRejected();
        FriendshipRequestController.saveFriendshipRequests();
        return new ServerResponse(TypeOfResponse.OK);
    }

    private static ServerResponse handleAcceptFriendshipRequest(ClientRequest clientRequest) {
        FriendshipRequest friendshipRequest = clientRequest.getFriendshipRequest();
        FriendshipRequest real = FriendshipRequestController.getFriendshipRequest(friendshipRequest);
        if (real == null) return new ServerResponse(TypeOfResponse.BAD_REQUEST_FORMAT);
        real.setAccepted();
        FriendshipRequestController.saveFriendshipRequests();
        return new ServerResponse(TypeOfResponse.OK);
    }

    private static ServerResponse handleGetOnlineFriends(ClientRequest clientRequest) {
        String username = (String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME);
        User user = UserController.getUserByUsername(username);
        if (user == null) new ServerResponse(TypeOfResponse.NOT_FOUND , "No user with username " + username +" was found!");

        ArrayList<User> users = new ArrayList<>();
        for (String friend : user.getFriends()) {
            User friendUser = UserController.getUserByUsername(friend) ;
            if (friendUser != null && friendUser.isLoggedIn())
                users.add(friendUser);
        }
        return new ServerResponse(TypeOfResponse.OK , users);
    }

    /**
     * a function to create a waiting game so that all players could join and then sends a notification to all invited players
     * @param clientRequest host game request with a list of all usernames in this game
     * @return server response
     */
    private static ServerResponse handleCreateGame(ClientRequest clientRequest) {
        ArrayList<String> playersOfThisGame = clientRequest.getUsernames();
        QueuedGame queuedGame = new QueuedGame(playersOfThisGame);
        queuedGame.start();
        QueuedGamesController.addQueuedGame(queuedGame);
        return new ServerResponse(TypeOfResponse.OK , "Game created !") ;
    }

    private static ServerResponse handleGetGamesForPlayer(ClientRequest clientRequest) {
        ArrayList<QueuedGame> queuedGames =
                QueuedGamesController.getQueuedGamesForPlayer(
                        (String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME)
                );
        ArrayList<String> uuids = new ArrayList<>();
        for (QueuedGame queuedGame : queuedGames)
            uuids.add(queuedGame.getUUID());
        ServerResponse serverResponse = new ServerResponse(TypeOfResponse.OK) ;
        serverResponse.setUuids(uuids) ;
        return serverResponse;
    }

    private static ServerResponse handleJoinGame (ClientRequest clientRequest){
        String uuid = clientRequest.getGameUUID();
        String username = (String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME);

        QueuedGame queuedGame = QueuedGamesController.getQueuedGameByUUID(uuid) ;
        if (queuedGame == null) return new ServerResponse(TypeOfResponse.NOT_FOUND);

        queuedGame.addPlayer(username);
        return new ServerResponse(TypeOfResponse.OK);
    }

    private static ServerResponse handleNextTurn(ClientRequest clientRequest) throws IOException {
        QueuedGame queuedGame = QueuedGamesController.getQueuedGameByUUID(clientRequest.getGameUUID()) ;

        GameControllerDecoy gameControllerDecoy = clientRequest.getGameControllerDecoy();

        GameController gameController = new GameController();
        GameController logical = gameController.deepCopyOut(gameControllerDecoy);
        logical.nextTurn(logical);

        gameControllerDecoy = new GameControllerDecoy(logical);

        for (String playersUsername : queuedGame.getPlayersUsernames())
            ServerMain.getServerHandlerByUsername(playersUsername).sendResponse(new ServerResponse(TypeOfResponse.GAME_UNDER_ACTION , gameControllerDecoy , queuedGame.getUUID()));

        return new ServerResponse(TypeOfResponse.OK) ;
    }

    private static ServerResponse handleNewMessage(ClientRequest clientRequest) {
        Message message = clientRequest.getMessage();
        switch (message.getMessageType()){
            case PRIVATE, PUBLIC -> {
                MessageController.addMessage(message);
                MessageController.saveMessages();
            }
            case GROUP -> {
//                message.getGroup().addMessage(message);
                MessageController.saveChatGroups();
            }
        }
        return new ServerResponse(TypeOfResponse.OK);
    }
}

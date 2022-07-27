package game.Server;

import game.Server.Controller.Chat.MessageController;
import game.Server.Controller.FriendshipRequestController;
import game.Server.Controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain extends Application {

    private static final int PORT_NUMBER = 8000 ;
    private static ServerSocket serverSocket ;
    private static ArrayList<ServerHandler> serverHandlers = new ArrayList<>() ;

    public static void main(String[] args) {

        UserController.loadUsers();
        FriendshipRequestController.loadFriendshipRequests();
        MessageController.loadMessages();

        launch();


    }

    public static ServerHandler getServerHandlerByUsername (String username){
        for (ServerHandler serverHandler : serverHandlers) {
            if (serverHandler.getUsername().equals(username))
                return serverHandler;
        }
        return null ;
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("server is up");
        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ServerHandler serverHandler = new ServerHandler(socket);
                    System.out.println("new connection to server !");
                    serverHandler.start();
                    serverHandlers.add(serverHandler);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
            }
        }
        catch (Exception ioException){
            System.out.println(ioException.getMessage());
            System.exit(1);
        }

        for (ServerHandler serverHandler : serverHandlers)
            serverHandler.closeConnection();
        System.out.println("server is down");
    }
}

package game.Server;

import game.Server.Controller.FriendshipRequestController;
import game.Server.Controller.UserController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    private static final int PORT_NUMBER = 8000 ;
    private static ServerSocket serverSocket ;

    public static void main(String[] args) {

        UserController.loadUsers();
        FriendshipRequestController.loadFriendshipRequests();

        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ServerHandler serverHandler = new ServerHandler(socket);
                    serverHandler.start();
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

    }

}

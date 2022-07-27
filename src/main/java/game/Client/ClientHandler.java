package game.Client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Server.Controller.game.GameController;
import game.Server.Controller.game.GameControllerDecoy;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread{

    final protected Socket socket ;
    protected DataOutputStream dataOutputStream ;
    protected DataInputStream dataInputStream ;

    protected ServerResponse serverResponse ;

    GameControllerDecoy gameControllerDecoy ;
    String gameUUID ;

    public String getGameUUID() {
        return gameUUID;
    }

    public GameControllerDecoy getGameControllerDecoy() {
        return gameControllerDecoy;
    }

    public Socket getSocket() {
        return socket;
    }

    public ClientHandler(Socket socket){
        this.socket = socket ;
        try {
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void sendRequest (ClientRequest clientRequest) throws IOException {
        dataOutputStream.writeUTF(new Gson().toJson(clientRequest));
        dataOutputStream.flush();
    }

    public ServerResponse getResponse () throws IOException {
        String response = dataInputStream.readUTF();
        serverResponse = new Gson().fromJson(response, new TypeToken<ServerResponse>() {}.getType());
        return serverResponse ;
    }

}

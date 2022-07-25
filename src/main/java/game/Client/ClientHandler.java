package game.Client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClientHandler{

    final protected Socket socket ;
    protected DataOutputStream dataOutputStream ;
    protected DataInputStream dataInputStream ;

    protected ServerResponse serverResponse ;

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

//    @Override
//    public void run() {
//        String response = null;
//
//        while (true) {
//            try {
//                response = dataInputStream.readUTF();
//                serverResponse = new Gson().fromJson(response, new TypeToken<ServerResponse>() {}.getType());
//            } catch (IOException e) {System.out.println(e.getMessage());}
//        }
//    }
}

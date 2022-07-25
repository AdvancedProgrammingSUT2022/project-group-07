package game.Server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Common.Model.User;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerHandler extends Thread {

    final protected Socket socket ;
    protected DataOutputStream dataOutputStream ;
    protected DataInputStream dataInputStream ;

    public ServerHandler(Socket socket){
        this.socket = socket ;
        try {
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                // getting request from client

                String request = dataInputStream.readUTF();
                ClientRequest request1 = new Gson().fromJson(request , new TypeToken<ClientRequest>(){}.getType());

                System.out.println(request1 + " received from client " + socket);
                System.out.println(request1.getTypeOfRequest());
                System.out.println(request1.getParams());

                // handling request
                ServerResponse serverResponse = RequestHandler.handle(request1);

                // sending response to client
                String response = new Gson().toJson(serverResponse);
                dataOutputStream.writeUTF(response);
                dataOutputStream.flush();

            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        closeConnection();
    }

    public void closeConnection (){
        do {
            try {
                socket.close();
                dataInputStream.close();
                dataOutputStream.close();
                System.out.println("Connection closed !");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } while (!socket.isClosed()) ;
    }
}

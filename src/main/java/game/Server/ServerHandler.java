package game.Server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.Network.TypeOfRequestParameter;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Common.Model.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerHandler extends Thread {

    final protected Socket socket ;
    protected String username ;
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
                ClientRequest clientRequest = new Gson().fromJson(request , new TypeToken<ClientRequest>(){}.getType());

                System.out.println(clientRequest + " received from client " + socket);
                System.out.println(clientRequest.getTypeOfRequest());
                System.out.println(clientRequest.getParams());

                // handling request
                ServerResponse serverResponse = RequestHandler.handle(clientRequest);

                // setting name for this server handler for future use
                if (clientRequest.getTypeOfRequest().equals(TypeOfRequest.LOGIN)
                        && serverResponse.getTypeOfResponse().equals(TypeOfResponse.OK)){
                    username = (String) clientRequest.getParams().get(TypeOfRequestParameter.USERNAME);
                    System.out.println(username + " set as username of server handler " + this);
                }

                // sending response to client
                String response = new Gson().toJson(serverResponse);
                dataOutputStream.writeUTF(response);
                dataOutputStream.flush();

            } catch (IOException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
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

    public String getUsername() {
        return username;
    }

    public void sendResponse (ServerResponse serverResponse) throws IOException {
        dataOutputStream.writeUTF(new Gson().toJson(serverResponse));
        dataOutputStream.flush();
    }

    public ClientRequest getResponseFromClient () throws IOException {
        String response = dataInputStream.readUTF();
        return new Gson().fromJson(response, new TypeToken<ClientRequest>() {}.getType());
    }

}

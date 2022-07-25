package game.Client;

import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Model.Network.ClientRequest;
import game.Server.Controller.UserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

public class Main extends Application {

    public static Stage mainStage;
    public static Scene scene;

    private static final int SERVER_PORT_NUMBER = 8000;
    private static ClientHandler clientHandler ;

    public static ClientHandler getClientHandler() {
        return clientHandler;
    }

    public static void main(String[] args) throws IOException {
        do {
            try {
                Socket socket = new Socket("localhost", SERVER_PORT_NUMBER);
                clientHandler = new ClientHandler(socket);
                break;
            } catch (Exception e){
                System.out.println("trying to connect to server");
                try {Thread.sleep(500);}
                catch (Exception ignored){}
            }
        } while (true) ;
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainStage.setResizable(false);
        Parent root = loadFXML("loginMenu");
        stage.setTitle("game");
        scene = new Scene(root);
        UserController.loadUsers();
        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadFXML(String name){
        try {
            URL address = new URL(Main.class.getResource("/game/fxml/" + name + ".fxml").toExternalForm());
            return FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeWindow() {
        mainStage.close();
    }

    public static void changeScene(String fxmlName) throws IOException {
        Parent root = loadFXML(fxmlName);
        scene.setRoot(root);
    }

    public static void loadNewStage (String stageTitle , String fxmlName){
        Stage newStage = new Stage();
        newStage.setOnCloseRequest(windowEvent -> {
            newStage.toBack();
            newStage.close();
        });
//        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setResizable(false);
        newStage.setTitle(stageTitle);
        Parent root = loadFXML(fxmlName);
        assert root != null;
        if (fxmlName.equals("technologyTreePage")){
            newStage.setWidth(1200);
            newStage.setHeight(750);
        }
        Scene newScene = new Scene(root);
        newStage.setScene(newScene);
        newStage.show();
    }
}

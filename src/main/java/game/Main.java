package game;

import game.Controller.UserController;
import game.Controller.game.GameController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    public static Stage mainStage;
    public static Scene scene;
    public static MediaPlayer mediaPlayer;
    public static void main(String[] args) {
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

    public static void playMenuMusic() {
        Media media = new Media(Main.class.getResource("/game/assets/music/1-02 Civilization V Theme - Menu Music.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}

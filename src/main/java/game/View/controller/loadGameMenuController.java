package game.View.controller;

import game.Controller.UserController;
import game.Controller.game.GameController;
import game.Controller.game.LogAndNotification.NotificationController;
import game.Main;
import game.Model.User;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class loadGameMenuController implements Initializable {

    public AnchorPane anchorPane;

    ArrayList<Button> saveButtons = new ArrayList<>();

    User loggedInUser = UserController.getCurrentUser() ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadButtons();
        addActionToButtons () ;

        for (int i=1 ; i<=5 ; i++)
            disableButton(i , saveButtons.get(i-1));

    }

    public void loadButtons (){
        anchorPane.getChildren().removeAll(saveButtons);
        saveButtons.clear();
        for (int i=1 ; i<=5 ; i++){
            Button button = new Button("Save " + i) ;
            button.setLayoutX(425);
            button.setLayoutY(250+i*50);
            saveButtons.add(button) ;
            anchorPane.getChildren().add(button);
        }
    }

    public void addActionToButtons (){
        for (int i=0 ; i<5 ; i++){
            int finalI = i;
            saveButtons.get(i).setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    try {
                        if (loadSave("save"+(finalI +1)))
                            Main.changeScene("gamePage");
                        else
                            System.out.println("can't load save");
                    }
                    catch (IOException e) {e.printStackTrace();}
                }
                else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
                    try {deleteSave("save"+(finalI + 1)) ;}
                    catch (FileNotFoundException e) {e.printStackTrace();}
                }
            });
        }
    }

    public void deleteSave (String name) throws FileNotFoundException {
        File file = new File(System.getProperty("user.dir")+"/src/main/resources/game/database/games/"+name+".json") ;
        PrintWriter writer = new PrintWriter(file);
        writer.print("");
        writer.close();
        loadButtons();
        addActionToButtons();
    }

    public void disableButton (int index , Button button){
        try {
            // TODO : handle next line !
            File file = new File(System.getProperty("user.dir")+"/src/main/resources/game/database/games/save"+index+".json") ;
            if (isFileEmpty(file)) button.setDisable(true);
        } catch (Exception e){System.out.println(e.getMessage());}
    }

    private boolean isFileEmpty(final File file) throws IOException {
        int limit = 4096;
        return file.length() < limit && FileUtils.readFileToString(file).trim().isEmpty();
    }

    public boolean loadSave (String name) throws IOException {
        File file = new File(System.getProperty("user.dir")+"/src/main/resources/game/database/games/"+name+".json") ;
        if (isFileEmpty(file)) return false;

        GameController gameController = GameController.getInstance() ;
        gameController.loadGame(name);
        gameController.setPlayers(gameController.getPlayers());
        NotificationController.runNotification(gameController);
        return true ;
    }

    public void back() throws IOException {
        Main.changeScene("gameMenu");
    }

}

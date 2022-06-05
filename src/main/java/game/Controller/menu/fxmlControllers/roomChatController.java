package game.Controller.menu.fxmlControllers;

import game.Controller.Chat.ChatGroup;
import game.Controller.Chat.MessageController;
import game.Controller.UserController;
import game.Model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class roomChatController implements Initializable {

    private static ArrayList<User> users = UserController.getUsers();
    private static Stage stage ;
    private static chatRoomController updater ;

    public TextField roomName;
    public Button closeBtn;
    private ArrayList<CheckBox> checkBoxes ;

    public GridPane gridPane;

    public static void setStage(Stage stage) {
        roomChatController.stage = stage;
    }

    public static void setUpdater(chatRoomController chatRoomController) {
        updater = chatRoomController ;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkBoxes = new ArrayList<>();
        int counter = 0 ;
        for (int i=0 ; i<gridPane.getRowCount() ; i++){
            for (int j=0 ; j<gridPane.getColumnCount() ; j++){
                if (counter>=users.size() )
                    continue;
                checkBoxes.add(new CheckBox(users.get(i).getUsername()));
                gridPane.add(checkBoxes.get(checkBoxes.size()-1) , j , i);
                counter++ ;
            }
        }
    }


    public void createRoom() {
        ArrayList<User > addedUser = new ArrayList<>();
        int checkedBoxes = 0 ;
        for (int counter=0 ; counter<checkBoxes.size() ; counter++) {
            if (checkBoxes.get(counter).isSelected()) {
                checkedBoxes++;
                addedUser.add(users.get(counter));
            }
        }
        String chatName = roomName.getText();
        if (chatName.isEmpty() || checkedBoxes<=1)
            return;
        MessageController.addChatGroup(new ChatGroup(addedUser , chatName));
        MessageController.saveChatGroups();
        updater.updateData();
        System.out.println("room created !");
        stage.close();
    }

    public void cancel() {
        stage.close();
    }

}

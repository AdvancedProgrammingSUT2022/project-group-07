package game.View.controller.ChatControllers;

import game.Controller.Chat.ChatGroup;
import game.Controller.Chat.MessageController;
import game.Controller.UserController;
import game.Model.User;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CreateNewRoomController implements Initializable {
    
    private static ArrayList<User> users = UserController.getUsers();

    public TextField roomName;
    public Button closeBtn;
    public Button createBtn ;

    private ArrayList<CheckBox> checkBoxes ;

    public GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        closeBtn.setOnMouseClicked(e -> ((Stage) closeBtn.getScene().getWindow()).close());

        createBtn.setOnMouseClicked(mouseEvent -> {
            ArrayList<User> addedUser = new ArrayList<>();
            int checkedBoxes = 0;
            for (int counter = 0; counter < checkBoxes.size(); counter++) {
                if (checkBoxes.get(counter).isSelected()) {
                    checkedBoxes++;
                    addedUser.add(users.get(counter));
                }
            }
            String chatName = roomName.getText();
            if (chatName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Choose a name for this room !");
                alert.show();
                return;
            }
            if (checkedBoxes == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Choose at least one player !");
                alert.show();
                return;
            }
            MessageController.addChatGroup(new ChatGroup(addedUser, chatName));
            MessageController.saveChatGroups();
            ((Stage) createBtn.getScene().getWindow()).close();
        });

        checkBoxes = new ArrayList<>();
        int counter = 0 ;
        for (int i=0 ; i<gridPane.getRowCount() ; i++){
            for (int j=0 ; j<gridPane.getColumnCount() ; j++){
                if (counter>=users.size() )
                    return;
                CheckBox newCheckBox = new CheckBox(users.get(counter).getUsername()) ;
                checkBoxes.add(newCheckBox);
                if (users.get(counter).getUsername().equals(UserController.getCurrentUser().getUsername()))
                    newCheckBox.setDisable(true);
                gridPane.add(checkBoxes.get(checkBoxes.size()-1) , j , i);
                counter++ ;
            }
        }
    }

}

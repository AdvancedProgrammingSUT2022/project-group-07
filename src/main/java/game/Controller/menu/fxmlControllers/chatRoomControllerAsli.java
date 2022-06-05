package game.Controller.menu.fxmlControllers;

import game.Controller.Chat.Message;
import game.Controller.Chat.MessageController;
import game.Controller.UserController;
import game.Model.User;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class chatRoomControllerAsli implements Initializable {

    User currentAudience ;

    public VBox chatOptionVBox;
    public VBox chatBoxVBox;
    public TextArea messageTextField;

    public void sendMessage() {
        String messageTextFieldText = messageTextField.getText();
        if (messageTextFieldText.isEmpty())
            return;
        Message message = new Message(UserController.getCurrentUser() , currentAudience , messageTextFieldText);
        messageTextField.clear();
        MessageController.addMessage(message);
        loadMessages();
        MessageController.saveMessages();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserController.loadUsers();
        MessageController.loadMessages();
        ArrayList<User> users = UserController.getUsers();
        for (User user : users) {
            if (user.equals(UserController.getCurrentUser()))
                continue;
            Button button = new Button(user.getUsername());
            button.setOnMouseClicked(mouseEvent -> {
                currentAudience = user ;
                System.out.println(currentAudience);
                loadMessages();
            });
            chatOptionVBox.getChildren().add(button);
        }
        currentAudience = users.get(0);
    }

    public void loadMessages (){
        ArrayList<Message> messages = MessageController.getMessages(UserController.getCurrentUser() , currentAudience);
        chatBoxVBox.getChildren().clear();
        for (Message message : messages) {
            HBox messageHBox = new HBox();
            messageHBox.getChildren().add(new Label(message.getMessage()));
            Button button = new Button("get Message data");
            button.setOnMouseClicked(mouseEvent -> System.out.println(messages));
            chatBoxVBox.getChildren().add(messageHBox) ;
        }
    }

}

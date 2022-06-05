package game.Controller.menu.fxmlControllers;

import game.Controller.Chat.ChatGroup;
import game.Controller.Chat.Message;
import game.Controller.Chat.MessageController;
import game.Controller.Chat.MessageType;
import game.Controller.UserController;
import game.Model.User;
import game.tempMain;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class chatRoomController implements Initializable {

    ArrayList<ChatGroup> chatGroups ;

    User currentAudience ;
    ChatGroup currentChatGroup ;
    MessageType messageType ;

    public VBox chatOptionVBox;
    public VBox chatBoxVBox;
    public TextArea messageTextField;

    public void sendMessage() {
        String messageTextFieldText = messageTextField.getText();
        if (messageTextFieldText.isEmpty())
            return;

        String audienceUsername = currentAudience==null ? "Greater Good" : currentAudience.getUsername();
        Message message = new Message(UserController.getCurrentUser().getUsername()
                , audienceUsername
                , messageType
                , messageTextFieldText);
        messageTextField.clear();
        switch (messageType){
            case PRIVATE -> {
                MessageController.addMessage(message);
                MessageController.saveMessages();
                loadMessages(MessageController.getMessages(UserController.getCurrentUser() , currentAudience));
            }
            case PUBLIC -> {
                MessageController.addMessage(message);
                MessageController.saveMessages();
                loadMessages(MessageController.getPublicMessages());
            }
            case GROUP -> {
                currentChatGroup.addMessage(message);
                MessageController.saveChatGroups();
                loadMessages(currentChatGroup.getMessages());
            }
        }
    }

    public void updateData (){
        chatOptionVBox.getChildren().clear();

        UserController.loadUsers();

        MessageController.loadMessages();

        MessageController.loadChatGroups();
        chatGroups = MessageController.getChatGroups();

        ArrayList<User> users = UserController.getUsers();

        // public chat button
        Button publicChatButton = new Button("Public Chat");
        publicChatButton.setOnMouseClicked(mouseEvent -> {
            currentAudience = null ;
            messageType = MessageType.PUBLIC ;
            loadMessages(MessageController.getPublicMessages());
        });
        chatOptionVBox.getChildren().add(publicChatButton);

        // user chat buttons
        for (User user : users) {
            if (user.equals(UserController.getCurrentUser()))
                continue;
            Button button = new Button(user.getUsername());
            button.setOnMouseClicked(mouseEvent -> {
                currentAudience = user ;
                messageType = MessageType.PRIVATE ;
                loadMessages(MessageController.getMessages(UserController.getCurrentUser() , currentAudience));
            });
            chatOptionVBox.getChildren().add(button);
        }

        // chat room buttons
        for (ChatGroup chatGroup : chatGroups) {
            Button button = new Button(chatGroup.getName());
            button.setOnMouseClicked(mouseEvent -> {
                currentChatGroup = chatGroup ;
                messageType = MessageType.GROUP ;
                loadMessages(chatGroup.getMessages());
            });
            chatOptionVBox.getChildren().add(button);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateData();
    }

    public void loadMessages (ArrayList<Message> messages){
        chatBoxVBox.getChildren().clear();
        for (Message message : messages) {
            HBox messageHBox = new HBox();

            if (message.getSender().equals(UserController.getCurrentUser().getUsername())) {
                messageHBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
                messageHBox.setStyle("-fx-pref-height: 30 ; -fx-background-color: yellow ;");
            }
            else {
                messageHBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                messageHBox.setStyle("-fx-pref-height: 30 ; -fx-background-color: red");
            }

            messageHBox.getChildren().add(new Label(message.getMessage()));

            Button button = new Button("get Message data");
            button.setOnMouseClicked(mouseEvent -> System.out.println(message));
            button.setStyle("background-color: Transparent; background-repeat:no-repeat; border: none;");

            messageHBox.getChildren().add(button);
            chatBoxVBox.getChildren().add(messageHBox) ;
        }
    }

    public void createNewRoom() {
        Stage stage = new Stage();
        Parent root = tempMain.loadFXML("roomUserSelectionPage");
        assert root != null;
        roomChatController.setStage(stage);
        roomChatController.setUpdater(this);
        stage.setScene(new Scene(root));
        stage.show();
    }

}

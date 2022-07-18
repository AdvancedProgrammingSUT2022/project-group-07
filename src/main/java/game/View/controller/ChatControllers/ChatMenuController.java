package game.View.controller.ChatControllers;

import game.Controller.Chat.*;
import game.Controller.UserController;
import game.Main;
import game.Model.User;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatMenuController implements Initializable {

    ArrayList<ChatGroup> chatGroups ;

    User currentAudience ;
    ChatGroup currentChatGroup ;
    MessageType messageType = MessageType.PUBLIC ;

    boolean exit = false ;

    public VBox chatOptionVBox;
    public VBox chatBoxVBox;
    public TextArea messageTextField;
    public Button backBtn;


    private int starter = 0 ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // message updater thread
        Thread thread = new Thread(() -> {
            Runnable messageUpdater = () -> {
                switch (messageType){
                    case PRIVATE -> loadMessages(MessageController.getMessages(UserController.getCurrentUser() , currentAudience));
                    case GROUP -> loadMessages(currentChatGroup.getMessages());
                    default -> loadMessages(MessageController.getPublicMessages());
                }
            } ;
            while (!exit) {
                Platform.runLater(messageUpdater);
                try {Thread.sleep(300);}
                catch (InterruptedException ignored) {}
            }
        });
        thread.start();

        // chat options updater
        Thread thread1 = new Thread(() -> {
            Runnable chatOptionsUpdater = () -> {
                ArrayList<User> users = UserController.getUsers();
                MessageController.loadChatGroups();
                chatGroups = MessageController.getChatGroups();
                // avoid unnecessary loading
                if (starter == users.size()-1 + chatGroups.size() + 1)
                    return;
                // it seems we need to load
                chatOptionVBox.getChildren().clear();
                createPublicChatButton();
                for (User user : users)
                    createUserChatButton(user);
                for (ChatGroup chatGroup : chatGroups)
                    createChatGroupButton(chatGroup);
                starter = users.size() + chatGroups.size() ;
            } ;
            while (!exit) {
                Platform.runLater(chatOptionsUpdater);
                try {Thread.sleep(300);}
                catch (InterruptedException ignored) {}
            }
        });
        thread1.start();


        backBtn.setOnMouseClicked(mouseEvent -> {
            System.out.println("back is being clicked");
            exit = true;
            try {
                Main.changeScene("mainMenu");
                System.out.println("manteghan menu bayad avaz she");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("back kar nakard chon ke :");
                System.out.println(e.getMessage());
            }
        });
    }

    public void sendMessage() {
        String messageTextFieldText = messageTextField.getText();
        if (messageTextFieldText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Message body can't be empty !");
            alert.show();
            return;
        }

        String audienceUsername = currentAudience==null ? "Public" : currentAudience.getUsername();
        Message message = new Message(UserController.getCurrentUser().getUsername()
                , audienceUsername
                , messageType
                , messageTextFieldText);
        messageTextField.clear();
        switch (messageType){
            case PRIVATE, PUBLIC -> {
                MessageController.addMessage(message);
                MessageController.saveMessages();
            }
            case GROUP -> {
                currentChatGroup.addMessage(message);
                MessageController.saveChatGroups();
            }
        }
    }


    public void createPublicChatButton (){
        Button publicChatButton = new Button("Public Chat");
        publicChatButton.setOnMouseClicked(mouseEvent -> {
            currentAudience = null ;
            messageType = MessageType.PUBLIC ;
        });
        chatOptionVBox.getChildren().add(publicChatButton);
    }

    public void createUserChatButton (User user){
        if (user.equals(UserController.getCurrentUser()))
            return;
        Button button = new Button(user.getUsername());
        button.setOnMouseClicked(mouseEvent -> {
            currentAudience = user ;
            messageType = MessageType.PRIVATE ;
        });
        chatOptionVBox.getChildren().add(button);
    }

    public void createChatGroupButton (ChatGroup chatGroup){
        Button button = new Button(chatGroup.getName());
        button.setOnMouseClicked(mouseEvent -> {
            currentChatGroup = chatGroup ;
            messageType = MessageType.GROUP ;
        });
        chatOptionVBox.getChildren().add(button);
    }

    public void loadMessages (ArrayList<Message> messages){
        chatBoxVBox.getChildren().clear();

        for (Message message : messages) {
            HBox messageHBox = new HBox();

            if (message.getSender().equals(UserController.getCurrentUser().getUsername())) {
                messageHBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            }
            else {
                messageHBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            }

            messageHBox.getChildren().add(new Label(message.getMessage()));

            if (!message.getSender().equals(UserController.getCurrentUser().getUsername()))
                message.setSeen();

            Button editButton = new Button("\uD83D\uDD8A");
            editButton.setOnMouseClicked(mouseEvent -> openEditMessageWindow(message));

            if (message.getSender().equals(UserController.getCurrentUser().getUsername()))
                messageHBox.getChildren().add(editButton);

            chatBoxVBox.getChildren().add(messageHBox) ;
        }
    }

    public void openEditMessageWindow(Message message){
        MessageController.setLastMessageToAttend(message);
        Main.loadNewStage("Edit message" , "editMessageWindow");
    }

    public void createNewRoom() {
        Main.loadNewStage("Create new room" , "createNewChatGroupPage");
    }

}

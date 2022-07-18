package game.View.controller.ChatControllers;

import game.Controller.Chat.ChatGroup;
import game.Controller.Chat.Message;
import game.Controller.Chat.MessageController;
import game.Controller.Chat.MessageType;
import game.Controller.UserController;
import game.Main;
import game.Model.User;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            exit = true;
            try {
                Main.changeScene("mainMenu");
            } catch (IOException e) {
                e.printStackTrace();
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

            messageHBox.getChildren().add(getAvatarImageView(message.getSender())) ;
            messageHBox.getChildren().add(new Label(message.getMessage()));
            String status = "" ;
            if (message.isSent())
                status = "🗸" ;
            if (message.isSeen())
                status += "🗸" ;
            Label messageStatus = new Label(status + "\t" + message.getCreationTime().substring(3));
            messageStatus.setStyle("-fx-font-size: 8 ; -fx-font-style: italic ; -fx-alignment: bottom-center");
            messageHBox.getChildren().add(messageStatus);

            if (!message.getSender().equals(UserController.getCurrentUser().getUsername()))
                message.setSeen();

            Button editButton = new Button("\uD83D\uDD8A");
            editButton.setOnMouseClicked(mouseEvent -> openEditMessageWindow(message));

            if (message.getSender().equals(UserController.getCurrentUser().getUsername()))
                messageHBox.getChildren().add(editButton);

            chatBoxVBox.getChildren().add(messageHBox) ;
        }
    }

    public ImageView getAvatarImageView (String sender){
        User senderUser = UserController.getUserByUsername(sender);
        ImageView avatarImageView = new ImageView();
        avatarImageView.setFitWidth(50);
        avatarImageView.setFitHeight(50);
        avatarImageView.setPreserveRatio(true);

        if (senderUser.getAvatarFilePath()==null || senderUser.getAvatarFilePath().isEmpty()){
            avatarImageView.setImage(new Image(Main.class.getResource("/game/images/avatars/"+senderUser.getAvatarNumber()+".png").toExternalForm()));
        } else {
            avatarImageView.setImage(new Image(senderUser.getAvatarFilePath()));
        }
        return avatarImageView ;
    }

    public void openEditMessageWindow(Message message){
        MessageController.setLastMessageToAttend(message);
        Main.loadNewStage("Edit message" , "editMessageWindow");
    }

    public void createNewRoom() {
        Main.loadNewStage("Create new room" , "createNewChatGroupPage");
    }

}

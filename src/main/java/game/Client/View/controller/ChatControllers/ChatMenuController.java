package game.Client.View.controller.ChatControllers;

import game.Client.ClientDataController;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Model.Network.ClientRequest;
import game.Common.Model.ServerResponse;
import game.Server.Controller.Chat.ChatGroup;
import game.Server.Controller.Chat.Message;
import game.Server.Controller.Chat.MessageController;
import game.Server.Controller.Chat.MessageType;
import game.Server.Controller.UserController;
import game.Client.Main;
import game.Common.Model.User;
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
//                switch (messageType){
//                    case PRIVATE -> loadMessages(MessageController.getMessages(UserController.getCurrentUser() , currentAudience));
//                    case GROUP -> loadMessages(currentChatGroup.getMessages());
//                    default -> loadMessages(MessageController.getPublicMessages());
//                }
                switch (messageType){
                    case PUBLIC -> loadPublicMessages() ;
                    case PRIVATE -> loadPrivateMessages() ;
                }
            } ;
            while (!exit) {
                Platform.runLater(messageUpdater);
                try {Thread.sleep(2000);}
                catch (InterruptedException ignored) {}
            }
        });
        thread.start();

        // chat options updater
        Thread thread1 = new Thread(() -> {
            Runnable chatOptionsUpdater = () -> {
                try {
                    Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_ALL_USERS));
                    ServerResponse serverResponse = Main.getClientHandler().getResponse();
                    ArrayList<User> users = serverResponse.getUsers();
                    if (starter == users.size()-1)
                        return;
                    chatOptionVBox.getChildren().clear();
                    createPublicChatButton();
                    for (User user : users)
                        createUserChatButton(user);
                    starter = users.size();
                } catch (IOException e) {
                    System.out.println("here");
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }

            } ;
            while (!exit) {
                Platform.runLater(chatOptionsUpdater);
                try {Thread.sleep(5000);}
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

    private void loadPrivateMessages() {
        try {
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_PRIVATE_CHAT , currentAudience.getUsername() , ClientDataController.getCurrentUser().getUsername() ));
            ServerResponse serverResponse = Main.getClientHandler().getResponse();
            ArrayList<Message> messages = serverResponse.getMessagesArrayList();
            loadMessages(messages);
        } catch (IOException e){
            System.out.println("ChatMenuController.loadPrivateMessages");
            System.out.println(e.getMessage());
        }
    }

    private void loadPublicMessages() {
        try {
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.GET_PUBLIC_CHAT));
            ServerResponse serverResponse = Main.getClientHandler().getResponse();
            ArrayList<Message> messages = serverResponse.getMessagesArrayList();
            loadMessages(messages);
        } catch (IOException e){
            System.out.println("ChatMenuController.loadPublicMessages");
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage() throws IOException {
        String messageTextFieldText = messageTextField.getText();
        if (messageTextFieldText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Message body can't be empty !");
            alert.show();
            return;
        }

        String audienceUsername = currentAudience==null ? "Public" : currentAudience.getUsername();
        Message message = new Message(ClientDataController.getCurrentUser().getUsername()
                , audienceUsername
                , messageType
                , messageTextFieldText);
        messageTextField.clear();

        Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.NEW_MESSAGE , message));
        ServerResponse serverResponse = Main.getClientHandler().getResponse();
        System.out.println(serverResponse.getTypeOfResponse());
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
        if (user.equals(ClientDataController.getCurrentUser()))
            return;
        Button button = new Button(user.getUsername());
        button.setOnMouseClicked(mouseEvent -> {
            currentAudience = user ;
            messageType = MessageType.PRIVATE ;
        });
        chatOptionVBox.getChildren().add(button);
    }

//    public void createChatGroupButton (ChatGroup chatGroup){
//        Button button = new Button(chatGroup.getName());
//        button.setOnMouseClicked(mouseEvent -> {
//            currentChatGroup = chatGroup ;
//            messageType = MessageType.GROUP ;
//        });
//        chatOptionVBox.getChildren().add(button);
//    }

    public void loadMessages (ArrayList<Message> messages){
        chatBoxVBox.getChildren().clear();

        for (Message message : messages) {
            HBox messageHBox = new HBox();

            if (message.getSender().equals(ClientDataController.getCurrentUser().getUsername())) {
                messageHBox.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            }
            else {
                messageHBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            }

            if (!message.getSender().equals(ClientDataController.getCurrentUser().getUsername()))
                message.setSeen();

            messageHBox.getChildren().add(getAvatarImageView(message.getSender())) ;

            Label label = new Label(message.getMessage());
            label.setStyle("-fx-text-fill: white; -fx-font-size: 20;  -fx-padding: 15px");
            messageHBox.getChildren().add(label);

            String status = "" ;
            if (message.isSent()) status += "🗸" ;
            if (message.isSeen()) status += "🗸" ;
            status += "\t" + message.getCreationTime().substring(3) ;
            Label messageStatus = new Label(status);
            messageStatus.setStyle("-fx-text-fill: white; -fx-font-size: 8 ;  -fx-padding: 10px ; -fx-font-style: italic ;");
            messageHBox.getChildren().add(messageStatus);

            Button editButton = new Button("\uD83D\uDD8A");
            editButton.setScaleX(0.5);
            editButton.setScaleY(0.5);
            editButton.setOnMouseClicked(mouseEvent -> openEditMessageWindow(message));

            if (message.getSender().equals(ClientDataController.getCurrentUser().getUsername())) {
                ImageView imageView = new ImageView(new Image(getClass().getResource(
                        "/game/assets/Backgrounds/yours.png").toExternalForm()));
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                messageHBox.getChildren().add(imageView);
                messageHBox.getChildren().add(editButton);
            }
            else {
                ImageView imageView = new ImageView(new Image(
                        getClass().getResource("/game/assets/Backgrounds/theirs.png").toExternalForm()
                ));
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                messageHBox.getChildren().add(imageView);
            }
            chatBoxVBox.getChildren().add(messageHBox) ;
        }
    }

    public ImageView getAvatarImageView (String sender){
        User senderUser = UserController.getUserByUsername(sender);
        ImageView avatarImageView = new ImageView();
        avatarImageView.setFitWidth(50);
        avatarImageView.setFitHeight(50);
        avatarImageView.setPreserveRatio(true);

        if (senderUser==null)
            avatarImageView.setImage(new Image(Main.class.getResource("/game/images/avatars/1.png").toExternalForm()));

        else if (senderUser.getAvatarFilePath()==null || senderUser.getAvatarFilePath().isEmpty()){
            avatarImageView.setImage(new Image(Main.class.getResource("/game/images/avatars/"+senderUser.getAvatarNumber()+".png").toExternalForm()));
        }
        else {
            try {
                avatarImageView.setImage(new Image(senderUser.getAvatarFilePath()));
            } catch (Exception e){
                avatarImageView.setImage(new Image(Main.class.getResource("/game/images/avatars/1.png").toExternalForm()));
            }
        }
        return avatarImageView ;
    }

    public void openEditMessageWindow(Message message){
        ClientDataController.setLastMessageToAttend(message);
        Main.loadNewStage("Edit message" , "editMessageWindow");
    }

    public void createNewRoom() {
        Main.loadNewStage("Create new room" , "createNewChatGroupPage");
    }

}
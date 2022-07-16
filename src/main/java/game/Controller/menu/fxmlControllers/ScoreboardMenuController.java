package game.Controller.menu.fxmlControllers;

import game.Controller.UserController;
import game.Main;
import game.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ScoreboardMenuController implements Initializable {

    @FXML
    BorderPane borderPane;
    @FXML
    Button backButton;
    @FXML
    VBox listVBox ;
    @FXML
    ScrollPane scrollPane ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String cssFile1 = this.getClass().getResource("/game/CSS/scoreboardStyle.css").toExternalForm();
        String cssFile2 = this.getClass().getResource("/game/CSS/loginPage.css").toExternalForm();
        borderPane.getStylesheets().addAll(cssFile1 , cssFile2) ;

        UserController.loadUsers();
        ArrayList<User> users = UserController.getUsers();
        ArrayList<User> sortedUsers = new ArrayList<>();
        users.sort(Comparator.comparing(User::getScore).thenComparing(User::getUsername));
        for (User user : users)
            sortedUsers.add(0,user);

        createScoreBoard(sortedUsers);
    }

    public void createScoreBoard(ArrayList<User> users){
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.getChildren().add(new ImageView()) ;
        hBox.getChildren().add(new Label("Username"));
        hBox.getChildren().add(new Label("Score"));
        hBox.getChildren().add(new Label("Last Win"));
        hBox.getChildren().add(new Label("Last Login"));
        hBox.getChildren().add(new Label("Status"));
        listVBox.getChildren().add(hBox);
        for (int i=0 ; i<users.size() ; i++) {
            User user = users.get(i);
            HBox userHBox= new HBox();
            userHBox.setPadding(new Insets(10, 10, 10, 10));
            addLabel(i , user , userHBox);
            listVBox.getChildren().add(userHBox);
        }
    }

    public void addLabel (int index , User user , HBox hBox){

        String currentPath = System.getProperty("user.dir");
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        if (user.getAvatarFilePath()==null || user.getAvatarFilePath().isEmpty())
            imageView.setImage(new Image(currentPath+"/src/main/resources/game/images/avatars/"+user.getAvatarNumber()+".png"));
        else
            imageView.setImage(new Image(user.getAvatarFilePath()));

        Label[] labels = new Label[]{
                new Label(user.getUsername()) ,
                new Label(Integer.toString(user.getScore())) ,
                new Label(Integer.toString(user.getScore())) ,
                new Label(Integer.toString(user.getScore())) ,
                // new Label(user.getLastWin) ,
                // new Label(user.getLastLogin)
        } ;
        hBox.getChildren().add(imageView);
        for (Label label : labels) {
            if (UserController.getCurrentUser()!=null
                    && user.getUsername().equals(UserController.getCurrentUser().getUsername()))
                label.getStyleClass().add("logginedLabel");
            hBox.getChildren().add(label);
        }
        Label onlineStatus = new Label("⬤") ;
        onlineStatus.setStyle("-fx-font-size: 60");
        if (user.isLoggedIn())
            onlineStatus.setStyle("-fx-text-fill: #18c018");
        else
            onlineStatus.setStyle("-fx-text-fill: red");
        hBox.getChildren().add(onlineStatus);
    }

    public void backToMainMenu() throws IOException {
        Main.changeScene("mainMenu");
    }
}


package game.Controller.menu.fxmlControllers;

import game.Controller.UserController;
import game.Model.User;
import game.tempMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class ScoreBoardMenuController implements Initializable {

    @FXML
    BorderPane borderPane;
    @FXML
    VBox vBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UserController.loadUsers();
        ArrayList<User> users = UserController.getUsers();
        ArrayList<User> sortedUsers = new ArrayList<>();
        users.sort(Comparator.comparing(User::getScore).thenComparing(User::getUsername));
        for (User user : users)
            sortedUsers.add(0,user);
        for (int i=0 ; i<10 ; i++)
            createScoreBoard(sortedUsers);
    }

    public void createScoreBoard(ArrayList<User> users){
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.getChildren().add(new ImageView());
        hBox.getChildren().add(new Label("Username"));
        hBox.getChildren().add(new Label("Score"));
        hBox.getChildren().add(new Label("Last Time Won"));
        hBox.getChildren().add(new Label("Last Time Online"));
        vBox.getChildren().add(hBox);
        for (int i=0 ; i<users.size() ; i++) {
            hBox = new HBox();
            User user = users.get(i);
            hBox.setPadding(new Insets(10, 10, 10, 10));
            addLabel(i , user , hBox);
            vBox.getChildren().add(hBox);
        }
    }

    public void backToMainMenu() throws IOException {
        tempMain.changeScene("mainMenuPage");
    }

    public void addLabel (int index , User user , HBox hBox){
//        hBox.getChildren().add(new ImageView(user.getAvatar()));
        hBox.getChildren().add(new ImageView());

        Label[] labels = new Label[]{
                new Label(user.getUsername()) ,
                new Label(Integer.toString(user.getScore())) ,
                new Label(Integer.toString(user.getScore())) ,
                new Label(Integer.toString(user.getScore()))
        } ;
//        hBox.getChildren().add(new Label(user.getLastTimeWon()));
//        hBox.getChildren().add(new Label(user.getLastTimeOnline()));

        for (Label label : labels) {
            if (UserController.getCurrentUser()!=null && user.equals(UserController.getCurrentUser())){
                label.setStyle("""
                        -fx-background-color: Gold ;" +
                                                " -fx-border-radius: 20 ;" +
                                                " -fx-border-color: Red ;" +
                                                " -fx-border-width: 2 ;" +
                                                " -fx-border-style: dotted""");
            }
            hBox.getChildren().add(label);
        }
    }
}


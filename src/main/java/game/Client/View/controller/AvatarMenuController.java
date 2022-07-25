package game.Client.View.controller;

import game.Server.Controller.UserController;
import game.Client.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AvatarMenuController {
    @FXML
    private ImageView photo1;
    @FXML
    private ImageView photo2;
    @FXML
    private ImageView photo3;
    @FXML
    private ImageView photo4;
    @FXML
    private ImageView photo5;
    @FXML
    private ImageView photo6;
    @FXML
    private ImageView photo7;
    @FXML
    private ImageView photo8;
    @FXML
    private Button back;
    @FXML


    public void choosePhoto1(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(9);
        showConfirm("profile avatar successfully changed!");
    }

    public void choosePhoto2(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(7);
        showConfirm("profile avatar successfully changed!");
    }

    public void choosePhoto3(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(8);
        showConfirm("profile avatar successfully changed!");
    }

    public void choosePhoto4(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(1);
        showConfirm("profile avatar successfully changed!");
    }

    public void choosePhoto5(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(2);
        showConfirm("profile avatar successfully changed!");
    }

    public void choosePhoto6(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(4);
        showConfirm("profile avatar successfully changed!");
    }

    public void choosePhoto7(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(5);
        showConfirm("profile avatar successfully changed!");
    }

    public void choosePhoto8(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(3);
        showConfirm("profile avatar successfully changed!");
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Main.changeScene("profileMenu");
    }
    public void showConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.show();
    }
}

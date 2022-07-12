package game.Controller.menu;

import game.Controller.UserController;
import game.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
    private Text success;


    public void choosePhoto1(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(9);
        success.setText("Now you have a new Avatar!");
    }

    public void choosePhoto2(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(7);
        success.setText("Now you have a new Avatar!");
    }

    public void choosePhoto3(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(8);
        success.setText("Now you have a new Avatar!");
    }

    public void choosePhoto4(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(1);
        success.setText("Now you have a new Avatar!");
    }

    public void choosePhoto5(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(2);
        success.setText("Now you have a new Avatar!");
    }

    public void choosePhoto6(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(4);
        success.setText("Now you have a new Avatar!");
    }

    public void choosePhoto7(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(5);
        success.setText("Now you have a new Avatar!");
    }

    public void choosePhoto8(MouseEvent mouseEvent) {
        UserController.getCurrentUser().setAvatarNumber(3);
        success.setText("Now you have a new Avatar!");
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Main.changeScene("profileMenu");
    }
}

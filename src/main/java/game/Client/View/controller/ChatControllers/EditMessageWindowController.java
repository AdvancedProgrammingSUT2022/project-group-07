package game.Client.View.controller.ChatControllers;

import game.Server.Controller.Chat.Message;
import game.Server.Controller.Chat.MessageController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EditMessageWindowController implements Initializable {

    @FXML
    Label selectedLabel;
    @FXML
    Button confirmButton;
    @FXML
    Button cancelButton;
    @FXML
    TextField messageTextField;
    @FXML
    Button deleteButton;
    @FXML
    ChoiceBox<String> choiceBox;

    private Message selectedMessage ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedMessage = MessageController.getLastMessageToAttend();

        cancelButton.setOnMouseClicked(this::closeStage);

        confirmButton.setOnMouseClicked(mouseEvent -> {
            if (checkEditValidation())
                closeStage(mouseEvent);
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Message body can't be empty ! please consider deleting message !");
                alert.show();
            }
        });

        deleteButton.setOnMouseClicked(mouseEvent -> {
            MessageController.removeMessage(selectedMessage);
            closeStage(mouseEvent);
        });

        initializeChoiceBox();
    }

    public boolean checkEditValidation (){
        String text = messageTextField.getText() ;
        if (text==null || text.isEmpty() || text.isBlank())
            return false;
        if (choiceBox.getValue().equals("Only for me"))
            selectedMessage.setSenderVersionMessage(text);
        else
            selectedMessage.setMessage(text);
        return true;
    }

    public void closeStage (MouseEvent mouseEvent){
        Node source = (Node) mouseEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


    private void initializeChoiceBox() {
        String[] options = {"Only for me" , "For everyone"} ;
        choiceBox.getItems().addAll(options);
        // if the item of the list is changed
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            selectedLabel.setText(options[new_value.intValue()] + " selected");
        });
    }


}

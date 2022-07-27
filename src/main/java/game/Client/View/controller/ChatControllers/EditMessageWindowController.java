package game.Client.View.controller.ChatControllers;

import game.Client.ClientDataController;
import game.Client.Main;
import game.Common.Enum.Network.TypeOfRequest;
import game.Common.Enum.TypeOfResponse;
import game.Common.Model.Network.ClientRequest;
import game.Server.Controller.Chat.Message;
import game.Server.Controller.Chat.MessageController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
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

        selectedMessage = ClientDataController.lastMessageToAttend ;

        cancelButton.setOnMouseClicked(this::closeStage);

        confirmButton.setOnMouseClicked(mouseEvent -> {
            try {
                if (checkEditValidation())
                    closeStage(mouseEvent);
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Message body can't be empty ! please consider deleting message !");
                    alert.show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        deleteButton.setOnMouseClicked(mouseEvent -> {
            try {
                Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.REMOVE_MESSAGE , selectedMessage));
                if (Main.getClientHandler().getResponse().getTypeOfResponse().equals(TypeOfResponse.OK))
                    closeStage(mouseEvent);
            } catch (IOException e) {
                System.out.println("EditMessageWindowController.initialize");
                System.out.println(e.getMessage());
            }
        });

        initializeChoiceBox();
    }

    public boolean checkEditValidation () throws IOException {
        String text = messageTextField.getText() ;
        if (text==null || text.isEmpty() || text.isBlank())
            return false;
        if (choiceBox.getValue().equals("Only for me")) {
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.EDIT_MESSAGE_FOR_ONE_SIDE , selectedMessage , text));
        }
        else {
            Main.getClientHandler().sendRequest(new ClientRequest(TypeOfRequest.EDIT_MESSAGE_FOR_BOTH_SIDES , selectedMessage , text));
        }
        if (Main.getClientHandler().getResponse().getTypeOfResponse().equals(TypeOfResponse.OK))
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

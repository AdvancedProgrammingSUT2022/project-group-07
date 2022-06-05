package game.Controller.menu.fxmlControllers;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.TabPane.TabClosingPolicy.UNAVAILABLE;

public class chatRoomController implements Initializable {


    public Button sendButton;
    public TextArea textArea;
    public VBox messagesVBox;
    public TabPane tabPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Thread tabChecker = new Thread(new tabController(tabPane));
        tabChecker.start();

        tabPane.setTabClosingPolicy(UNAVAILABLE);

        Tab tab = new Tab();
        tab.setId("my-first-tab");
        tab.setText("first tab");
        tabPane.getTabs().add(tab);

        Tab tab2 = new Tab();
        tab2.setId("my-second-tab");
        tab2.setText("second tab");
        tabPane.getTabs().add(tab2);
    }


}

class tabController implements Runnable {

    TabPane tabPane ;
    Tab prevTab = null ;
    Tab currentTab ;

    public tabController (TabPane tabPane){
        this.tabPane = tabPane;
    }

    @Override
    public void run() {
        while (true){
            currentTab = tabPane.getSelectionModel().getSelectedItem();
            if (currentTab==null || currentTab.equals(prevTab))
                continue;
            else {
                prevTab = currentTab;
                System.out.println("current tab is : " + currentTab.getId() + " or : " + currentTab);
                addTabContent(currentTab);
            }

            if (currentTab.equals("exitTab"))
                return;

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void addTabContent (Tab currentTab){
        BorderPane borderPane = new BorderPane() ;

        VBox messageVBox = new VBox();

        HBox sendHBox = new HBox() ;
        TextArea messageTextArea = new TextArea();
        Button sendButton = new Button("Send");

        sendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.out.println(messageTextArea.getText());
                messageTextArea.clear();
            }
        });

        sendHBox.getChildren().add(messageTextArea);
        sendHBox.getChildren().add(sendButton);


        borderPane.setCenter(messageVBox);
        borderPane.setBottom(sendHBox);


        currentTab.setContent(borderPane);
    }

    public void handleMessages (){

    }

}
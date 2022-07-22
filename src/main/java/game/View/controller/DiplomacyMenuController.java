package game.View.controller;

import game.Controller.UserController;
import game.Controller.game.DiplomacyController;
import game.Controller.game.GameController;
import game.Controller.game.LogAndNotification.NotificationController;
import game.Enum.TypeOfDiplomacy;
import game.Enum.TypeOfRelation;
import game.Model.Civilization;
import game.Model.DiplomacyRequest;
import game.Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DiplomacyMenuController implements Initializable {

    public Civilization currentCivilization ;
    public Civilization receiverCivilization ;

    public TabPane tabPane;
    public AnchorPane diplomacyAnchorPane;
    public AnchorPane diplomacyRequestsHistoryAnchorPane ;
    public AnchorPane tradeAnchorPane;
    public AnchorPane demandAnchorPane;

    // diplomacy pane stuff
    public ChoiceBox<String > diplomacyChooseCivilizationChoiceBox;
    public Button offerPeaceButton ;
    public Button declareWarButton ;
    public Button breakPeaceButton ;
    public Label currentStatusLabel;


    // diplomacy requests history page stuff
    public VBox outgoingDiplomacyRequestsVBox;
    public VBox incomingDiplomacyRequestsVBox;


    // for controller itself
    private String selectedPage = "Diplomacy Page" ;
    private String lastPage = "null" ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        init();
        currentCivilization = GameController.getInstance().getCurrentCivilization();
        for (Tab tab : tabPane.getTabs())
            tab.setOnSelectionChanged(event -> selectedPage = tabPane.getSelectionModel().getSelectedItem().getText());

        Thread updateScreen = new Thread(() -> {
            Runnable runnable = () -> {
                if (!lastPage.equals(selectedPage)) {
                    receiverCivilization = null ;
                    updateScreen();
                    lastPage = selectedPage ;
                }
            } ;
            while (true) {
                Platform.runLater(runnable);
                try {Thread.sleep(400);}
                catch (InterruptedException ignored) {}
            }
        });
        updateScreen.setDaemon(true);
        updateScreen.start();
    }

    // main methods of page
    public void updateScreen (){
        switch (selectedPage){
            case "Diplomacy Page" -> loadDiplomacyPage();
            case "Trade Page" -> loadTradePage() ;
            case "DemandPage" -> loadDemandPage();
            case "Diplomacy Requests History" -> loadDiplomacyRequestsHistory() ;
        }
    }

    public void init (){
        UserController.setCurrentUser(new User());
        ArrayList<User> players = new ArrayList<>();
        players.add(UserController.getCurrentUser());
        players.add(new User());
        players.add(new User());

        GameController gameController = GameController.getInstance();
        gameController.setPlayers(players);
        gameController.initialize();
        NotificationController.runNotification(gameController);
        currentCivilization = gameController.getCurrentCivilization();

        for (Civilization civilization1 : gameController.getCivilizations()) {
            for (Civilization civilization2 : gameController.getCivilizations()) {
                if (civilization1.equals(civilization2))
                    continue;
                civilization1.addRelationWithCivilization(TypeOfRelation.NEUTRAL , civilization2);
            }
        }
    }

    public void nextTurn() {
        GameController.getInstance().nextTurn(GameController.getInstance());
        currentCivilization = GameController.getInstance().getCurrentCivilization();
        loadDiplomacyChoiceBox(GameController.getInstance());
    }

    public void showNullCivilizationAlert (){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Please choose a civilization to deal with !");
        alert.show();
    }

    public Civilization getReceiverCivilization (String name){
        for (Civilization civilization : GameController.getInstance().getCivilizations()) {
            if (civilization.getName().equals(name))
                return civilization;
        }
        return null;
    }

    // diplomacy page methods
    public void loadDiplomacyPage (){
        loadDiplomacyChoiceBox(GameController.getInstance());
        loadDiplomacyButtonActions() ;
        if (receiverCivilization == null)
            return;
        currentStatusLabel.setText(currentCivilization.getRelationWithCivilization(receiverCivilization).toString());
        switch (currentCivilization.getRelationWithCivilization(receiverCivilization)){
            case ENEMY -> {
                declareWarButton.setDisable(true);
                offerPeaceButton.setDisable(false);
                breakPeaceButton.setDisable(true);
            }
            case ALLY -> {
                declareWarButton.setDisable(false);
                offerPeaceButton.setDisable(true);
                breakPeaceButton.setDisable(false);
            }
            case NEUTRAL -> {
                declareWarButton.setDisable(false) ;
                offerPeaceButton.setDisable(false) ;
                breakPeaceButton.setDisable(true) ;
            }
        }
    }

    public void loadDiplomacyButtonActions (){
        declareWarButton.setOnMouseClicked(mouseEvent -> {
            if (receiverCivilization == null)
                showNullCivilizationAlert();
            else {
                DiplomacyRequest diplomacyRequest = new DiplomacyRequest(currentCivilization , receiverCivilization , TypeOfDiplomacy.DECLARE_WAR);
                currentCivilization.addRelationWithCivilization(TypeOfRelation.ENEMY , receiverCivilization);
                diplomacyRequest.setAcceptedByReceiver();
                DiplomacyController.addDiplomacyRequest(diplomacyRequest);
                loadDiplomacyPage();
            }
        });

        offerPeaceButton.setOnMouseClicked(mouseEvent -> {
            if (receiverCivilization == null)
                showNullCivilizationAlert();
            else {
                DiplomacyRequest diplomacyRequest = new DiplomacyRequest(currentCivilization , receiverCivilization , TypeOfDiplomacy.PEACE);
                DiplomacyController.addDiplomacyRequest(diplomacyRequest);
            }
        });

        breakPeaceButton.setOnMouseClicked(mouseEvent -> {
            if (receiverCivilization == null)
                showNullCivilizationAlert();
            else {
                DiplomacyRequest diplomacyRequest = new DiplomacyRequest(currentCivilization , receiverCivilization , TypeOfDiplomacy.BREAK_PEACE);
                currentCivilization.addRelationWithCivilization(TypeOfRelation.NEUTRAL , receiverCivilization);
                diplomacyRequest.setAcceptedByReceiver();
                DiplomacyController.addDiplomacyRequest(diplomacyRequest);
                loadDiplomacyPage();
            }
        });

    }

    public void loadDiplomacyChoiceBox (GameController gameController){
        ArrayList<String > names = new ArrayList<>();
        for (Civilization civilization : gameController.getCivilizations()) {
            if (gameController.getCurrentCivilization().equals(civilization)) continue;
            names.add(civilization.getName());
        }
        diplomacyChooseCivilizationChoiceBox.setItems(FXCollections.observableArrayList(names));
        diplomacyChooseCivilizationChoiceBox.setOnAction(actionEvent -> {
            receiverCivilization = getReceiverCivilization(diplomacyChooseCivilizationChoiceBox.getSelectionModel().getSelectedItem());
            updateScreen();
        });
    }

    // trade page methods
    private void loadTradePage() {
    }
    

    // demand page methods
    private void loadDemandPage() {
    }

    // diplomacy requests history page methods
    private void loadDiplomacyRequestsHistory() {
        loadOutgoingDiplomacyRequestsHistory() ;
        loadIncomingDiplomacyRequestsHistory() ;
    }

    private Button getCancelButton (DiplomacyRequest diplomacyRequest){
        Button cancelButton = new Button("Cancel") ;
        cancelButton.getStyleClass().add("cancelRequestButton") ;
        cancelButton.setOnMouseClicked(mouseEvent -> {
            DiplomacyController.removeDiplomacy(diplomacyRequest) ;
            updateScreen();
        });
        return cancelButton ;
    }

    private void loadOutgoingDiplomacyRequestsHistory() {
        outgoingDiplomacyRequestsVBox.getChildren().clear();
        for (DiplomacyRequest diplomacyRequest : DiplomacyController.getDiplomacyRequestsOfCivilization(currentCivilization))
            outgoingDiplomacyRequestsVBox.getChildren().add(getOutgoingVBox(diplomacyRequest));
    }

    private VBox getOutgoingVBox(DiplomacyRequest diplomacyRequest){
        VBox vBox = new VBox();
        vBox.getStyleClass().add("requestVBox") ;
        vBox.setAlignment(Pos.CENTER);

        String status ;

        if (diplomacyRequest.isStillPending()) status = "Still Pending" ;
        else if (diplomacyRequest.isRejectedByReceiver()) status = "Rejected" ;
        else status = "Accepted" ;

        vBox.getChildren().add(new Label(diplomacyRequest.getTypeOfDiplomacy().toString())) ;
        vBox.getChildren().add(new Label("For " + diplomacyRequest.getReceiver().getName())) ;
        vBox.getChildren().add(new Label(status)) ;
        switch (status){
            case "Still Pending" -> vBox.setStyle("-fx-background-color: gray;");
            case "Accepted" -> vBox.setStyle("-fx-background-color: lightgreen;");
            case "Rejected" -> vBox.setStyle("-fx-background-color: red;");
        }

        if (status.equals("Still Pending"))
            vBox.getChildren().add(getCancelButton(diplomacyRequest));

        return vBox ;
    }

    private void loadIncomingDiplomacyRequestsHistory() {
        incomingDiplomacyRequestsVBox.getChildren().clear();
        for (DiplomacyRequest diplomacyRequest : DiplomacyController.getDiplomacyRequestsForCivilization(currentCivilization)) {
            incomingDiplomacyRequestsVBox.getChildren().add(getIncomingVBox(diplomacyRequest));
        }
    }

    private VBox getIncomingVBox(DiplomacyRequest diplomacyRequest) {
        VBox vBox = new VBox();
        vBox.getStyleClass().add("requestVBox") ;
        vBox.setAlignment(Pos.CENTER);
        String status ;

        if (diplomacyRequest.isStillPending()) status = "Still Pending" ;
        else if (diplomacyRequest.isRejectedByReceiver()) status = "Rejected" ;
        else status = "Accepted" ;

        vBox.getChildren().add(new Label(diplomacyRequest.getTypeOfDiplomacy().toString()));
        vBox.getChildren().add(new Label("From " + diplomacyRequest.getReceiver().getName())) ;
        switch (status){
            case "Still Pending" -> vBox.setStyle("-fx-background-color: gray;");
            case "Accepted" -> vBox.setStyle("-fx-background-color: lightgreen;");
            case "Rejected" -> vBox.setStyle("-fx-background-color: red;");
        }

        if (status.equals("Still Pending"))
            vBox.getChildren().add(new HBox(getAcceptButton(diplomacyRequest) , getRejectButton(diplomacyRequest)));

        return vBox;
    }

    private Button getAcceptButton (DiplomacyRequest diplomacyRequest){
        Button acceptButton = new Button("Accept") ;
        acceptButton.getStyleClass().add("acceptRequestButton") ;
        acceptButton.setOnMouseClicked(mouseEvent -> {
            diplomacyRequest.setAcceptedByReceiver();
            updateScreen();
        });
        return acceptButton ;
    }

    private Button getRejectButton (DiplomacyRequest diplomacyRequest){
        Button rejectButton = new Button("Reject") ;
        rejectButton.getStyleClass().add("rejectRequestButton") ;
        rejectButton.setOnMouseClicked(mouseEvent -> {
            diplomacyRequest.setRejectedByReceiver();
            updateScreen();
        });
        return rejectButton ;
    }

}

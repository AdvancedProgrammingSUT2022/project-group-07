package game.View.controller;

import game.Controller.UserController;
import game.Controller.game.DiplomacyController;
import game.Controller.game.GameController;
import game.Controller.game.LogAndNotification.NotificationController;
import game.Enum.Resources;
import game.Enum.TypeOfDiplomacy;
import game.Enum.TypeOfRelation;
import game.Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    public Label currentStatusLabel = new Label();
    public HBox currentStatusHBox;
    public Label receiverCivilizationLabel;

    // diplomacy requests history page stuff
    public VBox outgoingDiplomacyRequestsVBox;
    public VBox incomingDiplomacyRequestsVBox;

    // trade page stuff
    public ChoiceBox<String> diplomacyChooseCivilizationChoiceBoxForTrade;
    public GridPane whatYouWillGetGridPane;
    public GridPane whatTheyWillGetGridPane;
    public ImageView whatYouWillGetImageView;
    public ImageView whatTheyWillGetImageView;
    public Label receiverCivilizationLabelInTradePage;

    private TradingAsset whatYouWillGetTradingAsset ;
    private TradingAsset whatTheyWillGetTradingAsset ;
    int rowCount = 5 ;
    int colCount = 4 ;

    boolean exit = false ;

    // for controller itself
    private String selectedPage = "Diplomacy Page" ;
    private String lastPage = "null" ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        init();
        currentCivilization = GameController.getInstance().getCurrentCivilization();

        for (Tab tab : tabPane.getTabs()) {
            tab.setOnSelectionChanged(event -> selectedPage = tabPane.getSelectionModel().getSelectedItem().getText());
            tab.getStyleClass().add("tab") ;
        }

        Thread updateCurrent = new Thread(() -> {
            Runnable runnable = () -> {
                currentCivilization = GameController.getInstance().getCurrentCivilization();
            } ;
            while (!exit) {
                Platform.runLater(runnable);
                try {Thread.sleep(300);}
                catch (InterruptedException ignored) {}
            }
        });
        updateCurrent.setDaemon(true);
        updateCurrent.start();

        Thread updateScreen = new Thread(() -> {
            Runnable runnable = () -> {
                if (!lastPage.equals(selectedPage)) {
                    receiverCivilization = null ;
                    updateScreen();
                    lastPage = selectedPage ;
                }
            } ;
            while (!exit) {
                Platform.runLater(runnable);
                try {Thread.sleep(500);}
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
            case "Diplomacy Requests History" -> loadDiplomacyRequestsHistory() ;
        }
    }

    // a function to test menu
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
        loadDiplomacyChoiceBox(GameController.getInstance() , diplomacyChooseCivilizationChoiceBox);
        loadDiplomacyButtonActions() ;
        if (receiverCivilization == null)
            return;

        currentStatusHBox.getChildren().clear();
        currentStatusHBox.setAlignment(Pos.CENTER);
        currentStatusHBox.getChildren().add(getStatusImageView(currentCivilization.getRelationWithCivilization(receiverCivilization)));
        currentStatusHBox.getChildren().add(new Label(currentCivilization.getRelationWithCivilization(receiverCivilization).toString()));

        switch (currentCivilization.getRelationWithCivilization(receiverCivilization)){
            case ENEMY -> {
                declareWarButton.setDisable(true);
                offerPeaceButton.setDisable(false);
                breakPeaceButton.setDisable(true);
                currentStatusHBox.setStyle("-fx-background-color: red");
            }
            case ALLY -> {
                declareWarButton.setDisable(false);
                offerPeaceButton.setDisable(true);
                breakPeaceButton.setDisable(false);
                currentStatusHBox.setStyle("-fx-background-color: lightgreen");
            }
            case NEUTRAL -> {
                declareWarButton.setDisable(false) ;
                offerPeaceButton.setDisable(false) ;
                breakPeaceButton.setDisable(true) ;
                currentStatusHBox.setStyle("-fx-background-color: white");
            }
        }
    }

    public ImageView getStatusImageView (TypeOfRelation typeOfRelation){
        ImageView imageView = new ImageView(new Image(getClass().getResource("/game/images/status/"+typeOfRelation+".png").toExternalForm()));
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        return imageView ;
    }

    public void loadDiplomacyButtonActions (){
        declareWarButton.setOnMouseClicked(mouseEvent -> {
            if (receiverCivilization == null)
                showNullCivilizationAlert();
            else {
                DiplomacyRequest diplomacyRequest = new DiplomacyRequest(currentCivilization , receiverCivilization , TypeOfDiplomacy.DECLARE_WAR);
                currentCivilization.addRelationWithCivilization(TypeOfRelation.ENEMY , receiverCivilization);
                diplomacyRequest.setAcceptedByReceiver();
                DiplomacyController.addDiplomacy(diplomacyRequest);
                loadDiplomacyPage();
            }
        });

        offerPeaceButton.setOnMouseClicked(mouseEvent -> {
            if (receiverCivilization == null)
                showNullCivilizationAlert();
            else {
                DiplomacyRequest diplomacyRequest = new DiplomacyRequest(currentCivilization , receiverCivilization , TypeOfDiplomacy.PEACE);
                DiplomacyController.addDiplomacy(diplomacyRequest);
            }
        });

        breakPeaceButton.setOnMouseClicked(mouseEvent -> {
            if (receiverCivilization == null)
                showNullCivilizationAlert();
            else {
                DiplomacyRequest diplomacyRequest = new DiplomacyRequest(currentCivilization , receiverCivilization , TypeOfDiplomacy.BREAK_PEACE);
                currentCivilization.addRelationWithCivilization(TypeOfRelation.NEUTRAL , receiverCivilization);
                diplomacyRequest.setAcceptedByReceiver();
                DiplomacyController.addDiplomacy(diplomacyRequest);
                loadDiplomacyPage();
            }
        });

    }

    public void loadDiplomacyChoiceBox (GameController gameController , ChoiceBox<String> choiceBox){
        ArrayList<String > names = new ArrayList<>();
        for (Civilization civilization : gameController.getCivilizations()) {
            if (gameController.getCurrentCivilization().equals(civilization)) continue;
            names.add(civilization.getName());
        }
        choiceBox.setItems(FXCollections.observableArrayList(names));
        choiceBox.setOnAction(actionEvent -> {
            receiverCivilization = getReceiverCivilization(choiceBox.getSelectionModel().getSelectedItem());
            receiverCivilizationLabel.setText(receiverCivilization.getName());
            receiverCivilizationLabelInTradePage.setText(receiverCivilization.getName());
            updateScreen();
        });
    }

    // trade page methods
    private void loadTradePage() {
        loadDiplomacyChoiceBox(GameController.getInstance() , diplomacyChooseCivilizationChoiceBoxForTrade);
        whatYouWillGetGridPane.getChildren().clear();
        whatTheyWillGetGridPane.getChildren().clear();
        whatYouWillGetImageView.setImage(null);
        whatTheyWillGetImageView.setImage(null);
        whatYouWillGetTradingAsset = null ;
        whatTheyWillGetTradingAsset = null ;

        loadGridPaneFor(false);
        loadGridPaneFor(true);
    }

    private void loadGridPaneFor(boolean isThisYou){
        if (currentCivilization == null || receiverCivilization == null) return;

        if (isThisYou)
            fillGridPaneForCivilization(whatTheyWillGetGridPane , currentCivilization);
        else
            fillGridPaneForCivilization(whatYouWillGetGridPane , receiverCivilization);

    }

    private void fillGridPaneForCivilization (GridPane gridPane , Civilization civilization){
        gridPane.getChildren().clear();
        ArrayList<Resources> resources = civilization.getAllAvailableResources();
//        ArrayList<Resources> resources = new ArrayList<>(List.of(Resources.values()));
        addCoinValueToTrade(gridPane , civilization);
        int counter = 0 ;

        for (int y=1 ; y<rowCount ; y++){
            for (int x=0 ; x<colCount ; x++){
                if (counter >= resources.size()) continue;
                Resources resource = resources.get(counter);
                counter++ ;
                ImageView resourceImageView = new ImageView(new Image(getClass().getResource("/game/assets/civAsset/resources/"+resource.getName()+".png").toExternalForm()));
                resourceImageView.setFitHeight(30);
                resourceImageView.setFitWidth(30);
                Tooltip.install(resourceImageView , new Tooltip(resource.getTypeOfResource() + " Resource \n" + resource.getName().toUpperCase()));
                if ( civilization.equals(currentCivilization)){
                    resourceImageView.setOnMouseClicked(mouseEvent -> {
                        whatTheyWillGetTradingAsset = new TradingAsset(resource);
                        whatTheyWillGetImageView.setImage(resourceImageView.getImage());
                    });
                } else {
                    resourceImageView.setOnMouseClicked(mouseEvent -> {
                        whatYouWillGetTradingAsset = new TradingAsset(resource);
                        whatYouWillGetImageView.setImage(resourceImageView.getImage());
                    });
                }

                gridPane.add(resourceImageView , x , y);
            }
        }
    }

    public void addCoinValueToTrade (GridPane gridPane , Civilization civilization){
        ImageView gold5 = new ImageView(new Image(getClass().getResource("/game/images/icons/GOLD_ICON.png").toExternalForm())) ;
        ImageView gold10 = new ImageView(new Image(getClass().getResource("/game/images/icons/GOLD_ICON.png").toExternalForm())) ;
        ImageView gold50 = new ImageView(new Image(getClass().getResource("/game/images/icons/GOLD_ICON.png").toExternalForm())) ;
        gold5.setFitWidth(30);
        gold5.setFitHeight(30);
        gold10.setFitWidth(30);
        gold10.setFitHeight(30);
        gold50.setFitWidth(30);
        gold50.setFitHeight(30);
        gridPane.add(gold5 , 0 , 0);
        gridPane.add(gold10 , 1 , 0);
        gridPane.add(gold50 , 2 , 0);

        Tooltip.install(gold5 , new Tooltip("5 Gold"));
        Tooltip.install(gold10 , new Tooltip("10 Gold"));
        Tooltip.install(gold50 , new Tooltip("50 Gold"));

        if (civilization.equals(currentCivilization)){
            gold5.setOnMouseClicked(mouseEvent -> {
                whatTheyWillGetTradingAsset = new TradingAsset(5);
                whatTheyWillGetImageView.setImage(gold5.getImage());
            });
            gold10.setOnMouseClicked(mouseEvent -> {
                whatTheyWillGetTradingAsset = new TradingAsset(10);
                whatTheyWillGetImageView.setImage(gold10.getImage());
            });
            gold50.setOnMouseClicked(mouseEvent -> {
                whatTheyWillGetTradingAsset = new TradingAsset(50);
                whatTheyWillGetImageView.setImage(gold50.getImage());
            });
        } else {
            gold5.setOnMouseClicked(mouseEvent -> {
                whatYouWillGetTradingAsset = new TradingAsset(5);
                whatYouWillGetImageView.setImage(gold5.getImage());
            });
            gold10.setOnMouseClicked(mouseEvent -> {
                whatYouWillGetTradingAsset = new TradingAsset(10);
                whatYouWillGetImageView.setImage(gold10.getImage());
            });
            gold50.setOnMouseClicked(mouseEvent -> {
                whatYouWillGetTradingAsset = new TradingAsset(50);
                whatYouWillGetImageView.setImage(gold50.getImage());
            });
        }

        gold5.setDisable(false);
        gold10.setDisable(false);
        gold50.setDisable(false);
        if (civilization.getGold()<50) gold50.setDisable(true);
        if (civilization.getGold()<10) gold10.setDisable(true);
        if (civilization.getGold()<5) gold5.setDisable(true);
    }

    public void sendOffer() {
        if (whatYouWillGetTradingAsset == null && whatTheyWillGetTradingAsset==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("It is neither a deal nor a demand ! What do you want SON ?!");
            alert.show();
            return;
        }
        if (whatYouWillGetTradingAsset == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("You can't gift anything to others unless they demand it ! THIS IS REAL LIFE SON ! WAKE UP !");
            alert.show();
            return;
        }

        if (whatTheyWillGetTradingAsset == null ){
            DiplomacyRequest diplomacyRequest = new DiplomacyRequest(currentCivilization , receiverCivilization , TypeOfDiplomacy.DEMAND);
            diplomacyRequest.setTrade(
                    new Trade(currentCivilization , whatYouWillGetTradingAsset
                            , receiverCivilization , null));
            DiplomacyController.addDiplomacy(diplomacyRequest);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Your demand was sent to " + receiverCivilization.getName());
            alert.show();
            return;
        }

        DiplomacyRequest diplomacyRequest = new DiplomacyRequest(currentCivilization , receiverCivilization , TypeOfDiplomacy.TRADE);
        diplomacyRequest.setTrade(
                new Trade(currentCivilization , whatYouWillGetTradingAsset
                        , receiverCivilization , whatTheyWillGetTradingAsset));
        DiplomacyController.addDiplomacy(diplomacyRequest);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Your trade offer was sent to " + receiverCivilization.getName());
        alert.show();

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
            case "Still Pending" -> vBox.setStyle("-fx-background-color: lightgray;");
            case "Accepted" -> vBox.setStyle("-fx-background-color: lightgreen;");
            case "Rejected" -> vBox.setStyle("-fx-background-color: rgba(255,0,0,0.75);");
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
            case "Still Pending" -> vBox.setStyle("-fx-background-color: lightgray;");
            case "Accepted" -> vBox.setStyle("-fx-background-color: lightgreen;");
            case "Rejected" -> vBox.setStyle("-fx-background-color: rgba(255,0,0,0.75);");
        }
        if (diplomacyRequest.getTypeOfDiplomacy().equals(TypeOfDiplomacy.DEMAND)){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            ImageView senderTradingAssetImageView = getTradingAssetImageView(diplomacyRequest.getTrade().getSenderTradingAsset());
            hBox.getChildren().addAll(senderTradingAssetImageView);
            vBox.getChildren().add(hBox);
        }
        else if (diplomacyRequest.getTypeOfDiplomacy().equals(TypeOfDiplomacy.TRADE)){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            ImageView senderTradingAssetImageView = getTradingAssetImageView(diplomacyRequest.getTrade().getSenderTradingAsset());
            Label label = new Label("\tfor\t");
            ImageView receiverTradingAssetImageView = getTradingAssetImageView(diplomacyRequest.getTrade().getReceiverTradingAsset()) ;
            hBox.getChildren().addAll(senderTradingAssetImageView , label , receiverTradingAssetImageView);
            vBox.getChildren().add(hBox);
        }
        if (status.equals("Still Pending")) {
            HBox hBox = new HBox(getAcceptButton(diplomacyRequest), getRejectButton(diplomacyRequest));
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
        }
        return vBox;
    }

    private ImageView getTradingAssetImageView (TradingAsset tradingAsset){
        ImageView imageView = new ImageView();
        switch (tradingAsset.getTypeOfTrade()){
            case RESOURCE -> imageView.setImage(new Image(getClass().getResource("/game/assets/civAsset/resources/"+tradingAsset.getResource().toString()+".png").toExternalForm()));
            case GOLD -> imageView.setImage(new Image(getClass().getResource("/game/images/icons/GOLD_ICON").toExternalForm()));
        }
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        return imageView ;
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

    public void back(MouseEvent mouseEvent) {
        exit = true ;
        Node source = (Node)  mouseEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}

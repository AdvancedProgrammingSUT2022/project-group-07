package game.Client.View.controller;

import game.Server.Controller.game.GameController;
import game.Server.Controller.game.LogAndNotification.NotificationController;
import game.Common.Enum.TypeOfTechnology;
import game.Client.Main;
import game.Common.Model.Civilization;
import game.Common.Model.Technology;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class TechnologyTreeController implements Initializable {

    public AnchorPane anchorPane;
    public Button backButton ;

    ArrayList<TypeOfTechnology> reachedTypeOfTechnology = new ArrayList<>();
    ArrayList<Line> lines = new ArrayList<>() ;

    ArrayList<String > allPaths = new ArrayList<>() ;

    ArrayList<HBox> hBoxes = new ArrayList<>() ;

    int totalRows = 0 ;

    int numberOfIntervals = 0 ;
    HashMap<Integer , Integer> intervals = new HashMap<>() ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        anchorPane.getStyleClass().add("anchorPane");

        for (TypeOfTechnology value : TypeOfTechnology.values())
            doTheJobDeeply(value , "");
        correctPaths();

        int numberBefore = 0 ;
        for (String allPath : allPaths) {
            drawOnePath(allPath, -100);
            setIntervalsOfYS();
            if (numberBefore != numberOfIntervals)
                totalRows += 1;
            numberBefore = numberOfIntervals ;
        }

        anchorPane.getChildren().addAll(lines);
        anchorPane.getChildren().addAll(hBoxes);
        handleBoxes();
    }


    public String  doTheJobDeeply (TypeOfTechnology typeOfTechnology , String path){
        reachedTypeOfTechnology.add(typeOfTechnology);
        path += "|" + typeOfTechnology.getName() ;
        ArrayList<TypeOfTechnology> allTechnologiesRelated = getAllTechnologiesRelated(typeOfTechnology);
        if (allTechnologiesRelated.size() == 0)
            return path;
        for (TypeOfTechnology ofTechnology : allTechnologiesRelated) {
            String newPath = doTheJobDeeply(ofTechnology, path);
            allPaths.add(newPath);
        }
        return path ;
    }

    public ArrayList<TypeOfTechnology> getAllTechnologiesRelated (TypeOfTechnology typeOfTechnology){
        ArrayList<TypeOfTechnology> output = new ArrayList<>();
        for (TypeOfTechnology value : TypeOfTechnology.values()) {
            if (Arrays.asList(value.getPrerequisiteTech()).contains(typeOfTechnology))
                output.add(value);
        }
        return output ;
    }

    public void correctPaths (){
        ArrayList<String> toRemove = new ArrayList<>();
        for (String path1 : allPaths) {
            for (String path2 : allPaths) {
                if (path1.contains(path2) && !path1.equals(path2))
                    toRemove.add(path2);
            }
        }
        allPaths.removeAll(toRemove) ;
    }

    public void drawOnePath (String path , int x){
        ArrayList<String> components = new ArrayList<>(Arrays.asList(path.split("\\|")));
        for (int i = 0; i < components.size(); i++) {
            HBox availableHBox = getAvailableHBoxByTechnologyName(components.get(i)) ;
            if ( availableHBox == null ) {
                Label label = new Label(components.get(i));
                HBox hBoxToAdd = getHBox(label , x , totalRows*42 + 5) ;
                hBoxes.add(hBoxToAdd);
                if (i != 0 ){
                    HBox last = getAvailableHBoxByTechnologyName(components.get(i-1));
                    Line line = new Line(last.getLayoutX()+40 , last.getLayoutY()+20 , hBoxToAdd.getLayoutX() , hBoxToAdd.getLayoutY()+20);
                    line.setStrokeWidth(10);
                    line.setFill(Color.WHITE);
                    line.setSmooth(true);
                    lines.add(line);
                }
            }
            x += 100;
        }
    }

    public ImageView getImageView (String name){
        ImageView output = new ImageView();
        Image image ;
        try {
            image = new Image(Main.class.getResource("/game/images/technologies/"+name+".png").toExternalForm());
        } catch (Exception e){
            image = new Image(Main.class.getResource("/game/images/technologies/Agriculture.png").toExternalForm()) ;
        }
        output.setImage(image);
        output.setFitWidth(38);
        output.setFitHeight(38);
        return output ;
    }

    public HBox getHBox (Label label , int x , int y){
        HBox hBox = new HBox();
        hBox.getStyleClass().add("technologyHBox") ;
        label.setTooltip(new Tooltip(getTechnologyByName(label.getText()).getScienceNeeded()+""));
        hBox.setPrefHeight(35);
        hBox.setPrefWidth(90);
        hBox.setLayoutX(x);
        hBox.setLayoutY(y);
        hBox.getChildren().add(getImageView(label.getText()));
        hBox.getChildren().add(label);
        return hBox ;
    }

    public HBox getAvailableHBoxByTechnologyName (String name){
        for (HBox hBox : hBoxes) {
            Label label = (Label) hBox.getChildren().get(1) ;
            if (label.getText().equals(name))
                return hBox;
        }
        return null;
    }

    public TypeOfTechnology getTechnologyByName (String name){
        for (TypeOfTechnology value : TypeOfTechnology.values()) {
            if (value.getName().equals(name))
                return value;
        }
        return TypeOfTechnology.values()[0];
    }

    public void movePage(KeyEvent keyEvent) {
        switch (keyEvent.getCode()){
            case A , LEFT -> anchorPane.setTranslateX(anchorPane.getTranslateX() + 10);
            case D , RIGHT-> anchorPane.setTranslateX(anchorPane.getTranslateX() -10) ;
            case W , UP -> anchorPane.setTranslateY(anchorPane.getTranslateY() +10) ;
            case S , DOWN -> anchorPane.setTranslateY(anchorPane.getTranslateY() -10) ;
        }
    }

    public void movePageWithMouse(MouseEvent mouseEvent) {
        anchorPane.setTranslateX(mouseEvent.getX()-400);
        anchorPane.setTranslateY(mouseEvent.getY()-300);
    }

    public void handleBoxes (){
        for (HBox hBox : hBoxes) {
            String techName = ( (Label) hBox.getChildren().get(1)).getText() ;
            TypeOfTechnology typeOfTechnology = getTechnologyByName(techName) ;
            Civilization currentCivilization = GameController.getInstance().getCurrentCivilization();

            if (currentCivilization.getGainedTypeOfTechnologies().contains(typeOfTechnology)){
                hBox.getStyleClass().add("greenBox") ;
                hBox.setOnMouseClicked(mouseEvent -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("You already own this technology !");
                    alert.show();
                });
            }
            else if (currentCivilization.getCurrentResearch() != null
                    && currentCivilization.getCurrentResearch().getTypeOfTechnology().equals(typeOfTechnology)){
                hBox.getStyleClass().add("blueBox") ;
                hBox.setOnMouseClicked(mouseEvent -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("You're already researching this technology !");
                    alert.show();
                });
            }
            else {
                hBox.getStyleClass().add("grayBox") ;
                hBox.setOnMouseClicked(mouseEvent -> {
                    handleClick(typeOfTechnology);
                });
            }
        }
    }

    public void handleClick(TypeOfTechnology typeOfTechnology){
        Civilization currentCivilization = GameController.getInstance().getCurrentCivilization();
        if ( typeOfTechnology.getPrerequisiteTech() == null
                || currentCivilization.getGainedTypeOfTechnologies().containsAll(List.of(typeOfTechnology.getPrerequisiteTech()))){
            if (currentCivilization.getScience()==0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You don't have any science");
                alert.show();
            }
            else {
                Technology technology = new Technology(typeOfTechnology);
                technology.setRemainingTurns(typeOfTechnology.getCost()/currentCivilization.getScience());
                currentCivilization.setCurrentResearch(technology);
                NotificationController.logResearchStarted(typeOfTechnology , currentCivilization);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("research " + typeOfTechnology.getName() + " set successfully");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Your don't have all prerequisite technologies");
            alert.show();
        }
    }

    public void movePageWithMouseClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)){
            System.out.println("move");
            anchorPane.setTranslateX(validX((int) (mouseEvent.getX()-200)));
            anchorPane.setTranslateY(validY((int) (mouseEvent.getY()-300)));
        }
    }

    public int validX (int x){
        if (x<= -60)
            return -60 ;
        return Math.min(x, 1260);
    }

    public int validY (int y){
        if (y<= -40)
            return -40 ;
        return Math.min(y, 840);
    }

    public void exitPage(MouseEvent mouseEvent) {
        Node  source = (Node)  mouseEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setIntervalsOfYS (){
        intervals.clear();

        for (HBox hBox : hBoxes)
            intervals.put((int) (hBox.getLayoutY()/35), 1) ;

        numberOfIntervals = intervals.keySet().size();
    }

}


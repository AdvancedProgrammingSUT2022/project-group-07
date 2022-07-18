package game.View.controller;

import game.Controller.game.GameController;
import game.Controller.game.LogAndNotification.NotificationController;
import game.Enum.TypeOfTechnology;
import game.Main;
import game.Model.Civilization;
import game.Model.Technology;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class TechnologyTreeController implements Initializable {

    public AnchorPane anchorPane;

    ArrayList<TypeOfTechnology> reachedTypeOfTechnology = new ArrayList<>();
    ArrayList<Label> labels = new ArrayList<>();
    ArrayList<Line> lines = new ArrayList<>() ;

    ArrayList<String > allPaths = new ArrayList<>() ;

    ArrayList<HBox> hBoxes = new ArrayList<>() ;

    int totalRows = 0 ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (TypeOfTechnology value : TypeOfTechnology.values())
            doTheJobDeeply(value , "");
        correctPaths();

        for (String allPath : allPaths) {
            drawOnePath(allPath, 10);
            totalRows += 1 ;
        }

        anchorPane.getChildren().addAll(lines);
        anchorPane.getChildren().addAll(hBoxes);
        anchorPane.requestFocus();
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
            Label availableLabel = getAvailableLabelByTechnologyName(components.get(i)) ;
            if ( availableLabel == null ) {
                Label label = new Label(components.get(i));
                hBoxes.add(getHBox(label , x , totalRows*15 + 10));
                labels.add(label);
                if (i != 0 ){
                    Label last = getAvailableLabelByTechnologyName(components.get(i-1));
                    Line line = new Line(last.getLayoutX() , last.getLayoutY() , label.getLayoutX() , label.getLayoutY());
                    lines.add(line);
                }
            }
            x += 300;
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
        output.setFitWidth(50);
        output.setFitHeight(50);
        return output ;
    }

    public HBox getHBox (Label label , int x , int y){
        HBox hBox = new HBox();
        hBox.getStyleClass().add("technologyHBox") ;
        label.setTooltip(new Tooltip(getTechnologyByName(label.getText()).getScienceNeeded()+""));
        hBox.setPrefHeight(50);
        hBox.setPrefWidth(200);
        hBox.setLayoutX(x);
        hBox.setLayoutY(y);
        hBox.getChildren().add(getImageView(label.getText()));
        hBox.getChildren().add(label);
        return hBox ;
    }

    public Label getAvailableLabelByTechnologyName (String name){
        for (Label label : labels) {
            if (label.getText().equals(name))
                return label;
        }
        return null ;
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
            case A -> anchorPane.setTranslateX(anchorPane.getTranslateX() +10);
            case D -> anchorPane.setTranslateX(anchorPane.getTranslateX() -10) ;
            case W -> anchorPane.setTranslateY(anchorPane.getTranslateY() +10) ;
            case S -> anchorPane.setTranslateY(anchorPane.getTranslateY() -10) ;
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

}


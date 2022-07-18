package game.View.controller;

import game.Enum.TypeOfTechnology;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class TechnologyTreeController implements Initializable {

    public AnchorPane anchorPane;

    ArrayList<TypeOfTechnology> reachedTypeOfTechnology = new ArrayList<>();
    ArrayList<Label> labels = new ArrayList<>();
    ArrayList<Line> lines = new ArrayList<>() ;

    ArrayList<String > allPaths = new ArrayList<>() ;

    int totalRows = 0 ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (TypeOfTechnology value : TypeOfTechnology.values())
            doTheJobDeeply(value , "");

        correctPaths();

        for (String allPath : allPaths)
            drawOnePath(allPath, 10);

        anchorPane.getChildren().addAll(lines);
        anchorPane.getChildren().addAll(labels);
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
                label.setFont(new Font(10));
                label.setLayoutX(x);
                label.setLayoutY(totalRows*15 + 5);
                totalRows += 1 ;
                labels.add(label);
                if (i != 0 ){
                    Label last = getAvailableLabelByTechnologyName(components.get(i-1));
                    Line line = new Line(last.getLayoutX() , last.getLayoutY() , label.getLayoutX() , label.getLayoutY());
                    lines.add(line);
                }
            }
            x += 100;
        }
    }

    public Label getAvailableLabelByTechnologyName (String name){
        for (Label label : labels) {
            if (label.getText().equals(name))
                return label;
        }
        return null ;
    }

}


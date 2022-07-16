module game {
    requires java.desktop;
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.base;
    requires java.xml;
    requires org.controlsfx.controls;
    requires javafx.media;
    opens game.Controller to com.google.gson;
    opens game.Model to com.google.gson;
    opens game.Controller.Chat to com.google.gson ;
    opens game.View.controller to javafx.fxml;
    opens game.Controller.menu to javafx.fxml;

    exports game;
    opens game.Controller.menu.fxmlControllers to javafx.fxml;
}
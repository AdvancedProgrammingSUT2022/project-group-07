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
    requires org.apache.commons.io;
    opens game.Controller to com.google.gson;
    opens game.Model to com.google.gson;
    opens game.Controller.Chat to com.google.gson ;
    opens game.View.controller to javafx.fxml;
    opens game.Controller.menu to javafx.fxml;
    opens game.Controller.game to com.google.gson ;
    opens game.View.components to com.google.gson ;
    opens game.Enum to com.google.gson ;

    exports game.Enum to com.google.gson ;
    exports game.Model to com.google.gson ;
    exports game.Controller to com.google.gson ;
    exports game;
    opens game.View.controller.ChatControllers to javafx.fxml;
}
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
    exports game;
}
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
    opens game.Server.Controller to com.google.gson;
    opens game.Common.Model to com.google.gson;
    opens game.Server.Controller.Chat to com.google.gson ;
    opens game.Common.Model.Network to com.google.gson ;
    opens game.Common.Enum to com.google.gson ;

    exports game.Common.Enum.Network to com.google.gson ;
    exports game.Common.Model to com.google.gson ;

    opens game.Client.View.controller to javafx.fxml;
    opens game.Server.Controller.menu to javafx.fxml;

    opens game.Client.View.controller.ChatControllers to javafx.fxml;
    exports game.Client;

    exports game.Server.Controller.game to com.google.gson ;
    exports game.Client.View to javafx.fxml ;
    opens game.Client.View to javafx.fxml ;

    exports game.Client.View.components to javafx.fxml ;

    exports game.Server to javafx.graphics ;

    exports game.Common.Model.Network ;
}
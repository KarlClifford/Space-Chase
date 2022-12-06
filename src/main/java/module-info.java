module com.example.spacechase {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.net.http;
    requires java.desktop;

    opens com.example.spacechase to javafx.fxml;
    exports com.example.spacechase;
    exports com.example.spacechase.controllers;
    opens com.example.spacechase.controllers to javafx.fxml;
    opens com.example.spacechase.models to javafx.fxml;
    exports com.example.spacechase.utils;
    opens com.example.spacechase.utils to javafx.fxml;
    exports com.example.spacechase.services;
    opens com.example.spacechase.services to javafx.fxml;
    exports com.example.spacechase.models.characters;
    opens com.example.spacechase.models.characters to javafx.fxml;
    exports com.example.spacechase.models.items;
    opens com.example.spacechase.models.items to javafx.fxml;
    exports com.example.spacechase.models.level;
    opens com.example.spacechase.models.level to javafx.fxml;
    exports com.example.spacechase.models;
}
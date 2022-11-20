module com.example.spacechase {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.example.spacechase to javafx.fxml;
    exports com.example.spacechase;
}
module com.example.spacechase {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;

    opens com.example.spacechase to javafx.fxml;
    exports com.example.spacechase;
}
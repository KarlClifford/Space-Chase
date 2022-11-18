module com.example.spacechase {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spacechase to javafx.fxml;
    exports com.example.spacechase;
}
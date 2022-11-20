package com.example.spacechase;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    /**
     * Stage of the game.
     */
    private static Stage stage;

    /**
     * @param stage stage of the game.
     */
    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }

    /**
     * @param scene scene of the game.
     */
    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Loads a scene from fxml file and casts it to the stage.
     * @param source source of the fxml file.
     */
    protected void loadFxml(String source) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
        AnchorPane pane;
        try {
            pane = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Scene scene = new Scene(pane, App.STAGE_WIDTH, App.STAGE_HEIGHT);

        stage.setScene(scene);
    }
}

package com.example.spacechase.controllers;

import com.example.spacechase.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import com.example.spacechase.utils.Data;

/**
 * This class represents a controller. A controller contains
 * components of a stage. A controller controls the current
 * stage and scene, and can load a scene from a fxml file.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class Controller {
    /**
     * Stage of the game.
     * @see javafx.stage
     */
    private static Stage stage;

    /**
     * Sets the stage of the game.
     * @param stage stage of the game.
     */
    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }

    /**
     * Sets the scene of the game.
     * @param scene scene of the game.
     */
    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /**
     * Loads a scene from fxml file and casts it to the stage.
     * Gets the controller of the fxml file.
     * @param source source of the fxml file.
     * @return controller of the fxml file.
     */
    public Object loadFxml(String source) {
        URL url = Data.getUrl(source);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Pane pane;

        // Try to load the fxml file, throws exception if loader fails.
        try {
            pane = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        Scene scene = new Scene(pane, App.STAGE_WIDTH, App.STAGE_HEIGHT);

        stage.setScene(scene);

        return fxmlLoader.getController();
    }
}

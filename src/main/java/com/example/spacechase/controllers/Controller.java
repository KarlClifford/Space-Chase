package com.example.spacechase.controllers;

import com.example.spacechase.utils.Data;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

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
    protected static Stage stage;

    /**
     * Gets the stage of the game.
     * @return stage of the game.
     */
    public static Stage getStage() {
        return stage;
    }

    /**
     * Sets the stage of the game.
     * @param stage stage of the game.
     */
    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }

    /**
     * Sets the root of scene in stage.
     * @param parent root of scene.
     */
    public static void setRoot(Parent parent) {
        stage.getScene().setRoot(parent);
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

        stage.getScene().setRoot(pane);

        return fxmlLoader.getController();
    }
}

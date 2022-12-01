package com.example.spacechase;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

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
     * @param source source of the fxml file.
     * @return scene of the fxml file.
     */
    protected Object loadFxml(String source) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
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

package com.example.spacechase;

import com.example.spacechase.controllers.Controller;
import com.example.spacechase.controllers.SettingsController;
import com.example.spacechase.services.SoundEngine;
import com.example.spacechase.utils.Data;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * This class represents the main app of the game. It loads
 * the required fonts and launch the game with main menu.
 * @author Tristan Tsang
 * @author Karl Clifford
 * @version 1.0.2
 */
public class App extends Application {
    /**
     * Create the music player that will be used to play global audio.
     */
    public static final SoundEngine MUSIC_PLAYER = new SoundEngine();
    /**
     * Width of the stage.
     * @see javafx.stage
     */
    public static final double STAGE_WIDTH = Screen.getPrimary()
            .getBounds()
            .getWidth();
    /**
     * Height of the stage.
     * @see javafx.stage
     */
    public static final double STAGE_HEIGHT = Screen.getPrimary()
            .getBounds()
            .getHeight();
    /**
     * Title of the stage.
     * @see javafx.stage
     */
    public static final String TITLE = "Space Chase";
    /**
     * Resizeable of the stage.
     * @see javafx.stage
     */
    public static final boolean RESIZEABLE = false;
    /**
     * Font size.
     * @see javafx.scene.text.Font
     */
    public static final int FONT_SIZE = 10;
    /**
     * Name of directory that contains all fonts.
     */
    private static final String FONTS_DIRECT = "fonts";

    /**
     * @param stage primary stage of the game.
     * @throws IOException This exception is being thrown when the FXMLLoader
     * cannot load the scene.
     */
    @Override
    public void start(final Stage stage) throws IOException {
        SettingsController settingsController = new SettingsController();
        settingsController.loadSettings();

        Controller.setStage(stage);

        Pane pane = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource("fxml/titleScreen.fxml")));
        pane.setPrefSize(App.STAGE_WIDTH, App.STAGE_HEIGHT);

        Scene scene = new Scene(pane);
        stage.setScene(scene);

        stage.setTitle(TITLE);
        stage.setResizable(RESIZEABLE);
        stage.show();


    }

    /**
     * Loads all the fonts and launches the game.
     * @param args parameters on launching the game.
     */
    public static void main(final String[] args) {
        loadFonts();
        // Launch game.
        launch(args);
    }

    /**
     * Loads every font from the font resource folder.
     */
    private static void loadFonts() {
        File fontsDirect = Data.getFileFromPath(FONTS_DIRECT);

        // For every font file in fonts directory, load the font.
        for (File file : Objects.requireNonNull(fontsDirect.listFiles())) {
            Font.loadFont("file:" + file.getPath(), FONT_SIZE);
        }
    }
}

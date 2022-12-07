package com.example.spacechase;

import com.example.spacechase.controllers.Controller;
import com.example.spacechase.services.GameMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JOptionPane;

/**
 * This class represents the main app of the game. It loads
 * the required fonts and launch the game with main menu.
 * @author Tristan Tsang
 * @author Karl Clifford
 * @version 1.0.1
 */
public class App extends Application {
    /**
     * Width of the stage.
     * @see javafx.stage
     */
    public static final double STAGE_WIDTH = 800;
    /**
     * Height of the stage.
     * @see javafx.stage
     */
    public static final double STAGE_HEIGHT = 500;
    /**
     * Title of the stage.
     * @see javafx.stage
     */
    public static final String TITLE = "Space Chase";
    /**
     * Resizeable of the stage.
     * @see javafx.stage
     */
    public static final boolean RESIZEABLE = true;
    /**
     * Font size.
     * @see javafx.scene.text.Font
     */
    public static final int FONT_SIZE = 10;
    /**
     * Path of fonts.
     */
    private static final String PATH_TO_FONTS =
            "src/main/resources/com/example/spacechase/fonts";

    /**
     * @param stage primary stage of the game.
     * @throws IOException This exception is being thrown when the FXMLLoader
     * cannot load the scene.
     */
    @Override
    public void start(final Stage stage) throws IOException {
        Controller.setStage(stage);

        Pane pane = FXMLLoader.load(
                Objects.requireNonNull(
                        getClass().getResource("fxml/mainMenu.fxml")));

        Scene scene = new Scene(pane);
        stage.setScene(scene);

        stage.setTitle(TITLE);
        stage.setWidth(STAGE_WIDTH);
        stage.setHeight(STAGE_HEIGHT);
        stage.setResizable(RESIZEABLE);
        stage.show();
    }

    /**
     * Loads all the fonts and launches the game.
     * @param args parameters on launching the game.
     */
    public static void main(final String[] args) {
        loadFonts();
        String messageOfTheDay;
        // Try to retrieve the message of the day.
        try {
            messageOfTheDay = GameMessage.fetch();
        } catch (IOException e) {
            // The user isn't connected to the internet.
            messageOfTheDay = ("Error: Couldn't get the message of the day. "
                    + "Are you connected to the internet?");
            // The user disconnected from the internet while fetching the MOTD.
        } catch (InterruptedException e) {
            messageOfTheDay = ("Error: Couldn't get the message of the day.");
        }
        // Show message of the day.
        JOptionPane.showMessageDialog(null, messageOfTheDay);
        // Launch game.
        launch(args);
    }

    /**
     * Loads every font from the font resource folder.
     */
    private static void loadFonts() {
        // For every font file in fonts directory, load the font.
        for (File file : Objects.requireNonNull(
                new File(PATH_TO_FONTS).listFiles())) {
            Font.loadFont("file:" + file.getPath(), FONT_SIZE);
        }
    }
}

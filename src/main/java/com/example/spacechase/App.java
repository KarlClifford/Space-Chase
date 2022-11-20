package com.example.spacechase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    /**
     * width of the stage.
     */
    public static final double STAGE_WIDTH = 600;
    /**
     * height of the stage.
     */
    public static final double STAGE_HEIGHT = 500;
    /**
     * title of the stage.
     */
    public static final String TITLE = "Space Chase";
    /**
     * resizeable of the stage.
     */
    public static final boolean RESIZEABLE = true;
    /**
     * font size.
     */
    public static final int FONT_SIZE = 10;
    /**
     * path of fonts.
     */
    private static final String PATH_TO_FONTS =
            "src/main/resources/com/example/spacechase/fonts";

    /**
     * @param stage primary stage of the game.
     * @throws IOException
     */
    @Override
    public void start(final Stage stage) throws IOException {
        Controller.setStage(stage);

        AnchorPane pane = FXMLLoader.load(
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
     * @param args
     */
    public static void main(final String[] args) {
        loadFonts();
        launch(args);
    }

    /**
     * Loads every font in the font resource folder.
     */
    private static void loadFonts() {
        for (File file : Objects.requireNonNull(
                new File(
                        PATH_TO_FONTS)
                        .listFiles())) {
            Font.loadFont("file:" + file.getPath(), FONT_SIZE);
        }
    }
}

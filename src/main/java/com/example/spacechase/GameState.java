package com.example.spacechase;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameState {
    /**
     * spacing of vbox.
     */
    private static final int VBOX_SPACING = 10;
    /**
     * large font size.
     */
    private static final int LARGE_FONT_SIZE = 50;
    /**
     * normal font size.
     */
    private static final int NORM_FONT_SIZE = 16;
    /**
     * level of the game state.
     */
    private final Level level;

    /**
     * Creates a new GameState object.
     * @param level level.
     */
    public GameState(Level level) {
        this.level = level;
    }

    /**
     * Creates a pause button, when it is pressed, pause time,
     * save the current level, and launch the pause menu.
     * @return pause button
     */
    public Button createPauseButton() {
        Button pauseButton = new Button();
        pauseButton.setFont(Font.font("neuropol x rg", NORM_FONT_SIZE));
        pauseButton.setText("||");
        pauseButton.setOnMouseClicked(e -> {
            GameClock clock = level.getClock();
            clock.setRun(false);

            Data.saveLevel(level);

            createPauseMenu();


        });

        return pauseButton;
    }

    /**
     * Creates a pause menu. It is launched when pause button
     * is clicked.
     */
    private void createPauseMenu() {
        final Scene levelScene = level.getScene();

        Button resumeButton = new Button();
        resumeButton.setText("resume");
        resumeButton.setFont(Font.font("neuropol x rg", NORM_FONT_SIZE));

        Button restartButton = new Button();
        restartButton.setText("restart");
        restartButton.setFont(Font.font("neuropol x rg", NORM_FONT_SIZE));

        Button menuButton = new Button();
        menuButton.setText("menu");
        menuButton.setFont(Font.font("neuropol x rg", NORM_FONT_SIZE));

        Button quitButton = new Button();
        quitButton.setText("quit game");
        quitButton.setFont(Font.font("neuropol x rg", NORM_FONT_SIZE));

        //WebView webView = new WebView();
        //webView.getEngine().load("file:"
        //+ new File(
        // "src/main/resources/com/example/spacechasealt/html/ad.html")
        // .getAbsolutePath());

        resumeButton.setOnMouseClicked(e -> {
            GameClock clock = level.getClock();
            clock.setRun(true);

            Controller.setScene(levelScene);
        });

        restartButton.setOnMouseClicked(e -> level.restart());

        menuButton.setOnMouseClicked(e -> {
            Controller controller = new Controller();
            controller.loadFxml("fxml/levelMenu.fxml");
        });

        quitButton.setOnMouseClicked(e -> Platform.exit());

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(VBOX_SPACING);
        vBox.setStyle("-fx-background-color: BLACK");
        vBox.getChildren().addAll(
                resumeButton,
                menuButton,
                restartButton,
                quitButton);

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: BLACK");
        pane.setCenter(vBox);

        Scene scene = new Scene(pane);
        Controller.setScene(scene);
    }

    /**
     * Creates an end level menu. It is created when a level has ended.
     * @param win the state of the game, true of win and false for lose.
     */
    public void createEndLevelMenu(boolean win) {
        Label levelStateLabel = new Label();
        levelStateLabel.setFont(Font.font("iomanoid", LARGE_FONT_SIZE));

        Button levelButton = new Button();
        levelButton.setFont(Font.font("neuropol x rg", NORM_FONT_SIZE));

        if (win) {
            levelStateLabel.setText("Level cleared!");
            levelStateLabel.setTextFill(Color.GREEN);
            levelButton.setText("next");
            levelButton.setOnMouseClicked(e -> level.next());
        } else {
            levelStateLabel.setText("Level failed!");
            levelStateLabel.setTextFill(Color.RED);
            levelButton.setText("restart");
            levelButton.setOnMouseClicked(e -> level.restart());
        }

        Button menuButton = new Button();
        menuButton.setText("menu");
        menuButton.setFont(Font.font("neuropol x rg", NORM_FONT_SIZE));
        menuButton.setOnMouseClicked(e -> {
            Controller controller = new Controller();
            controller.loadFxml("fxml/levelMenu.fxml");
        });

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(VBOX_SPACING);
        vBox.setStyle("-fx-background-color: BLACK");
        vBox.getChildren().addAll(levelStateLabel, levelButton, menuButton);

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: BLACK");
        pane.setCenter(vBox);

        Scene scene = new Scene(pane);
        Controller.setScene(scene);
    }
}

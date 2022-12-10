package com.example.spacechase.controllers;

import com.example.spacechase.models.Level;
import com.example.spacechase.models.level.GameClock;
import com.example.spacechase.services.SoundEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

/**
 * This class represents a controller of the pause menu.
 * This menu contains resume button that resumes the level,
 * restart button that restarts the level, main menu button
 * that goes to main menu, and quit game button that quits
 * the game.
 * @author Tristan Tsang
 * @author Karl Clifford
 * @version 1.0.0
 */
public class PauseMenuController extends Controller {
    /**
     * Fxml file path of level menu.
     * @see javafx.fxml
     */
    private static final String LEVEL_MENU_FXML_PATH = "fxml/levelMenu.fxml";
    /**
     * Button to resume the level.
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button resumeButton;
    /**
     * Button to restart the level.
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button restartButton;

    /**
     * Sets the resume button to be able to resume
     * the game clock of the level. Sets the restart
     * button to be able to restart the current
     * level.
     * @param level current level that is in.
     */
    public void start(Level level) {
        resumeButton.setOnMouseClicked(e -> {
            GameClock clock = level.getClock();
            clock.setRun(true);

            Scene levelScene = level.getScene();
            setScene(levelScene);
        });

        restartButton.setOnMouseClicked(e -> level.restart());
    }

    /**
     * Loads the main menu if button is clicked.
     */
    @FXML
    private void onMainMenuButtonClicked() {
        loadFxml(LEVEL_MENU_FXML_PATH);

        // Stop the playing music and start the menu music.
        App.MUSIC_PLAYER.stopMusic();
        App.MUSIC_PLAYER.playSound(
                SoundEngine.Sound.MENU_MUSIC,
                SoundEngine.MUSIC_VOLUME,
                true);
    }

    /**
     * Quits the game if button is clicked.
     */
    @FXML
    private void onQuitGameButtonClicked() {
        Platform.exit();
    }
}

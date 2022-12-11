package com.example.spacechase.controllers;

import com.example.spacechase.App;
import com.example.spacechase.models.Level;
import com.example.spacechase.services.SoundEngine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * This class represents a controller for level ended
 * menu. This menu contains an action button that can
 * either restarts the level or proceeds to the next
 * level.
 * @author Tristan Tsang
 * @author Daniel Halsall
 * @author Karl Clifford
 * @version 1.0.2
 */
public class LevelEndedMenuController extends Controller {
    /**
     * Text that shows level cleared.
     */
    private static final String LEVEL_CLEARED_TEXT = "Level Cleared!";
    /**
     * Text that shows level failed.
     */
    private static final String LEVEL_FAILED_TEXT = "Level Failed!";
    /**
     * Text that shows on next button.
     */
    private static final String NEXT_TEXT = "next";
    /**
     * Text that shows on restart button.
     */
    private static final String RESTART_TEXT = "restart";
    /**
     * Fxml file path of level menu.
     * @see javafx.fxml
     */
    private static final String LEVEL_MENU_FXML_PATH = "fxml/levelMenu.fxml";
    /**
     * Label that shows the state of the level.
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label levelEndedLabel;
    /**
     * Button that either restarts the level
     * or transfers to the next level.
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button actionButton;

    /**
     * Sets action button to next where it can start next
     * level if level is cleared. Otherwise, sets action button
     * to restart where it can restart the current level.
     * @param level current level that is in.
     * @param isLevelCleared level is cleared or not.
     */
    public void start(Level level, boolean isLevelCleared) {
        /*
         * If the level is cleared, display a level cleared
         * scene with next button that leads to next level.
         * Otherwise, display a level failed scene with
         * restart button that restarts the level.
         */
        if (isLevelCleared) {
            levelEndedLabel.setText(LEVEL_CLEARED_TEXT);
            levelEndedLabel.setTextFill(Color.GREEN);
            actionButton.setText(NEXT_TEXT);
            actionButton.setOnMouseClicked(e -> level.next());
        } else {
            levelEndedLabel.setText(LEVEL_FAILED_TEXT);
            levelEndedLabel.setTextFill(Color.RED);
            actionButton.setText(RESTART_TEXT);
            actionButton.setOnMouseClicked(e -> {
                AdvertController controller = (AdvertController)
                        new Controller()
                                .loadFxml("fxml/advert.fxml");
                controller.start(level);
            });
        }
    }

    /**
     * Loads main menu if button is clicked.
     */
    @FXML
    private void onMainMenuButtonClicked() {
        loadFxml(LEVEL_MENU_FXML_PATH);

        // Stop the playing music and start the menu music.
        App.MUSIC_PLAYER.stopMusic();
        App.MUSIC_PLAYER.playSound(
                SoundEngine.Sound.MENU_MUSIC,
                SoundEngine.getMusicVolume(),
                true);
    }
}

package com.example.spacechase.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 * This class represents the controller of settings
 * menu. It can control the volume of music in game.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class SettingsController extends Controller {
    /**
     * Fxml file path of main menu.
     * @see javafx.fxml
     */
    private static final String MAIN_MENU_FXML_PATH = "fxml/mainMenu.fxml";
    /**
     * Slider that controls the volume of the game.
     * @see javafx.scene.control.Slider
     */
    @FXML
    private Slider musicSlider;
    /**
     * Label that shows the volume of the game.
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label volumeLabel;

    /**
     * Changes the volume when the slider is dragged.
     */
    @FXML
    private void initialize() {
        musicSlider.valueProperty().addListener(e -> {
            int volume = (int) musicSlider.getValue();
            volumeLabel.setText(String.valueOf(volume));
            // To be added after sound engine.
            //SoundEngine.setVolume(volume);
        });
    }

    /**
     * Goes to main menu if clicked.
     */
    @FXML
    private void onBackButtonClicked() {
        loadFxml(MAIN_MENU_FXML_PATH);
    }
}

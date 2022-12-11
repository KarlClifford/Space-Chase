package com.example.spacechase.controllers;

import com.example.spacechase.App;
import com.example.spacechase.models.characters.Player;
import com.example.spacechase.services.SoundEngine;
import com.example.spacechase.utils.Control;
import com.example.spacechase.utils.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.json.simple.JSONValue;
import java.util.HashMap;

/**
 * This class represents the controller of settings
 * menu. It can control the volume of music in game.
 * @author Tristan Tsang
 * @version 1.0.1
 */
public class SettingsController extends Controller {
    /**
     * Path to the settings file.
     */
    private static final String SETTINGS_PATH = "data/settings/settings.json";
    /**
     * Fxml file path of main menu.
     * @see javafx.fxml
     */
    private static final String MAIN_MENU_FXML_PATH = "fxml/mainMenu.fxml";
    /**
     * Slider that controls the volume of the music.
     * @see javafx.scene.control.Slider
     */
    @FXML
    private Slider musicSlider;
    /**
     * Slider that controls the volume of the sound effects.
     * @see javafx.scene.control.Slider
     */
    @FXML
    private Slider fxSlider;
    /**
     * Label that shows the volume of the music.
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label volumeLabel;
    /**
     * Label that shows the volume of the sound effects.
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label fxLabel;

    /**
     * Text field for W keybinding.
     */
    @FXML
    private TextField upTextField;

    /**
     * Text field for S keybinding.
     */
    @FXML
    private TextField downTextField;

    /**
     * Text field for A keybinding.
     */
    @FXML
    private TextField leftTextField;

    /**
     * Text field for D keybinding.
     */
    @FXML
    private TextField rightTextField;

    /**
     * Changes the volume when the slider is dragged.
     */
    @FXML
    private void initialize() {
        loadSettings();
        musicSlider.setValue(SoundEngine.getMusicVolume());
        fxSlider.setValue(SoundEngine.getSoundEffectVolume());
        /*
         * Changes the volume of music whenever music slider
         *  is interacted.
         */
        musicSlider.valueProperty().addListener(e -> {
            String volume = String.valueOf(musicSlider.getValue());
            changeSetting("music", volume);
        });

        /*
         * Changes the volume of sound effects whenever fx
         *  slider is interacted.
         */
        fxSlider.valueProperty().addListener(e -> {
            String volume = String.valueOf(fxSlider.getValue());
            changeSetting("fx", volume);
        });
    }

    /**
     * Loads local settings configurations.
     */
    public void loadSettings() {
        HashMap<Object, Object> settings =
                Data.readJsonAsHashMap(SETTINGS_PATH);

        // Change each setting with its value.
        for (Object key : settings.keySet()) {
            String attribute = String.valueOf(key);
            String value = String.valueOf(settings.get(key));

            changeSetting(attribute, value);
        }
    }

    /**
     * Changes setting in settings json.
     * @param attribute attribute of setting.
     * @param value value of setting.
     */
    private void writeSetting(Object attribute, Object value) {
        // Assign attribute, value pair to hashmap of settings.
        HashMap<Object, Object> settings =
                Data.readJsonAsHashMap(SETTINGS_PATH);
        settings.put(attribute, value);

        // Write settings as json format into settings json.
        String jsonString = JSONValue.toJSONString(settings);
        Data.writeJson(SETTINGS_PATH, jsonString);
    }

    /**
     * Changes the given setting to value.
     * @param attribute attribute of setting.
     * @param value value of setting.
     */
    private void changeSetting(String attribute, String value) {
        switch (attribute) {
            case "music" -> {
                int volume = (int) Double.parseDouble(value);
                setMusicVolume(volume);
            }
            case "fx" -> {
                int volume = (int) Double.parseDouble(value);
                setFxVolume(volume);
            }
            default ->
                    throw new IllegalArgumentException(
                            "ERROR: Illegal attribute provided.");
        }

        writeSetting(attribute, value);
    }

    /**
     * Sets the volume of the music.
     * @param volume volume of the music.
     */
    private void setMusicVolume(int volume) {
        if (volumeLabel != null) {
            volumeLabel.setText(String.valueOf(volume));
        }
        SoundEngine.setMusicVolume(volume);
    }

    /**
     * Sets the volume of the sound effects.
     * @param volume volume of the sound effects.
     */
    private void setFxVolume(int volume) {
        if (fxLabel != null) {
            fxLabel.setText(String.valueOf(volume));
        }
        SoundEngine.setFxMusicVolume(volume);
    }


    /**
     * Sets all the keybinds.
     */
    @FXML
    private void setKeyBinds() {
        for (TextField textField : new TextField[] {
                upTextField, downTextField, leftTextField, rightTextField}) {
            Control control = switch (textField.toString()) {
                case "upTextField" -> Control.UP;
                case "downTextField" -> Control.DOWN;
                case "leftTextField" -> Control.LEFT;
                case "rightTextField" -> Control.RIGHT;
                default -> Control.NULL;
            };

            String input = textField.getText();

            // Sets the keybind if it keybind is one character.
            if (input.length() == 1) {
                KeyCode keyCode = KeyCode.getKeyCode(input);
                Player.setKeybind(control, keyCode);
            }
        }
    }

    /**
     * Goes to main menu if clicked.
     */
    @FXML
    private void onBackButtonClicked() {
        App.MUSIC_PLAYER.stopMusic();
        App.MUSIC_PLAYER.playSound(
                        SoundEngine.Sound.MENU_MUSIC,
                        SoundEngine.getMusicVolume(),
                        true);
        loadFxml(MAIN_MENU_FXML_PATH);
    }
}

package com.example.spacechase.controllers;

import com.example.spacechase.App;
import com.example.spacechase.models.characters.Player;
import com.example.spacechase.services.SoundEngine;
import com.example.spacechase.utils.Control;
import com.example.spacechase.utils.Data;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import org.json.simple.JSONValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

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
     * Button for up keybinding.
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button upKeybind;
    /**
     * Button for down keybinding.
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button downKeybind;
    /**
     * Button for left keybinding.
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button leftKeybind;
    /**
     * Button for right keybinding.
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button rightKeybind;
    /**
     * Whether user is setting key bind.
     */
    private BooleanProperty isKeybinding;

    /**
     * Changes the volume when the slider is dragged.
     */
    @FXML
    private void initialize() {
        isKeybinding = new SimpleBooleanProperty(false);

        // Loads all the settings.
        loadSettings();

        // Sets the label of music and sound effect volume.
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
        /*
         * Gets the corresponding attribute and applies changes
         * in game.
          */
        switch (attribute) {
            case "music" -> {
                int volume = (int) Double.parseDouble(value);
                setMusicVolume(volume);
            }
            case "fx" -> {
                int volume = (int) Double.parseDouble(value);
                setFxVolume(volume);
            }
            case "UP", "DOWN", "LEFT", "RIGHT"
                    -> readKeybind(attribute, value);
            default ->
                    throw new IllegalArgumentException(
                            "ERROR: Illegal attribute provided.");
        }

        // Stores it in json file.
        writeSetting(attribute, value);
    }

    /**
     * Sets the volume of the music.
     * @param volume volume of the music.
     */
    private void setMusicVolume(int volume) {
        // Displays music volume if label exists.
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
        // Displays sound effects volume if label exists.
        if (fxLabel != null) {
            fxLabel.setText(String.valueOf(volume));
        }
        SoundEngine.setFxMusicVolume(volume);
    }

    /**
     * Reads keybind and sets it.
     * @param controlStr control in string.
     * @param keyCodeStr key code in string.
     */
    private void readKeybind(String controlStr, String keyCodeStr) {
        Control control = Control.valueOf(controlStr);
        KeyCode keyCode = KeyCode.valueOf(keyCodeStr);
        Player.setKeybind(control, keyCode);

        updateKeybindText(control, keyCode);
    }

    /**
     * Sets the text in the keybind button to keycode.
     * @param control control of the game.
     * @param keyCode keycode of the control.
     */
    private void updateKeybindText(Control control, KeyCode keyCode) {
        Button[] keybinds = new Button[] {
                upKeybind, downKeybind, leftKeybind, rightKeybind};

        // Gets the corresponding button and change its text to key code.
        Arrays.stream(keybinds)
                .filter(Objects::nonNull)
                .filter(button -> button.getId()
                        .replaceAll("Keybind", "")
                        .toUpperCase()
                        .equals(control.toString()))
                .findFirst()
                .ifPresent(button -> button.setText(keyCode.toString()));
    }

    /**
     * Fires when a keybind button is clicked. Waits for player
     * to input a key and sets the keybind for that control.
     * @param event event of key binding.
     */
    @FXML
    private void onKeybinding(MouseEvent event) {
        Button button = (Button) event.getSource();
        Control control = Control.valueOf(
                button.getId()
                        .replaceAll("Keybind", "")
                        .toUpperCase());

        isKeybinding.set(true);
        AtomicBoolean cancelBind = new AtomicBoolean(false);

        Scene scene = stage.getScene();
        scene.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();

            // Sets the keybind if this bind is not cancelled.
            if (!cancelBind.get()) {
                isKeybinding.set(false);
                writeSetting(control.toString(), keyCode.toString());
                Player.setKeybind(control, keyCode);
                button.setText(keyCode.getName());
            }

            e.consume();
        });

        // Cancel the bind if another keybind is requested.
        isKeybinding.addListener(e -> cancelBind.set(true));
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

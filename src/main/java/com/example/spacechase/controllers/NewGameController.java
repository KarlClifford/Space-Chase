package com.example.spacechase.controllers;

import com.example.spacechase.utils.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

/**
 * This class represents a controller for new game menu.
 * This controller contains components of a username text field,
 * a error message label, and a back button.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class NewGameController extends Controller {
    /**
     * Name text field.
     * @see javafx.scene.control.TextField
     */
    @FXML
    private TextField nameTextField;

    /**
     * Error message label.
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label errorMessageLabel;

    /**
     * Creates a new profile if the username is valid, and goes
     * to level menu of the player. Otherwise, an error message
     * is shown.
     * @throws IOException This exception is thrown when it fails
     * to load the level menu scene.
     */
    @FXML
    private void onConfirmButtonClicked() throws IOException {
        String name = nameTextField.getText();
        boolean success = Data.createProfile(name);

        /* If successful in creating a profile, load the level menu.
         Otherwise, show an error message. */
        if (success) {
            LevelMenuController.setPlayerName(name);
            loadFxml("fxml/levelMenu.fxml");
        } else {
            errorMessageLabel.setOpacity(1);
        }
    }

    /**
     * Goes back to main menu when clicked.
     */
    @FXML
    private void onBackButtonClicked() {
        loadFxml("fxml/mainMenu.fxml");
    }
}

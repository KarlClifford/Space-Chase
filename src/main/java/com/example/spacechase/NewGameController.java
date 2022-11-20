package com.example.spacechase;

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
     * name text field.
     */
    @FXML
    private TextField nameTextField;

    /**
     * error message label.
     */
    @FXML
    private Label errorMessageLabel;

    /**
     * Creates a new profile if the username is valid, and goes
     * to level menu of the player. Otherwise, an error message
     * is shown.
     * @throws IOException
     */
    @FXML
    private void onConfirmButtonClicked() throws IOException {
        String name = nameTextField.getText();
        boolean success = Data.createProfile(name);

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

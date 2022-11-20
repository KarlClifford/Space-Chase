package com.example.spacechase;

import javafx.fxml.FXML;

/**
 * This class represents a controller for main menu.
 * This controller contains components of a new game button
 * and load game button.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class MainMenuController extends Controller {
    /**
     * Goes to new game menu when clicked.
     */
    @FXML
    private void onNewGameButtonClicked() {
        loadFxml("fxml/newGame.fxml");
    }

    /**
     * Goes to load game menu when clicked.
     */
    @FXML
    private void onLoadGameButtonClicked() {
        loadFxml("fxml/loadGame.fxml");
    }
}

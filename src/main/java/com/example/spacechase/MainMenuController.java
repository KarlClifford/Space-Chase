package com.example.spacechase;

import javafx.fxml.FXML;

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

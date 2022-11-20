package com.example.spacechase;

import javafx.fxml.FXML;

public class MainMenuController extends Controller {
    /**
     * Goes to new game menu when clicked.
     */
    @FXML
    private void onNewGameButtonClicked() {
        loadFxml("menus/newGame.fxml");
    }

    /**
     * Goes to load game menu when clicked.
     */
    @FXML
    private void onLoadGameButtonClicked() {
        loadFxml("menus/loadGame.fxml");
    }
}

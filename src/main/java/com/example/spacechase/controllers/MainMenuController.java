package com.example.spacechase.controllers;

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
     * Fxml file path of new game.
     * @see javafx.fxml
     */
    private static final String NEW_GAME_FXML_PATH = "fxml/newGame.fxml";
    /**
     * Fxml file path of load game.
     * @see javafx.fxml
     */
    private static final String LOAD_GAME_FXML_PATH = "fxml/loadGame.fxml";
    /**
     * Fxml file path of high score table.
     * @see javafx.fxml
     */
    private static final String HIGH_SCORE_TABLE_PATH = "fxml/highScoreTable.fxml";
    /**
     * Fxml file path of settings.
     * @see javafx.fxml
     */
    private static final String SETTINGS_FXML_PATH = "fxml/settings.fxml";

    /**
     * Goes to new game menu when clicked.
     */
    @FXML
    private void onNewGameButtonClicked() {
        loadFxml(NEW_GAME_FXML_PATH);
    }

    /**
     * Goes to load game menu when clicked.
     */
    @FXML
    private void onLoadGameButtonClicked() {
        loadFxml(LOAD_GAME_FXML_PATH);
    }

    /**
     * Goes to high score table when clicked.
     */
    @FXML
    private void onHighScoreTableButtonClicked() {
        loadFxml(HIGH_SCORE_TABLE_PATH);
    }

    /**
     * Goes to settings when clicked.
     */
    @FXML
    private void onSettingsButtonClicked() {
        loadFxml(SETTINGS_FXML_PATH);
    }


}

package com.example.spacechase;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

/**
 * This class represents a controller for level selection menu.
 * This controller contains components of a buttons for different level
 * and a back button.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class LevelMenuController extends Controller {
    /**
     * Player name.
     */
    private static String playerName;
    /**
     * Container of all level buttons.
     * @see javafx.scene.layout.FlowPane
     */
    @FXML
    private FlowPane levelContainer;

    /**
     * Sets player name.
     * @param name player name.
     */
    public static void setPlayerName(String name) {
        playerName = name;
    }

    /**
     * Creates a new button for each level. Starts the level
     * if it is clicked.
     */
    @FXML
    private void initialize() {
        Level[] levels = Data.getLevels(playerName);

        // Creates a button for each level exists in player profile.
        for (Level level : levels) {
            int id = level.getId();
            Button button = new Button();
            button.setText(String.valueOf(id));
            button.setOnMouseClicked(e -> level.start());

            levelContainer.getChildren().add(button);
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

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
     * player name.
     */
    private static String playerName;
    /**
     * container of all level buttons.
     */
    @FXML
    private FlowPane levelContainer;

    /**
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

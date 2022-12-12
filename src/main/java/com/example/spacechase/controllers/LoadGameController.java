package com.example.spacechase.controllers;

import com.example.spacechase.utils.Data;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * This class represents a controller for load game menu.
 * This controller contains components of a buttons for loading
 * and deleting a profile, and a back button.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class LoadGameController extends Controller {
    /**
     * Spacing for HBox.
     * @see javafx.scene.layout.HBox
     */
    private static final int HBOX_SPACING = 10;
    /**
     * Font size.
     * @see javafx.scene.text.Font
     */
    private static final int FONT_SIZE = 12;
    /**
     * Height of bin image.
     */
    private static final int BIN_IMAGE_HEIGHT = 20;
    /**
     * VBox container of profiles.
     * @see javafx.scene.layout.VBox
     */
    @FXML
    private VBox profileContainer;

    /**
     * Shows every profile with a load button and delete button.
     */
    @FXML
    private void initialize() {
        String[] profiles = Data.getProfiles();

        // Create a profile box for each profile.
        for (String name : profiles) {
            HBox hBox = new HBox();
            hBox.setSpacing(HBOX_SPACING);
            hBox.setAlignment(Pos.CENTER_LEFT);

            Label label = createProfileLabel(name);

            Button loadButton = createLoadButton(name);
            Button deleteButton = createDeleteButton(name);

            hBox.getChildren().addAll(label, loadButton, deleteButton);
            profileContainer.getChildren().addAll(hBox);
        }
    }

    /**
     * Goes back to main menu when clicked.
     */
    @FXML
    private void onBackButtonClicked() {
        loadFxml("fxml/mainMenu.fxml");
    }

    /**
     * Creates a new profile label.
     * @param name player name
     * @return Label of the profile
     */
    private Label createProfileLabel(String name) {
        Label label = new Label();
        label.setText(name);
        label.setFont(Font.font("neuropol x rg", FONT_SIZE));

        return label;
    }

    /**
     * Creates a load button. Goes to level menu of the player if
     * it is clicked.
     * @param name player name.
     * @return load button.
     */
    private Button createLoadButton(String name) {
        Button loadButton = new Button();
        loadButton.setText("Load");
        loadButton.setFont(Font.font("neuropol x rg", FONT_SIZE));
        loadButton.setOnMouseClicked(e -> {
            LevelMenuController.setPlayerName(name);
            loadFxml("fxml/levelMenu.fxml");
        });
        return loadButton;
    }

    /**
     * Creates a delete button. Deletes the player profile when clicked.
     * @param name player name.
     * @return delete button.
     */
    private Button createDeleteButton(String name) {
        ImageView binImage =
                new ImageView(
                        Data.getUrl("images/bin.png").toExternalForm());
        binImage.setFitHeight(BIN_IMAGE_HEIGHT);
        binImage.setPreserveRatio(true);

        Button deleteButton = new Button();
        deleteButton.setGraphic(binImage);
        deleteButton.setOnMouseClicked(e -> {
            Data.removeProfile(name);
            loadFxml("fxml/loadGame.fxml");
        });

        return deleteButton;
    }
}

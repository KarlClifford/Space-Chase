package com.example.spacechase.controllers;

import com.example.spacechase.utils.Data;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class represents a controller for high score table
 * menu. It contains buttons where they can show high score
 * table for each level after clicking them.
 * @author Tristan Tsang
 * @version 1.0.2
 */
public class HighScoreTableController extends Controller {
    /**
     * Number limit of scores showing in table.
     */
    private static final int LIMIT = 10;
    /**
     * Opacity when level button is darken.
     * @see javafx.scene.control.Button
     */
    private static final double BUTTON_DARKEN_OPACITY = .5;
    /**
     * Font size of profile box.
     * @see javafx.scene.text.Font
     */
    private static final int PROFILE_FONT_SIZE = 12;
    /**
     * Font of profile box.
     * @see javafx.scene.text.Font
     */
    private static final String PROFILE_FONT = "neuropol x rg";
    /**
     * Spacing of a profile box.
     * @see javafx.scene.layout.HBox
     */
    private static final int PROFILE_BOX_SPACING = 20;
    /**
     * Spacing of a score box.
     */
    private static final int SCORE_BOX_SPACING = 10;
    /**
     * Container for level buttons.
     *
     * @see javafx.scene.layout.HBox
     */
    @FXML
    private HBox levelButtonContainer;
    /**
     * Container for high score tables.
     */
    @FXML
    private AnchorPane scoreBoxContainer;

    /**
     * Creates buttons for each level that shows high score
     * table.
     */
    @FXML
    private void initialize() {
        // Get the directory that contains all level files.
        File[] levelFiles = new File(Data.getUrl(Data.LEVELS_PATH)
                .getPath()
        ).listFiles();

        /*
         * If levels directory exists, create button and score
         * table for each level in the directory.
         */
        if (levelFiles != null) {
            /*
             * For each level in directory, create a button that
             * opens up a score board for that level.
             */
            for (File levelFile : levelFiles) {
                String levelFileName = levelFile.getName();
                String levelId = levelFileName.replaceAll("(.txt)", "");

                VBox scoreBox = createScoreBox(levelFileName);

                Button button = new Button();
                button.setText(levelId);
                button.setOnMouseClicked(e -> {
                    showNode(scoreBox);
                    brightenNode(button);
                });

                showNode(scoreBox);
                brightenNode(button);

                levelButtonContainer.getChildren().add(button);
                scoreBoxContainer.getChildren().add(scoreBox);
            }
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
     * Hides all other nodes and show this node.
     * @param node node to be shown.
     */
    private void showNode(Node node) {
        hideAllNodes();
        node.setVisible(true);
        node.setViewOrder(1);
    }

    /**
     * Hides all nodes within score box container.
     */
    private void hideAllNodes() {
        for (Node node : scoreBoxContainer.getChildren()) {
            node.setVisible(false);
            node.setViewOrder(0);
        }
    }

    /**
     * Darkens all nodes in level button container.
     */
    private void darkenAllNodes() {
        for (Node node : levelButtonContainer.getChildren()) {
            node.setOpacity(BUTTON_DARKEN_OPACITY);
        }
    }

    /**
     * Darkens all nodes and brighten this node.
     * @param node node to be brighten.
     */
    private void brightenNode(Node node) {
        darkenAllNodes();
        node.setOpacity(1);
    }

    /**
     * Creates a high score table for given level,
     * lists out profile names and their score in
     * descending order of scores.
     * @param levelFileName level file name of this score board.
     * @return a vBox of score box of level.
     */
    private VBox createScoreBox(String levelFileName) {
        System.out.println(levelFileName);
        VBox scoreBox = new VBox();
        scoreBox.setSpacing(SCORE_BOX_SPACING);

        Map<String, Integer> scores = new HashMap<>();

        String[] profiles = Data.getProfiles();

        /*
         * Check through each profile to see if is possible to add
         * profile to the high score table.
         */
        for (String name : profiles) {
            URL url = Data.getUrl(
                    String.format("%s/%s/%s",
                            Data.PROFILES_PATH,
                            name,
                            levelFileName));

            /*
             * If the player has unlocked that level,
             * read that level file.
             */
            if (url != null) {
                File file = new File(url.getPath());

                /*
                 * If the level file exists, create a HBox
                 * that contains a name label and score label
                 * and add it to the scores sorted map, so
                 * that they are sorted by the scores.
                 */
                if (file.exists()) {
                    int score = readScoreFromFile(file);

                    scores.put(name, score);
                }

            }
        }

        /*
         * Turn the map to a list and sort them by values in reverse order.
         */
        List<Map.Entry<String, Integer>> sortedList = new LinkedList<>(
                scores.entrySet());
        sortedList.sort(Entry.comparingByValue(Comparator.reverseOrder()));

        /*
         * Add each HBox to the score box with their ranking labels.
         */
        int n = 1;
        for (Map.Entry<String, Integer> entry : sortedList) {
            // Only the top profiles up to limit.
            if (n <= LIMIT) {
                String name = entry.getKey();
                int score = entry.getValue();

                HBox profileBox = createProfileBox(name, score);
                HBox hBox = new HBox(new Label(n + ". "), profileBox);
                scoreBox.getChildren().add(hBox);

                n++;
            }
        }

        return scoreBox;
    }

    /**
     * Create a profile box from name and score.
     * @param name name of profile.
     * @param score score of profile.
     * @return profile box of player.
     */
    private HBox createProfileBox(String name, int score) {
        Label nameLabel = new Label(name);
        Label scoreLabel = new Label(String.valueOf(score));

        nameLabel.setFont(
                Font.font(PROFILE_FONT, PROFILE_FONT_SIZE));
        scoreLabel.setFont(
                Font.font(PROFILE_FONT, PROFILE_FONT_SIZE));

        HBox profileBox = new HBox(nameLabel, scoreLabel);
        profileBox.setSpacing(PROFILE_BOX_SPACING);

        return profileBox;
    }

    /**
     * Reads the score from the level.
     * @param file file of a level.
     * @return score of that level. Returns -1 if
     * file is not found.
     */
    private int readScoreFromFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter("\n");

            String line = scanner.next();
            String[] split = line.split("\\s");
            String scoreInStr = split[split.length - 1];

            return Integer.parseInt(scoreInStr);
        } catch (FileNotFoundException e) {
            return -1;
        }
    }
}

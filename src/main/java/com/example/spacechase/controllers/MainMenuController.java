package com.example.spacechase.controllers;

import com.example.spacechase.utils.Data;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.time.Clock;
import java.util.Random;

/**
 * This class represents a controller for main menu.
 * This controller contains components of a new game button
 * and load game button.
 * @author Tristan Tsang
 * @version 1.0.1
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
    private static final String HIGH_SCORE_TABLE_PATH =
            "fxml/highScoreTable.fxml";
    /**
     * Fxml file path of settings.
     * @see javafx.fxml
     */
    private static final String SETTINGS_FXML_PATH = "fxml/settings.fxml";
    /**
     * Minimum time interval between each image to spawn.
     */
    private static final double MIN_SPAWN_TIME = 250;
    /**
     * Maximum time interval between each image to spawn.
     */
    private static final double MAX_SPAWN_TIME = 1500;
    /**
     * Minimum size of an image.
     */
    private static final double MIN_SPAWN_SIZE = 25;
    /**
     * Maximum size of an image.
     */
    private static final double MAX_SPAWN_SIZE = 100;
    /**
     * Minimum rotation speed of an image.
     */
    private static final double MIN_ROT_SPEED = 1000;
    /**
     * Maximum rotation speed of an image.
     */
    private static final double MAX_ROT_SPEED = 2000;
    /**
     * Minimum time for an image to stay in scene.
     */
    private static final double MIN_LIFE_TIME = 10000;
    /**
     * Maximum time for an image to stay in scene.
     */
    private static final double MAX_LIFE_TIME = 20000;
    /**
     * Maximum rotation of an image.
     */
    private static final int MAX_ROT = 360;
    /**
     * Opacity of an image.
     */
    private static final double IMAGE_OPACITY = .5;
    /**
     * Pane that contains all elements in scene.
     * @see javafx.scene.layout.AnchorPane
     */
    @FXML
    private AnchorPane pane;
    /**
     * Box that contains label and buttons of menu.
     * @see javafx.scene.layout.VBox
     */
    @FXML
    private VBox menuBox;

    /**
     * Anchors the menu box to center and
     * spawns images continuously.
     */
    @FXML
    private void initialize() {
        setAnchor(menuBox, 0.0);
        menuBox.setViewOrder(0);

        spawnImages();
    }

    /**
     * Continuously spawn different images in random positions,
     * images will spawn from the left side and roll towards
     * the right side of the scene.
     */
    private void spawnImages() {
        AnimationTimer timer = new AnimationTimer() {
            final Clock clock = java.time.Clock.systemDefaultZone();
            final Random random = new Random();
            private double last = clock.millis();
            private double interval = random.nextDouble(
                    MIN_SPAWN_TIME, MAX_SPAWN_TIME);
            @Override
            public void handle(long l) {
                double now = clock.millis();

                // Spawn a random image if it reaches the time interval.
                if (now - last >= interval) {
                    spawnImage();

                    last = now;
                    interval = random.nextDouble(
                            MIN_SPAWN_TIME, MAX_SPAWN_TIME);
                }
            }
        };

        timer.start();
    }

    /**
     * Spawns a random image in random position and random
     * rotation, moves to random position within random
     * duration.
     */
    private void spawnImage() {
        final Random random = new Random();
        final Stage stage = Controller.getStage();
        final double size = random.nextDouble(MIN_SPAWN_SIZE, MAX_SPAWN_SIZE);
        final double x0 = -size;
        final double x1 = stage.getWidth();
        final double y0 = random.nextDouble(0, stage.getHeight() - size);
        final double y1 = random.nextDouble(0, stage.getHeight() - size);
        final int startRot = random.nextInt(0, MAX_ROT);
        final int endRot = startRot + MAX_ROT;
        final double rotSpeed = random.nextDouble(
                MIN_ROT_SPEED, MAX_ROT_SPEED);
        final double moveSpeed = random.nextDouble(
                MIN_LIFE_TIME, MAX_LIFE_TIME);

        File[] images = Data.getFileFromPath("images").listFiles();

        // Return if directory is not found.
        if (images == null) {
            return;
        }

        // Gets a random image from directory.
        int index = random.nextInt(0, images.length - 1);
        File image = images[index];

        ImageView imageView = createImageView(
                image.getPath(),
                x0,
                y0,
                size,
                startRot);

        pane.getChildren().add(imageView);

        // Rotates the image forever.
        RotateTransition rotate = createRotateTransition(
                imageView, startRot, endRot);
        rotate.setDuration(Duration.millis(rotSpeed));
        rotate.play();

        // Moves the image from left to right in the screen.
        TranslateTransition translate = createTranslateTransition(
                imageView, x0, y0, x1, y1);
        translate.setDuration(Duration.millis(moveSpeed));
        translate.play();
        translate.setOnFinished(e -> {
            imageView.setOpacity(0);
            pane.getChildren().remove(imageView);
        });
    }

    /**
     * Creates an image view with given position, size, and rotation.
     * @param path path of the image.
     * @param x x position of the image.
     * @param y y position of the image.
     * @param size size of the image.
     * @param rot rotation of the image.
     * @return image view created from path.
     */
    private ImageView createImageView(
            String path, double x, double y, double size, int rot) {
        ImageView imageView = new ImageView(path);
        imageView.setRotate(rot);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        imageView.setViewOrder(1);
        imageView.setOpacity(IMAGE_OPACITY);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        return imageView;
    }

    /**
     * Creates a transition that rotates the node from rotation
     * to rotation and loops forever.
     * @param node node to perform transition in.
     * @param fromRot starting rotation of the node.
     * @param toRot ending rotation of the node.
     * @return transition of the node.
     */
    private RotateTransition createRotateTransition(
            Node node, double fromRot, double toRot) {
        RotateTransition rotate = new RotateTransition();
        rotate.setNode(node);
        rotate.setFromAngle(fromRot);
        rotate.setToAngle(toRot);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setCycleCount(-1);
        return rotate;
    }

    /**
     * Creates a transition of the node that moves from
     * point to point.
     * @param node node to perform transition in.
     * @param fromX x of starting position.
     * @param fromY y of starting position.
     * @param toX x of ending position.
     * @param toY y of ending position.
     * @return transition of the node.
     */
    private TranslateTransition createTranslateTransition(
            Node node, double fromX, double fromY, double toX, double toY) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(node);
        translate.setFromX(fromX);
        translate.setFromY(fromY);
        translate.setToX(toX);
        translate.setToY(toY);
        translate.setInterpolator(Interpolator.LINEAR);
        translate.setCycleCount(1);
        return translate;
    }

    /**
     * Anchors the given node in the scene with value of anchor.
     * @param node node to be anchored.
     * @param anchor value of anchor.
     */
    private void setAnchor(Node node) {
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
    }

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

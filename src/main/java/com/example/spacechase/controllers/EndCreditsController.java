package com.example.spacechase.controllers;

import com.example.spacechase.utils.Data;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;

/**
 * This class represents a controller for the end credits media.
 * It contains a button to exit back to the main menu.
 * @author Karl Clifford
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class EndCreditsController extends Controller {

    /**
     * The path to the end credits video.
     */
    public static final String MEDIA_PATH = "media/end-credits.mp4";
    /**
     * Media player to view the end credits.
     */
    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    /**
     * Controls the media playback.
     */
    private void start() {
        File file = Data.getFileFromPath(MEDIA_PATH);
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.onEndOfMediaProperty().addListener(e -> exit());
        mediaPlayer.play();
    }

    /**
     * Exits back to the main menu.
     */
    @FXML
    private void onInterrupted() {
        mediaPlayer.stop();
        exit();
    }

    /**
     * Loads the main menu fxml file.
     */
    private void exit() {
        loadFxml("fxml/mainMenu.fxml");
    }

    /**
     * Starts the media playback.
     */
    public void initialize() {
        start();
    }
}

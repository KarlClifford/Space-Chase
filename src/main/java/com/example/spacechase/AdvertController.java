package com.example.spacechase;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.util.Duration;
import java.io.File;
/**
 * AdvertController is responsible for controlling the
 * advert window generated by the advert.fxml file
 * to be able to display a video specified by the code.
 * @author Daniel Halsall.
 * @version 1.0.1.
 */
public class AdvertController extends Controller {
    /**
     * Variable holing a button object.
     */
    @FXML
    private Button button;
    /**
     * Variable holing a file.
     * @see javafx.scene.control.Button
     */
    private File file;
    /**
     * Variable holing a media object.
     */
    @FXML
    private Media media;
    /**
     * Variable holing a mediaView object.
     * @see javafx.scene.media.Media
     */
    @FXML
    private MediaView mediaView;
    /**
     * Variable holing a mediaPayer object.
     * @see javafx.scene.media.MediaView
     */
    @FXML
    private MediaPlayer mediaPlayer;
    /**
     * Integer variable representing the time
     * taken before the button will appear.
     * @see javafx.scene.media.MediaPlayer
     */
    private final int timer = 5;
    /**
     * Start will play a
     * video and load a button after a determined amount of time
     * to restart the level.
     * @param level the current level the player is on.
     */
    public void start(Level level) {
        file = new File(
                "src/main/resources/com/example/spacechase/Vid/vid.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new  MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        button.setVisible(false);
        PauseTransition pt = new PauseTransition(Duration.seconds(timer));
        pt.setOnFinished(e -> {
            button.setVisible(true);
            button.setOnMouseClicked(f -> level.restart());
        });
        pt.play();
    }
}

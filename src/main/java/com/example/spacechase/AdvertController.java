package com.example.spacechase;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.util.Duration;
import java.io.File;

public class AdvertController extends Controller{
    @FXML
    public Button button;
    @FXML
    private File file;
    @FXML
    private Media media;
    @FXML
    private MediaView mediaView;
    @FXML
    private MediaPlayer mediaPlayer;


    @FXML
    public void initialize() {
        file = new File("src/main/resources/com/example/spacechase/Vid/vid.mp4");
        media = new Media(file.toURI().toString());
        mediaPlayer = new  MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();
        button.setVisible(false);
        PauseTransition pt = new PauseTransition(Duration.seconds(5));
        pt.setOnFinished(e -> {
            button.setVisible(true);
        });
        pt.play();
    }

    @FXML
    protected void resetLevel() {
        Controller controller = new Controller();
        controller.loadFxml("fxml/levelMenu.fxml");
    }

}
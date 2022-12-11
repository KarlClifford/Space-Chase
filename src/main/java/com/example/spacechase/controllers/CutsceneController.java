package com.example.spacechase.controllers;

import com.example.spacechase.App;
import com.example.spacechase.models.Level;
import com.example.spacechase.models.level.GameClock;
import com.example.spacechase.services.SoundEngine;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.time.Clock;

/**
 * This class represents a controller for the cutscene screen.
 * Which is triggered by a player stepping on a note.
 * @author Tristan Tsang
 * @author Karl Clifford
 * @version 1.0.0
 */
public class CutsceneController extends Controller {
    /**
     * The volume of the music to be played on this screen.
     */
    private static final int SLOW_MUSIC_VOLUME = 40;
    /**
     * The speed of the music to be played on this screen.
     */
    private static final double SLOW_MUSIC_SPEED = 0.15;
    /**
     * Speed of printing each character of message out.
     */
    private static final double PRINT_SPEED = 100;
    /**
     * Label of the message.
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label messageLabel;
    /**
     * Button to resume the game or skips the printing
     * animation of the message.
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button continueButton;
    private Level level;
    /**
     * Message of this cutscene.
     */
    private String message;

    /**
     * Starts the cutscene.
     */
    public void start() {
        GameClock gameClock = level.getClock();
        gameClock.setRun(false);

        AnimationTimer timer = new AnimationTimer() {
            final Clock clock = Clock.systemDefaultZone();
            long last = clock.millis();

            @Override
            public void handle(long l) {
                long now = clock.millis();
                /*
                 * Stops timer when there is no more characters
                 * in the message.
                 */
                if (message.length() == 0) {
                    this.stop();
                    /*
                     * Prints out the first character in the
                     * message every 100 milliseconds, leaves
                     * the rest in message.
                     */
                } else if (now - last >= PRINT_SPEED) {
                    char c = message.charAt(0);
                    message = message.substring(1);
                    messageLabel.setText(messageLabel.getText() + c);
                    last = now;
                }
            }
        };

        timer.start();

        continueButton.setOnMouseClicked(e -> {
            /*
             * Stops the animation and shows full message
             * if message is still printing out. Otherwise,
             * ends cutscene and resumes the level.
             */
            if (!messageLabel.getText().equals(message)) {
                timer.stop();
                messageLabel.setText(message);
            } else {
                resumeMusic();
                gameClock.setRun(true);
                Scene scene = level.getScene();
                setScene(scene);
            }
        });
    }

    /**
     * Sets the message of the cutscene.
     * @param message message of the cutscene.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the level of the cutscene.
     * @param level level of the cutscene.
     */
    public void setLevel(Level level) {
        this.level = level;
    }


    /**
     * Adds a slowed music effect.
     */
    public void slowMusic() {
        SoundEngine.setMusicVolume(SLOW_MUSIC_VOLUME);
        App.MUSIC_PLAYER.setPlaybackSpeed(SLOW_MUSIC_SPEED);
    }

    /**
     * Resets the music back to default.
     */
    public void resumeMusic() {
        App.MUSIC_PLAYER.setPlaybackSpeed(SoundEngine.MUSIC_VOLUME);
        App.MUSIC_PLAYER.setPlaybackSpeed(1);
    }

    public void initialize() {
        System.out.println("init");
        slowMusic();

    }
}

package com.example.spacechase.controllers;

import com.example.spacechase.App;
import com.example.spacechase.models.Level;
import com.example.spacechase.models.level.GameClock;
import com.example.spacechase.services.SoundEngine;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
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
    private Button skipButton;
    /**
     * Stores the level of the game.
     */
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

        // Resumes the game if there's no message.
        if (message == null) {
            resumeMusic();
            gameClock.setRun(true);
            setRoot(level.getPane());
            return;
        }

        AnimationTimer timer = new AnimationTimer() {
            final Clock clock = Clock.systemDefaultZone();
            private long last = clock.millis();
            private String copy = message;

            @Override
            public void handle(long l) {
                long now = clock.millis();

                /*
                 * Stops timer when there is no more characters
                 * in the message.
                 */
                if (copy.length() == 0) {
                    this.stop();
                    /*
                     * Prints out the first character in the
                     * message every 100 milliseconds, leaves
                     * the rest in message.
                     */
                } else if (now - last >= PRINT_SPEED) {
                    // Prints a new line if character is '^'.
                    if (copy.charAt(0) == '^') {
                        copy = copy.substring(1);
                        messageLabel.setText(messageLabel.getText() + '\n');
                    } else {
                        char c = copy.charAt(0);

                        copy = copy.substring(1);
                        messageLabel.setText(messageLabel.getText() + c);
                        playTypingSound();
                        last = now;
                    }
                }
            }
        };

        timer.start();

        message = message.replaceAll("\\^", "\n");

        skipButton.setOnMouseClicked(e -> {
            /*
             * Stops the animation and shows full message
             * if message is still printing out. Otherwise,
             * ends cutscene and resumes the level.
             */
            if (!messageLabel.getText().equals(message)) {
                messageLabel.setText(message);
                timer.stop();
            } else {
                resumeMusic();
                gameClock.setRun(true);
                setRoot(level.getPane());
            }
        });
    }

    /**
     * Plays typing sound effect.
     */
    private void playTypingSound() {
        SoundEngine soundEngine = new SoundEngine();
        soundEngine.playSound(SoundEngine.Sound.CLICK,
                SoundEngine.getSoundEffectVolume(),
                false);
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
        SoundEngine.setMusicVolume(SoundEngine.getMusicVolume() / 2);
        App.MUSIC_PLAYER.setPlaybackSpeed(SLOW_MUSIC_SPEED);
    }

    /**
     * Resets the music back to default.
     */
    public void resumeMusic() {
        SoundEngine.setMusicVolume(SoundEngine.getMusicVolume() * 2);
        App.MUSIC_PLAYER.setPlaybackSpeed(1);
    }

    /**
     * Plays the slow music.
     */
    @FXML
    public void initialize() {
        slowMusic();
    }
}

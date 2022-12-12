package com.example.spacechase.controllers;

import com.example.spacechase.App;
import com.example.spacechase.services.GameMessage;
import com.example.spacechase.services.SoundEngine;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import java.io.IOException;
import java.time.Clock;

/**
 * Controls displaying the message of the day before launching the main menu.
 * @author Karl Clifford
 * @version 1.0.0
 */
public class MODController extends Controller {
    /**
     * Speed of printing each character of message out.
     */
    private static final double PRINT_SPEED = 20;
    /**
     * Sound FX volume.
     */
    private static final int FX_VOLUME = 5;
    /**
     * Time the game should pause for before switching screens.
     */
    private static final int PAUSE_DURATION = 7500;
    /**
     * Label of the message.
     * @see javafx.scene.control.Label
     */
    @FXML
    private Label messageLabel;
    /**
     * Button that skips motd.
     * @see javafx.scene.control.Button
     */
    @FXML
    private Button skipButton;
    /**
     * Message of this cutscene.
     */
    private String message;

    /**
     * Starts the cutscene.
     */
    public void start() {
        messageLabel.setTextOverrun(OverrunStyle.CLIP);
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setMaxWidth(stage.getWidth());
        messageLabel.setMaxHeight(stage.getHeight());

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
                    // Provide enough time to read the message.
                    if (now - last >= PAUSE_DURATION) {
                        this.stop();
                        switchToMainMenu();
                    }

                    /*
                     * Prints out the first character in the
                     * message every 100 milliseconds, leaves
                     * the rest in message.
                     */
                } else if (now - last >= PRINT_SPEED) {
                    // Prints new line if character is '^'.
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

        skipButton.setOnKeyPressed(e -> {
            timer.stop();
            /*
             * Stops the animation and shows full message
             * if message is still printing out. Otherwise,
             * ends printing and goes to main menu.
             */
            if (!messageLabel.getText().equals(message)) {
                messageLabel.setText(message);
            } else {
                switchToMainMenu();
            }
        });
    }

    /**
     * Goes to main menu.
     */
    private void switchToMainMenu() {
        // Plays menu music.
        App.MUSIC_PLAYER.playSound(
                SoundEngine.Sound.MENU_MUSIC,
                SoundEngine.getMusicVolume(),
                true);
        // Switches to the main menu.
        loadFxml("fxml/mainMenu.fxml");
    }

    /**
     * Plays typing sound effect.
     */
    private void playTypingSound() {
        SoundEngine soundEngine = new SoundEngine();
        soundEngine.playSound(SoundEngine.Sound.CLICK,
                FX_VOLUME,
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
     * Plays the slow music.
     */
    @FXML
    public void initialize() {
        String messageOfTheDay;
        // Try to retrieve the message of the day.
        try {
            messageOfTheDay = GameMessage.fetch();
        } catch (IOException e) {
            // The user isn't connected to the internet.
            messageOfTheDay = ("Error: Couldn't get the message of the day. "
                    + "Are you connected to the internet?");
            // The user disconnected from the internet while fetching the MOTD.
        } catch (InterruptedException e) {
            messageOfTheDay = ("Error: Couldn't get the message of the day.");
        }

        String message = "DATE: [??:??:????]\nSENDER: [UNKNOWN]\n\n---------"
                + " START MESSAGE ---------\n\nGreetings, space traveler.\nMy"
                + " knowledge and understanding is vast.\nI see your journey "
                + "and applaud your bravery.\nMay your travels be safe and en"
                + "lightening\nAlways remember:\n\n" + messageOfTheDay + "\n"
                + "\nIn the vastness of space the only thing left is the Cha"
                + "se.\nBe safe traveller.\n\n--------- END MESSAGE ---------";

        setMessage(message);
        start();
    }
}

package com.example.spacechase.controllers;

import com.example.spacechase.services.GameMessage;
import com.example.spacechase.services.SoundEngine;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
     * Message of this cutscene.
     */
    private String message;

    /**
     * Starts the cutscene.
     */
    public void start() {

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

                    // Provide enough time to read the message.
                    try {
                        Thread.sleep(PAUSE_DURATION);
                    } catch (InterruptedException e) {
                        // The operation was cancelled.
                        throw new RuntimeException(e);
                    }
                    // Switch to the main menu.
                    loadFxml("fxml/mainMenu.fxml");
                    /*
                     * Prints out the first character in the
                     * message every 100 milliseconds, leaves
                     * the rest in message.
                     */
                } else if (now - last >= PRINT_SPEED) {
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

        String message = "DATE: [??:??:????]\nSENDER: [UNKNOWN]\n\n--------- START MESSAGE ---------\n\nGreetings, space traveler.\nMy knowledge and understanding is vast.\nI see your journey and applaud your bravery.\nMay your travels be safe and enlightening\nAlways remember:\n\n" + messageOfTheDay + "\n\nIn the vastness of space the only thing left is the Chase.\nBe safe traveller.\n\n--------- END MESSAGE ---------";

        setMessage(message);
        start();
    }
}

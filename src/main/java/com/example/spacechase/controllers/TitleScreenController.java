package com.example.spacechase.controllers;

import com.example.spacechase.services.GameMessage;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;

public class TitleScreenController extends Controller {
    /**
     * Duration for fade in transition of the label.
     */
    private static final double FADE_IN_DURATION = 3000.0;
    /**
     * Duration for fade out transition of the label.
     */
    private static final double FADE_OUT_DURATION = 1000.0;
    /**
     * In value of fade of the label.
     */
    private static final double FADE_IN = 10;
    /**
     * Out value of fade of the label.
     */
    private static final double FADE_OUT = .01;
    /**
     * Box that contains title labels.
     * @see javafx.scene.layout.VBox
     */
    @FXML
    private VBox titleLabel;
    @FXML
    private Label message;

    /**
     * Fades and zooms in the title label.
     */
    @FXML
    private void initialize() {
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
        message.setText(messageOfTheDay);

        fadeInTitle();
    }

    /**
     * Fades in the title.
     */
    private void fadeInTitle() {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setDuration(Duration.millis(FADE_IN_DURATION));
        fadeIn.setFromValue(FADE_OUT);
        fadeIn.setToValue(FADE_IN);
        fadeIn.setCycleCount(1);
        fadeIn.setNode(titleLabel);
        fadeIn.play();
        fadeIn.setOnFinished(e -> {
            try {
                /*
                 * Pause the fade animation to give enough opportunity to
                 * read the game message.
                 */
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                // The sleep operation was cancelled.
                throw new RuntimeException(ex);
            }
            fadeOutTitle();
        });
    }

    /**
     * Fades out the title.
     */
    private void fadeOutTitle() {
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setDuration(Duration.millis(FADE_OUT_DURATION));
        fadeOut.setFromValue(FADE_IN);
        fadeOut.setToValue(FADE_OUT);
        fadeOut.setCycleCount(1);
        fadeOut.setNode(titleLabel);
        fadeOut.play();
        fadeOut.setOnFinished(e -> loadFxml("fxml/mainMenu.fxml"));
    }
}

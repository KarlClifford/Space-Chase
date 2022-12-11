package com.example.spacechase.controllers;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Title screen controller, handles loading and displaying the title screen.
 * @author Tristan Tsang
 * @version 1.0.0
 */
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
     * Duration for title staying on the scene.
     */
    private static final long TITLE_DURATION = 2000L;
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

    /**
     * Fades and zooms in the title label.
     */
    @FXML
    private void initialize() {

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
                Thread.sleep(TITLE_DURATION);
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
        fadeOut.setOnFinished(e -> loadFxml("fxml/modScreen.fxml"));
    }
}

package com.example.spacechase;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class AdvertController extends  Controller {
    @FXML
    public Button button;

    @FXML
    public void initialize() {
        button.setVisible(false);
        PauseTransition pt = new PauseTransition(Duration.seconds(5));
        pt.setOnFinished(e -> {
            button.setVisible(true);
        });
        pt.play();
    }

    @FXML
    protected void resetLevel() {
        System.out.println("restart the level");
        Controller controller = new Controller();
        controller.loadFxml("fxml/levelMenu.fxml");
        //GameState.restart()
    }

}
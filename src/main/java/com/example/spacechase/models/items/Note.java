package com.example.spacechase.models.items;

import com.example.spacechase.controllers.Controller;
import com.example.spacechase.controllers.CutsceneController;
import com.example.spacechase.models.characters.Collector;
import com.example.spacechase.models.characters.Player;

/**
 * This class represents a note item which can be collected by the user to
 * reveal a message.
 * @author Tristan Tsang
 * @author Karl Clifford
 * @version 1.0.0
 */
public class Note extends Item {
    /**
     * Message of the note.
     */
    private String message;

    /**
     * Creates a note item that can display message.
     */
    public Note() {
        this.id = 'N';
        this.imagePath = "SDcard.gif";
    }

    /**
     * Sets the message of the note.
     * @param message message of the note.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the message of the note.
     * @return message of note.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param collector collector of the item.
     */
    @Override
    public void interact(Collector collector) {
        // Goes to cutscene if is a player.
        if (collector instanceof Player) {
            this.remove();

            CutsceneController controller = (CutsceneController)
                    new Controller()
                    .loadFxml("fxml/noteScreen.fxml");
            controller.setMessage(message);
            controller.setLevel(level);
            controller.start();
        }
    }
}

package com.example.spacechase.models.characters;

import com.example.spacechase.models.items.Gate;
import com.example.spacechase.models.level.Tile;
import com.example.spacechase.utils.Control;
import com.example.spacechase.utils.Direction;
import com.example.spacechase.services.SoundEngine;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.HashMap;

/**
 * This class represents a player.
 * A player contains components of direction and all shared
 * components from character.
 * @author Tristan Tsang
 * @author Karl Clifford
 * @author Alex Hallsworth
 * @author Ben Thornber
 * @version 1.0.1
 */
public class Player extends Collector {
    /**
     * Keybinds for all controls.
     */
    private static final HashMap<KeyCode, Control> KEYBINDS = new HashMap<>();
    static {
        KEYBINDS.put(KeyCode.W, Control.UP);
        KEYBINDS.put(KeyCode.A, Control.LEFT);
        KEYBINDS.put(KeyCode.D, Control.RIGHT);
        KEYBINDS.put(KeyCode.S, Control.DOWN);
    }
    /**
     * Direction of the player.
     */
    private Direction direction;
    /**
     * Creates a Player instance.
     */
    public Player() {
        this.id = 'P';
        this.imagePath = "AstronautGif.gif";
    }

    /**
     * Removes the character and trigger end game.
     */
    @Override
    public void remove() {
        super.remove();
        level.end(false);
    }

    /**
     * Initializes input for the level. Changes direction depending
     * on the input.
     */
    public void initialize() {
        Scene scene = level.getPane().getScene();
        scene.setOnKeyPressed(keyEvent -> {
            Control control =
                    KEYBINDS.getOrDefault(keyEvent.getCode(), Control.NULL);

            // Changes direction if there's an input from control.
            direction = switch (control) {
                case UP -> Direction.UP;
                case LEFT -> Direction.LEFT;
                case DOWN -> Direction.DOWN;
                case RIGHT -> Direction.RIGHT;
                default -> null;
            };
        });
    }

    /**
     * Changes the keybind of control.
     * @param control control of the player.
     * @param keyCode key code of the control.
     */
    public static void setKeybind(Control control, KeyCode keyCode) {
        KEYBINDS.put(keyCode, control);
    }

    /**
     * Plays the sound effect of player movement.
     */
    private void playMoveSound() {
        // Initialise a new sound engine, so we can play a sound effect.
        SoundEngine soundEngine = new SoundEngine();
        // Play the move sound effect.
        soundEngine.playSound(
                SoundEngine.Sound.MOVE,
                SoundEngine.getSoundEffectVolume(),
                false);
    }


    /**
     * Changes the tile of the character if player
     * is trying to move towards a direction.
     */
    @Override
    void move() {
        /* If player has direction from input,
        move towards that direction if possible. */
        if (direction != null) {
            Tile link = tile.getLinkedTile(direction);
            /* Change tile of the player to link of current tile
            in player direction if link exists and has no
            character on it and has no gate on it */
            if (link != null && link.getCharacter() == null
                && !(link.getItem() instanceof Gate)) {
                changeTile(link);
                playMoveSound();
            }
        }

        direction = null;
    }
}

package com.example.spacechase.models.characters;

import com.example.spacechase.models.items.Gate;
import com.example.spacechase.models.level.Tile;
import com.example.spacechase.utils.Direction;
import javafx.scene.Scene;

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
     * @param scene scene of the level.
     */
    public void initialize(Scene scene) {
        scene.setOnKeyPressed(keyEvent ->
                direction = switch (keyEvent.getCode()) {
                    case W -> Direction.UP;
                    case A -> Direction.LEFT;
                    case S -> Direction.DOWN;
                    case D -> Direction.RIGHT;
                    default -> null;
                }
        );
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
                SoundEngine.SOUND_EFFECT_VOLUME,
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

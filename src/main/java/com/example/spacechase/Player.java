package com.example.spacechase;

import javafx.scene.Scene;

/**
 * This class represents a player.
 * A player contains components of direction and all shared
 * components from character.
 * @author Tristan Tsang
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
            character on it. */
            if (link != null && link.getCharacter() == null) {
                changeTile(link);
            }
        }

        direction = null;
    }
}
package com.example.spacechase.models.characters;

import com.example.spacechase.models.level.Entity;
import com.example.spacechase.models.Level;
import com.example.spacechase.models.level.Tile;

/**
 * This abstract class represents a character. A character contains
 * components of id, tile, image, level.
 * @author Tristan Tsang
 * @version 1.0.1
 */
public abstract class Character extends Entity {
    /**
     * Changes the current tile to the new tile.
     * @param link the tile to move on to.
     */
    protected void changeTile(Tile link) {
        tile.setCharacter(null);
        link.setCharacter(this);
        tile = link;
    }

    /**
     * Removes the character from the level.
     */
    protected void remove() {
        level.removeCharacter(this);
        tile.setCharacter(null);
        imageView.setOpacity(0);
    }

    /**
     * Draws the image of the character to the center of the tile.
     */
    public void draw() {
        imageView.relocate(
                (Tile.TILE_SIZE + Level.TILE_SPACING) * tile.getX()
                        + Tile.TILE_SIZE / 2 / 2,
                Level.CANVAS_OFFSET_X
                        + (Tile.TILE_SIZE + Level.TILE_SPACING) * tile.getY()
                        + Tile.TILE_SIZE / 2 / 2);
    }

    /**
     * Movement of a character.
     */
    abstract void move();

    /**
     * Moves the character and draws it.
     */
    public void update() {
        move();
        draw();
    }
}

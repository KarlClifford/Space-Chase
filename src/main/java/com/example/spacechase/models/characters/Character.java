package com.example.spacechase.models.characters;

import com.example.spacechase.models.level.Entity;
import com.example.spacechase.models.Level;
import com.example.spacechase.models.level.Tile;

/**
 * This abstract class represents a character. A character contains
 * components of id, tile, image, level.
 * @author Tristan Tsang
 * @author Alex Hallsworth
 * @author Ben Thornber
 * @version 1.0.2
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

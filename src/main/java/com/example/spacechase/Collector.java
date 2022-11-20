package com.example.spacechase;

/**
 * This class represents a collector. A collector contains
 * components of all shared components from character.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public abstract class Collector extends Character {
    /**
     * Collector picks up item from the tile.
     */
    @Override
    protected void update() {
        super.update();

        Item item = tile.getItem();
        if (item != null) {
            item.interact(this);
        }
    }
}

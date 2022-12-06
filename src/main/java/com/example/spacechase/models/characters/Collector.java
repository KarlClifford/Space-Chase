package com.example.spacechase.models.characters;

import com.example.spacechase.models.items.Item;

/**
 * This class represents a collector. A collector handles
 * the pickup process for in game items.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public abstract class Collector extends Character {
    /**
     * Collector picks up item from the tile.
     */
    @Override
    public void update() {
        super.update();

        Item item = tile.getItem();
        // If there is an item, interact with it.
        if (item != null) {
            item.interact(this);
        }
    }
}

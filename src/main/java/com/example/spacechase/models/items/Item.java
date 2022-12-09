package com.example.spacechase.models.items;

import com.example.spacechase.models.characters.Collector;
import com.example.spacechase.models.level.Entity;

/**
 * This abstract class represents an item. An item contains
 * components of id, tile, level and image.
 * @author Tristan Tsang
 * @author Ben Thornber
 * @version 1.0.2
 */
public abstract class Item extends Entity {
    /**
     * Removes the item from level as it is collected.
     * @param collector collector of the item.
     */
    public void interact(Collector collector) {
        level.removeItem(this);
        tile.setItem(null);
        imageView.setOpacity(0);
    }
}

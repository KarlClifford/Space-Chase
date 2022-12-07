package com.example.spacechase.models.items;

import com.example.spacechase.models.characters.Collector;
import com.example.spacechase.models.characters.Player;

/**
 * This class represents a door. A door is an item that allows
 * player to finish a level, or fail a level if an enemy interact
 * with it.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class Door extends Item {
    /**
     * Creates a door object.
     */
    public Door() {
        this.id = 'D';
        this.imagePath = "door.png";
    }

    /**
     * Begins the end game procedure.
     * @param collector collector that reached this door.
     */
    @Override
    public void interact(Collector collector) {
        boolean haveItemsInMap = level.getItems()
                .stream()
                .anyMatch(item -> !(item instanceof Door));

        // End the level if there is no other items in the map.
        if (!haveItemsInMap) {
            super.interact(collector);
            level.end(collector instanceof Player);
        }
    }
}

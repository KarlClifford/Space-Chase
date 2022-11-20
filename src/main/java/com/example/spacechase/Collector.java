package com.example.spacechase;

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

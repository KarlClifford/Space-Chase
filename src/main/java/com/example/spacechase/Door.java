package com.example.spacechase;

/**
 * This class represents a door. A door contains
 * components of all components shared from item.
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
     * Ends the game, stops the time, and creates an end level menu.
     * @param collector collector of this door.
     */
    @Override
    public void interact(Collector collector) {
        super.interact(collector);
        GameClock clock = level.getClock();
        clock.setRun(false);

        //Data.saveLevel(level);

        GameState state = level.getState();
        state.createEndLevelMenu(collector instanceof Player);
    }
}

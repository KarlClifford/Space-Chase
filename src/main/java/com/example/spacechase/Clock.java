package com.example.spacechase;

/**
 * This class represents a clock. A clock contains components of
 * a value and same components shared with item.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class Clock extends Item {
    /**
     * Time value of the clock.
     */
    private static final double VALUE = 30.0;

    /**
     * Creates a clock item.
     */
    public Clock() {
        this.id = '@';
        this.imagePath = "clock.png";
    }

    /**
     * Changes the game time when clock is picked up.
     * @param collector collector of this clock.
     */
    @Override
    public void interact(Collector collector) {
        super.interact(collector);

        double time = level.getTime();

        if (collector instanceof Player) {
            level.setTime(time + VALUE);
        } else {
            level.setTime(time - VALUE);
        }
    }
}

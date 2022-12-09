package com.example.spacechase.models.items;

import com.example.spacechase.models.characters.Collector;
import com.example.spacechase.models.characters.Player;

/**
 * This class represents a clock.
 * A clock keeps track of the remaining time left while playing the game.
 * @author Tristan Tsang
 * @author Ben Thornber
 * @version 1.0.1
 */
public class Clock extends Item {
    /**
     * Amount of time that the game clock will change after clock is interacted.
     */
    private static final double VALUE = 30.0;

    /**
     * Creates a clock item.
     */
    public Clock() {
        this.id = '@';
        this.imagePath = "OxygenTank.gif";
    }

    /**
     * Increases the game time if a player picks up the clock,
     * decreases the game time if an enemy picks up the clock.
     * @param collector collector of this clock.
     */
    @Override
    public void interact(Collector collector) {
        super.interact(collector);

        double time = level.getTime();

        /* If a collector is an instance of player, increase the game time.
         Otherwise, decrease the game time. */
        if (collector instanceof Player) {
            level.setTime(time + VALUE);
        } else {
            level.setTime(time - VALUE);
        }
    }
}

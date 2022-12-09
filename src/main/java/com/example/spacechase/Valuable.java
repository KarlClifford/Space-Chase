package com.example.spacechase;

/**
 * Abstract class which models all the different
 * scoring items available in the game.
 * This class represents Score, as well as the
 * interact method to increase the score counter.
 * @author Jamie Quinn
 *
 */
public abstract class Valuable extends Item {
    /**
     * Increases the score counter if the player picks
     * up the valuable item.
     * @param collector collector of the item.
     */
    @Override
    public void interact(Collector collector) {
        super.interact(collector);

        int score = level.getScore();

        /* if the player collects the item, increase the score counter.*/
        if (collector instanceof Player) {
            level.setScore(score + score);
        }
    }
}

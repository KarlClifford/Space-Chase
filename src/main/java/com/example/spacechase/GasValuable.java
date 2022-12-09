package com.example.spacechase;

/**
 * Class that models the gas canister, which is the
 * highest scoring item. Worth 20 points.
 * @author Jamie Quinn
 * @version 1.0.0
 */
public class GasValuable extends Valuable {
    /**
     * The score value of the gas item that will increase the
     * score counter after it is interacted with.
     */
    private static final int SCORE = 20;

    /**
     * Creates a new instance of the item.
     */
    public GasValuable() {
        this.id = 'G';
        this.imagePath = "gas.png";
    }

    /**
     * Increases the score counter if the player
     * object interacts with it.
     * @param collector collector of the item.
     */
    public void interact(Collector collector) {
        super.interact(collector);

        int score = level.getScore();

        /* if the player collects the item, increase the score counter.*/
        if (collector instanceof Player) {
            level.setScore(score + SCORE);
        }
    }
}

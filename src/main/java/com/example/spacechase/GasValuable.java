package com.example.spacechase;

/**
 * Class that models the gas canister, which is the
 * highest scoring item. Worth 20 points.
 * @author Jamie Quinn
 * @version 1.0.0
 */
public class GasValuable extends Valuable {
    
    /**
     * Creates a new instance of the item.
     */
    public GasValuable() {
        this.id = 'G';
        //TODO: check if image filename matches.
        this.imagePath = "gascan.png";
        this.score = 20;
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
            level.setScore(score + score);
        }
    }
}

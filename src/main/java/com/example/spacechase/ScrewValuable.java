package com.example.spacechase;

/**
 * Class that models the screw, which is the
 * lowest scoring item. Worth 5 points.
 * @author Jamie Quinn
 * @version 1.0.0
 */
public class ScrewValuable extends Valuable {

    /**
     * Creates a new instance of the item.
     */
    public ScrewValuable() {
        this.id = '+';
        //TODO: check if image filename matches.
        this.imagePath = "screw.png";
        this.score = 5;
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

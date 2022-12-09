package com.example.spacechase;

/**
 * Class that models the screwdriver, which is the
 * 2nd lowest scoring item. Worth 10 points.
 * @author Jamie Quinn
 * @version 1.0.0
 */
public class ScrewdriverValuable extends Valuable {
    /**
     * The score value of the screwdriver item that will increase
     * the score counter after it is interacted with.
     */
    private static final int SCORE = 10;
    /**
     * Creates a new instance of the item.
     */
    public ScrewdriverValuable() {
        this.id = 'Y';
        this.imagePath = "screwdriver.png";
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

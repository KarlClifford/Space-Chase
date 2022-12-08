package com.example.spacechase;

/**
 * Class that models the Toolbox, which is the
 * 2nd highest scoring item. Worth 15 points.
 * @author Jamie Quinn
 * @version 1.0.0
 */
public class ToolboxValuable extends Valuable {

    /**
     * Creates a new instance of the item.
     */
    public ToolboxValuable() {
        this.id = 'T';
        //TODO: check if image filename matches.
        this.imagePath = "toolbox.png";
        this.score = 15;
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

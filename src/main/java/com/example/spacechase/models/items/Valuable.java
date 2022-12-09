package com.example.spacechase.models.items;

import com.example.spacechase.models.characters.Collector;
import com.example.spacechase.models.characters.Player;

import java.util.HashMap;

/**
 * Class which models all the different
 * scoring items available in the game.
 * This class represents the score attributes/values
 * and relevant image paths for each item as
 * well as a method to increase the score counter.
 * @author Jamie Quinn
 * @version 1.0.0
 *
 */
public class Valuable extends Item {
    /**
     * Holds the screw item score value.
     */
    private static final int SCREW_VALUE = 5;
    /**
     * Holds the screwdriver item score value.
     */
    private static final int SCREWDRIVER_VALUE = 10;
    /**
     * Holds the toolbox item score value.
     */
    private static final int TOOLBOX_VALUE = 15;
    /**
     * Holds the gas item score value.
     */
    private static final int GAS_VALUE = 20;
    /**
     * Hashmap which contains the item identifying character as well as the
     * score value identified with it.
     */
    private static final HashMap<java.lang.Character, Integer>
            ITEM_SCORES = new HashMap<>();
    static {
        ITEM_SCORES.put('+', SCREW_VALUE);
        ITEM_SCORES.put('Y', SCREWDRIVER_VALUE);
        ITEM_SCORES.put('T', TOOLBOX_VALUE);
        ITEM_SCORES.put('G', GAS_VALUE);
    }
    /**
     * Hashmap which associates each item by its image filename.
     */
    private static final HashMap<java.lang.Character, String>
            ITEM_IMAGES = new HashMap<>();
    static {
        ITEM_IMAGES.put('+', "screw.png");
        ITEM_IMAGES.put('Y', "screwdriver.png");
        ITEM_IMAGES.put('T', "toolbox.png");
        ITEM_IMAGES.put('G', "gas.png");
    }
    /**
     * This represents the score value of each item.
     */
    private final int score;

    /**
     * Constructor that will build each item type and
     * assign its appropriate score value and image path.
     * @param itemType the identifying character for
     *                 each item.
     */
    public Valuable(char itemType) {
        this.score = ITEM_SCORES.get(itemType);
        this.imagePath = ITEM_IMAGES.get(itemType);
    }
    /**
     * Increases the score counter if the player picks
     * up the valuable item.
     * @param collector collector of the item.
     */
    @Override
    public void interact(Collector collector) {
        super.interact(collector);

        int currentScore = level.getScore();

        /*If the player collects the item, increase the score counter.*/
        if (collector instanceof Player) {
            level.setScore(currentScore + score);
        }
    }
}

package com.example.spacechase;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.time.Clock;
import java.util.Objects;

/**
 * This class represents a bomb. It can be triggered
 * by any collector and explode after counting down
 * 3 seconds.
 * @author Rami Abdulrazzaq
 * @version 1.0.0
 */
public class Bomb extends Item {
    /**
     * Duration of explosion animation in milliseconds.
     */
    private static final int EXPLOSION_TIME = 500;
    /**
     * Boolean variable to check if the bomb is triggered.
     */
    private boolean isTriggered;
    /**
     * Variable initTime(initial time) stores the time interval
     * when bomb was triggered.
     */
    private long initTime;
    /**
     * Variable boolean that determines
     * whether a bomb is detonated or not.
     */
    private boolean isDetonated;

    /**
     * Creates a bomb
     * and each instance of bomb holds the triggers
     * and the linkedList of items that are on the
     * same row and column of the bomb.
     */
    public Bomb() {
        this.id = '*';
        this.imagePath = "blackHole.gif";

    }

    /**
     * Method that gets the items(except doors and gates)
     * Vertical and Horizontal to the bomb and destroys them.
     */
    public void destroyItems() {
        int bombX = tile.getX();
        int bombY = tile.getY();
        Tile[][] tiles = level.getTileMap();
        int yMax = tiles.length;
        int xMax = tiles[0].length;
        // Loops from x coordinate 0 to x coordinate Max.
        for (int x = 0; x < xMax; x++) {
            destroyItem(x, bombY);
        }

        // Destroy every item the bomb comes into contact with.
        for (int y = 0; y < yMax; y++) {
            destroyItem(bombX, y);
        }
        this.remove();
    }

    /**
     * Destroys an item in given position.
     * @param x x position of an item.
     * @param y y position of an item.
     */
    private void destroyItem(int x, int y) {
        Image explosion = createImage("images/blackHoleexplosion.gif");

        Tile[][] tiles = level.getTileMap();
        Tile tile = tiles[y][x];
        Item item = tile.getItem();

        // Check that there is an item to destroy.
        if (item != null
                && !(item instanceof Bomb)
                && !(item instanceof Door)
                && !(item instanceof Gate)) {
            ImageView itemImage = item.getImageView();
            itemImage.setImage(explosion);

            AnimationTimer timer = new AnimationTimer() {
                final Clock clock = Clock.systemDefaultZone();
                final long last = clock.millis();
                @Override
                public void handle(long l) {
                    long now = clock.millis();

                    /*
                     * Check that we have past the time threshold to destroy
                     * the item.
                     */
                    if (now - last >= EXPLOSION_TIME) {
                        item.remove();
                        this.stop();
                    }
                }
            };

            timer.start();
            /*
             * Item is a bomb so should be immediately destroyed
             * with no countdown.
             */
        } else if (item instanceof Bomb bomb
                && bomb != this
                && !(bomb.isDetonated)) {
            imageView.setImage(explosion);

            AnimationTimer timer = new AnimationTimer() {
                final Clock clock = Clock.systemDefaultZone();
                final long last = clock.millis();
                @Override
                public void handle(long l) {
                    long now = clock.millis();

                    /*
                     * Check that we have past the time threshold to destroy
                     * the bomb.
                     */
                    if (now - last >= EXPLOSION_TIME) {
                        bomb.setIsDetonated();
                        bomb.destroyItems();
                        this.stop();
                    }
                }
            };

            timer.start();
        }
    }

    /**
     * Plays the sound effect of explosion.
     */
    private void playDestroySound() {
        SoundEngine soundEngine = new SoundEngine();
        soundEngine.playSound(
                SoundEngine.Sound.DESTROY,
                SoundEngine.SOUND_EFFECT_VOLUME,
                false);
    }

    /**
     * Creates an image from the path.
     * @param path path of image.
     * @return image.
     */
    private Image createImage(String path) {
        return new Image(
                Objects.requireNonNull(
                                this.getClass()
                                        .getResource(path))
                        .toExternalForm());
    }


    /**
     * Checks if a character is on any of
     * the tiles a block away from the bomb.
     * @return true if the bomb should be triggered.
     */
    public boolean canTrigger() {
        boolean trigger = false;
        /* Loops through the trigger tiles
        that are one block away from
        the bomb and checks if a character
        is on the trigger tile if(true) then
        trigger the bomb.
         */
        for (Direction direction : Direction.values()) {
            Tile neighbourTile = tile.getNeighbourTile(direction);
            if (neighbourTile != null
                    && neighbourTile
                    .getCharacter() != null) {
                trigger = true;
            }
        }
        return trigger;
    }


    /**
     * Checks if the bomb has been triggered.
     * @return getter for the variable isTriggered.
     */
    public boolean getIsTriggered() {
        return this.isTriggered;
    }

    /**
     * Method that triggers our bomb
     * by setting the triggered variable to true.
     * @param currentTime time the bomb is triggered.
     */
    public void trigger(long currentTime) {
        Image countdownImage = createImage("images/blackHolecounting.gif");
        imageView.setImage(countdownImage);
        initTime = currentTime;
        this.isTriggered = true;
    }

    /**
     * Gets the initial time interval.
     * @return the initial time interval.
     */
    public long getInitTime() {
        return initTime;
    }

    /**
     * Sets the initial time for the initial time interval.
     * @param initTime Setter for initial time.
     */
    public void setInitTime(long initTime) {
        this.initTime = initTime;
    }

    /**
     * Checks if the bomb has finished exploding.
     * @return whether a bomb has been detonated or not.
     */
    public boolean getIsDetonated() {
        return isDetonated;
    }

    /**
     * Sets the bomb to detonate by changing the isDetonated variable to true.
     */
    public void setIsDetonated() {
        isDetonated = true;
    }
}


package com.example.spacechase;

/**
 * @author Rami Abdulrazzaq
 * @version 1.0.0
 */
public class Bomb extends Item {

    /**
     * Boolean variable to check if the bomb is triggered.
     */
    private boolean isTriggered;
    /**
     * Variable initTime(initial time).
     * stores the time interval
     * Bomb was triggered
     */
    private long initTime;
    /**
     * Variable lastTime to store the time
     * Of the last tick of Bomb.
     */
    private long lastTime;
    /**
     * Variable boolean that determines
     * whether a bomb is detonated or not.
     */
    private boolean isDetonated;

    /**
     * Creates a bomb.
     * And each instance of bomb holds the triggers
     * And the linkedList of items that are on te
     * Same row and column of the bomb.
     */

    public Bomb() {
        this.id = '*';
        this.imagePath = "blackHole.png";

    }

    /**
     * Method that gets the items(except doors and gates)
     * Vertical and Horizontal to the bomb and destroys them.
     */
    public void destroyItems() {
        int bombX = this.getTile().getX();
        int bombY = this.getTile().getY();
        Tile[][] tiles = level.getTileMap();
        int yMax = tiles.length;
        int xMax = tiles[0].length;
        //loops from x0 to xMax
        for (int x = 0; x < xMax; x++) {
            Tile tileX = tiles[bombY][x];
            Item item = tileX.getItem();
            if (item != null
                    && !(item instanceof Bomb)
                    && !(item instanceof Door)
                    && !(item instanceof Gate)) {
                item.remove();
            } else if (item instanceof Bomb bomb
                    && bomb != this
                    && !(bomb.isDetonated)) {
                bomb.setIsDetonated();
                bomb.destroyItems();
            }
        }
        for (int y = 0; y < yMax; y++) {
            Tile tileY = tiles[y][bombX];
            Item item = tileY.getItem();
            if (item != null
                    && !(item instanceof Bomb)
                    && !(item instanceof Door)
                    && !(item instanceof Gate)) {
                item.remove();
            } else if (item instanceof Bomb bomb
                    && bomb != this
                    && !(bomb.isDetonated)) {
                bomb.setIsDetonated();
                bomb.destroyItems();
            }
        }
        this.remove();
    }


    /**
     * checks if a character is on any of
     * the tiles a block away from the bomb.
     *
     * @return boolean if bomb should be triggered
     */
    public boolean canTrigger() {
        boolean trigger = false;
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
        initTime = lastTime = currentTime;
        this.isTriggered = true;
    }

    /**
     * @return initTime
     */
    public long getInitTime() {
        return initTime;
    }

    /**
     * @return lastTime
     */
    public long getLastTime() {
        return lastTime;
    }

    /**
     * @param initTime Setter for initial time.
     */
    public void setInitTime(long initTime) {
        this.initTime = initTime;
    }

    /**
     * @param lastTime Setter for last time.
     */
    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * @return isDetonated
     */
    public boolean getIsDetonated() {
        return isDetonated;
    }

    /**
     * sets isDetonated to true.
     */
    public void setIsDetonated() {
        isDetonated = true;
    }
}
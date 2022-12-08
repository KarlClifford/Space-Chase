package com.example.spacechase;

import java.util.LinkedList;

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
     * Tile variable that refers to the tile above the bomb.
     */
    private final Tile bombTriggerUp;
    /**
     * Tile variable that refers to the tile below the bomb.
     */
    private final Tile bombTriggerDown;
    /**
     * Tile variable that refers to the tile left to the bomb.
     */
    private final Tile bombTriggerLeft;
    /**
     * Tile variable that refers to the tile right to the bomb.
     */
    private final Tile bombTriggerRight;
    /**
     * LinkedList of items that are on the
     * Same row and column of the bomb.
     */
    private LinkedList<Item> itemsXY = new LinkedList<>();

    /**
     * Creates a bomb.
     * And each instance of bomb holds the triggers
     * And the linkedList of items that are on te
     * Same row and column of the bomb.
     */

    public Bomb() {
        this.id = '*';
        this.imagePath = "blackHole.png";
        this.bombTriggerUp = getTile().getNeighbourTile(Direction.UP);
        this.bombTriggerDown = getTile().getNeighbourTile(Direction.DOWN);
        this.bombTriggerLeft = getTile().getNeighbourTile(Direction.LEFT);
        this.bombTriggerRight = getTile().getNeighbourTile(Direction.RIGHT);
        this.itemsXY = getItemsXY();
    }

    /**
     * Method that gets the itemsXY and
     * Loop through the list to destroy each item.
     */
    public void destroyItems() {
        do {
            if (this.itemsXY.peek() instanceof Bomb) {
                this.itemsXY.add(this.itemsXY.poll());
            } else {
                level.removeItem(this.itemsXY.peek());
                this.itemsXY.peek().getTile().setItem(null);
                imageView.setOpacity(0);
                this.itemsXY.poll();
            }
        } while (this.itemsXY.size() > 0 &&
                !(this.itemsXY.peek() instanceof Bomb));
        while (this.itemsXY.size() > 0) {
            level.removeItem(this.itemsXY.peek());
            this.itemsXY.peek().getTile().setItem(null);
            imageView.setOpacity(0);
            ((Bomb) this.itemsXY.peek()).destroyItems();
        }
    }

    /**
     * Finds the items on the same row and column of the bomb
     * and adds them to this the list.
     *
     * @return the list of itemsXY.
     */
    public LinkedList<Item> getItemsXY() {
        boolean neighbourExists = true;
        Tile neighbour = this.getTile();
        Tile neighbourUp = neighbour.getNeighbourTile(Direction.UP);
        Tile neighbourRight = neighbour.getNeighbourTile(Direction.RIGHT);
        Tile neighbourDown = neighbour.getNeighbourTile(Direction.DOWN);
        Tile neighbourLeft = neighbour.getNeighbourTile(Direction.LEFT);
        /*
        neighbourExists is a boolean variable
        while there are neighbors keep looping
        if any of the neighbours are not null else
        set neighbour Exists to false
         */
        while (neighbourExists) {
            if (neighbourUp != null || neighbourRight != null ||
                    neighbourDown != null || neighbourLeft != null) {
                if (neighbourRight != null) {
                    /*
                    rest of the conditions check if the next tile is not empty
                    then check if the item of that tile
                    is not an instanceOf a Gate or a Door
                    then add that item to the list
                     */
                    if ((neighbourUp != null)) {
                        if (!(neighbourUp.getItem() instanceof Gate ||
                                neighbourUp.getItem() instanceof Door)) {
                            this.itemsXY.add(neighbourUp.getItem());
                            neighbourUp = neighbourUp.getNeighbourTile(Direction.UP);
                        }
                    }
                }
                if (neighbourRight != null) {
                    if (!(neighbourRight.getItem() instanceof Gate ||
                            neighbourRight.getItem() instanceof Door)) {
                        this.itemsXY.add(neighbourRight.getItem());
                        neighbourRight = neighbourRight.getNeighbourTile(Direction.RIGHT);
                    }
                }

                if (neighbourDown != null) {
                    if (!(neighbourDown.getItem() instanceof Gate ||
                            neighbourDown.getItem() instanceof Door)) {
                        this.itemsXY.add(neighbourDown.getItem());
                        neighbourDown = neighbourDown.getNeighbourTile(Direction.RIGHT);
                    }
                }
                if (neighbourLeft != null) {
                    if (!(neighbourLeft.getItem() instanceof Gate ||
                            neighbourLeft.getItem() instanceof Door)) {
                        this.itemsXY.add(neighbourLeft.getItem());
                        neighbourLeft = neighbourLeft.getNeighbourTile(Direction.RIGHT);
                    }
                }
            } else {
                neighbourExists = false;
            }
        }
        return this.itemsXY;
    }


    /**
     * @return getter for the variable isTriggered.
     */
    public boolean isTriggered() {
        return this.isTriggered;
    }

    /**
     * setter for variable isTriggered.
     */
    public void trigger() {
        isTriggered = true;
    }

    /**
     * @return bombTriggerUp.
     */
    public Tile getBombTriggerUp() {
        return this.bombTriggerUp;
    }

    /**
     * @return BombTriggerDown
     */
    public Tile getBombTriggerDown() {
        return this.bombTriggerDown;
    }

    /**
     * @return BombTriggerLeft
     */
    public Tile getBombTriggerLeft() {
        return this.bombTriggerLeft;
    }

    /**
     * @return BombTriggerRight
     */
    public Tile getBombTriggerRight() {
        return this.bombTriggerRight;
    }
}
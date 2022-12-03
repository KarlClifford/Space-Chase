package com.example.spacechase;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a floor following thief. A floor following contains
 * components of follow colour, last tile, directions, and all shared components
 * from collector.
 * @author Tristan Tsang
 * @author Alex Hallsworth
 * @version 1.0.1
 */
public class FloorFollowing extends Collector {
    /**
     * The colour that the thief will follow.
     */
    private char followColour;
    /**
     * Last two tiles that the thief has been to.
     */
    private Tile[] lastTile;
    /**
     * Directions order for the thief to follow.
     */
    private Direction[] directions;

    /**
     * Creates a floor following thief.
     */
    public FloorFollowing() {
        this.id = 'F';
        this.imagePath = "floor_following.png";
        this.directions = Direction.values();
        this.lastTile = new Tile[2];
    }

    /**
     * Sets the tile of the character and sets the first colour
     * of the colours in tile to its following colour.
     * @param tile tile of the character.
     */
    @Override
    public void setTile(Tile tile) {
        this.tile = tile;
        char[] colours = tile.getColours();
        followColour = colours[0];
    }


    /**
     * Gets the tile to move onto. It loops through all the directions,
     * if there's a connection to another tile that contains followColour,
     * return the tile.
     * @return the tile to moved onto.
     */
    private Tile getLinkTile() {
        for (Direction direction : directions) {
            Tile link = tile.getLinkedTile(direction);

            /* If a link exists and does not have a character on it
               nor a gate on it. */
            if (link != null && link.getCharacter() == null
                && !(link.getItem() instanceof Gate)) {
                /* If the tile matches any tiles in last tile,
                 rotate the order of directions. */
                if (Arrays.stream(lastTile).anyMatch(t -> t == link)) {
                    directions = rotateArrayOrder(directions)
                            .toArray(Direction[]::new);
                }

                char[] colours = link.getColours();

                /* Return the tile if the following colour
                 is an element of all the colours in the tile. */
                if (isElement(followColour, colours)) {
                    return link;
                }
            }

        }

        return tile;
    }

    /**
     * Checks if the given element is an element of the given array.
     * @param elem element.
     * @param arr array.
     * @return whether element is an element of array.
     */
    private boolean isElement(char elem, char[] arr) {
        /* For every character in the array, return true if it equals
         to the element. Otherwise, return false. */
        for (char t : arr) {
            // Return true if character equals to element.
            if (t == elem) {
                return true;
            }
        }

        return false;
    }

    /**
     * Rotates the order of an array.
     * @param arr array
     * @param <T> type of array
     * @return shifted order of array in array list.
     */
    private <T> ArrayList<T> rotateArrayOrder(T[] arr) {
        ArrayList<T> newArr = new ArrayList<>(
                Arrays.asList(arr).subList(1, arr.length));
        newArr.add(arr[0]);

        return newArr;
    }

    /**
     * Moves to the new tile, sets lastTile to be last two tiles.
     */
    @Override
    void move() {
        Tile link = getLinkTile();

        lastTile = new Tile[]{lastTile[1], link};
        changeTile(link);
    }
}

package com.example.spacechase;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a floor following thief. A floor following contains
 * components of follow colour, last tile, directions, and all shared components
 * from collector.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class FloorFollowing extends Collector {
    /**
     * the colour that the thief will follow.
     */
    private char followColour;
    /**
     * last two tiles that the thief has been to.
     */
    private Tile[] lastTile;
    /**
     * directions order.
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

            if (link != null && link.getCharacter() == null) {
                if (Arrays.stream(lastTile).anyMatch(t -> t == link)) {
                    directions = rotateArrayOrder(directions)
                            .toArray(Direction[]::new);
                    //return getLinkTile();
                }

                char[] colours = link.getColours();

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
        for (char t : arr) {
            if (t == elem) {
                return true;
            }
        }

        return false;
    }

    /**
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
     * moves to the new tile and set lastTile to be last two tiles.
     */
    @Override
    void move() {
        Tile link = getLinkTile();

        lastTile = new Tile[]{lastTile[1], link};
        changeTile(link);
    }
}

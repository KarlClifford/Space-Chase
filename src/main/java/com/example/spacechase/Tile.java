package com.example.spacechase;

import java.util.Arrays;
import java.util.HashMap;

public class Tile {
    /**
     * tile size.
     */
    public static final double TILE_SIZE = 50;
    /**
     * colours of the tile.
     */
    private final char[] colours;
    /**
     * x position.
     */
    private final int x;
    /**
     * y position.
     */
    private final int y;
    /**
     * links of the tile.
     */
    private final HashMap<Direction, Tile> links;
    /**
     * neighbours of the tile.
     */
    private final HashMap<Direction, Tile> neighbours;
    /**
     * item of the tile.
     */
    private Item item;
    /**
     * character of the tile.
     */
    private Character character;

    /**
     * Creates a new Tile instance.
     *
     * @param x       x position.
     * @param y       y position.
     * @param colours colours of the tile.
     */
    public Tile(int x, int y, String colours) {
        this.x = x;
        this.y = y;
        this.colours = colours.toCharArray();
        this.links = new HashMap<>();
        this.neighbours = new HashMap<>();
    }

    /**
     * Sets the linking tile with given direction.
     *
     * @param direction direction.
     * @param tile      linking tile.
     */
    public void setLink(Direction direction, Tile tile) {
        links.put(direction, tile);
    }

    /**
     * Sets the neighbour tile with given direction.
     *
     * @param direction direction.
     * @param tile      neighbour tile.
     */
    public void setNeighbour(Direction direction, Tile tile) {
        neighbours.put(direction, tile);
    }

    /**
     * @param item item of the tile.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @param character character of the tile.
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * @return x position.
     */
    public int getX() {
        return x;
    }

    /**
     * @return y position.
     */
    public int getY() {
        return y;
    }

    /**
     * @return colours of the tile.
     */
    public char[] getColours() {
        return colours;
    }

    /**
     * @return item of the tile.
     */
    public Item getItem() {
        return item;
    }

    /**
     * @return character of the tile.
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Gets linking tile in given direction.
     *
     * @param direction direction.
     * @return linked tile in direction.
     */
    public Tile getLinkedTile(Direction direction) {
        return links.get(direction);
    }

    /**
     * Gets neighbour tile in given direction.
     *
     * @param direction direction.
     * @return neighbour tile in direction.
     */
    public Tile getNeighbourTile(Direction direction) {
        return neighbours.get(direction);
    }

    /**
     * @param t tile to be compared.
     * @return whether there are any elements in colours.
     * of the tiles are the same.
     */
    public boolean equalsColour(Tile t) {
        for (char x : (t).getColours()) {
            for (char y : colours) {
                if (x == y) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param o object to be compared.
     * @return whether is the same tile or not.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Tile t) {
            return t.getX() == x && t.getY() == y;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * @return string of the tile.
     */
    @Override
    public String toString() {
        return String.format("Tile@%d,%d@%s", x, y, Arrays.toString(colours));
    }
}

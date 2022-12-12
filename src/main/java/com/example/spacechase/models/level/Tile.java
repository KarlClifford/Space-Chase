package com.example.spacechase.models.level;

import com.example.spacechase.models.characters.Character;
import com.example.spacechase.models.items.Item;
import com.example.spacechase.utils.Direction;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * This class represents a tile.
 * A tile contains components of colours array, position x
 * and y, links and neighbours tile, an item, and a character.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class Tile {
    /**
     * Tile size.
     */
    public static final double TILE_SIZE = 50;
    /**
     * Colours of the tile.
     */
    private final char[] colours;
    /**
     * X position in the map.
     */
    private final int x;
    /**
     * Y position in the map.
     */
    private final int y;
    /**
     * Links of the tile.
     */
    private final HashMap<Direction, Tile> links;
    /**
     * Neighbours of the tile.
     */
    private final HashMap<Direction, Tile> neighbours;
    /**
     * Item of the tile.
     */
    private Item item;
    /**
     * Character of the tile.
     */
    private Character character;

    /**
     * Creates a new Tile instance.
     * @param x x position.
     * @param y y position.
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
     * @param direction direction.
     * @param tile linking tile.
     */
    public void setLink(Direction direction, Tile tile) {
        links.put(direction, tile);
    }

    /**
     * Sets the neighbour tile with given direction.
     * @param direction direction.
     * @param tile neighbour tile.
     */
    public void setNeighbour(Direction direction, Tile tile) {
        neighbours.put(direction, tile);
    }

    /**
     * Sets the item of the tile.
     * @param item item of the tile.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Sets the character of the tile.
     * @param character character of the tile.
     */
    public void setCharacter(Character character) {
        this.character = character;
    }

    /**
     * Gets the x position of the tile.
     * @return x position.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y position of the tile.
     * @return y position.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the colours of the tile.
     * @return colours of the tile.
     */
    public char[] getColours() {
        return colours;
    }

    /**
     * Gets the item in the tile.
     * @return item of the tile.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Gets the character in the tile.
     * @return character of the tile.
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * Gets linking tile in given direction.
     * @param direction direction.
     * @return linked tile in direction.
     */
    public Tile getLinkedTile(Direction direction) {
        return links.get(direction);
    }

    /**
     * Gets neighbour tile in given direction.
     * @param direction direction.
     * @return neighbour tile in direction.
     */
    public Tile getNeighbourTile(Direction direction) {
        return neighbours.get(direction);
    }

    /**
     * Return true if two tiles has common colour(s).
     * @param t tile to be compared.
     * @return whether there are any elements in colours.
     * of the tiles are the same.
     */
    public boolean equalsColour(Tile t) {
        /* Loop through both tile colours and return true
         if same colour is found. */
        for (char x : (t).getColours()) {
            /* Loop through colours of this tile to see
             if there is any match of colours to this
             colour. */
            for (char y : colours) {
                // return true if same colour is found.
                if (x == y) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns whether object equals to this tile.
     * @param o object to be compared.
     * @return whether is the same tile or not.
     */
    @Override
    public boolean equals(Object o) {
        /* If object is a tile, return whether the
         tile shares the same x y position on map
         (same position should be same tile).
         Otherwise, return false as nothing else should
         be equal to this tile. */
        if (o instanceof Tile t) {
            return t.getX() == x && t.getY() == y;
        } else {
            return false;
        }
    }

    /**
     * String of the tile that contains its colours and the data
     * of the entity in this tile.
     * @return string of the tile.
     */
    @Override
    public String toString() {
        String charStr = character == null ? "" : character.toString();
        String itemStr = item == null ? "" : item.toString();
        String entityType = charStr + itemStr;
        entityType = entityType.isBlank() ? "_" : entityType;

        return String.format("%s%s",
                String.join("",
                        Stream.of(colours)
                                .map(String::valueOf)
                                .toArray(String[]::new)),
                entityType);
    }
}

package com.example.spacechase;

import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * This abstract class represents an item. An item contains
 * components of id, tile, level and image.
 * @author Tristan Tsang
 * @author Alex Hallsworth
 * @author Ben Thornber
 * @version 1.0.1
 */
public abstract class Item {
    /**
     * Url path to images directory which contains images.
     */
    protected static final String PATH_TO_IMAGES = "images/";
    /**
     * Url path of the default item image.
     */
    protected String imagePath = "bin.png";
    /**
     * Image view of the item.
     * @see javafx.scene.image.ImageView
     */
    protected ImageView imageView;
    /**
     * ID of the item which indicates the type of item.
     */
    protected char id = '_';
    /**
     * Level of the item that is on.
     */
    protected Level level;
    /**
     * Tile of the item that is on.
     */
    protected Tile tile;

    /**
     * Gets the tile that the item is on.
     * @return tile of the item.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Sets the level of the item that is on.
     * @param level level of the item.
     */
    protected void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Sets the tile of the item that is on.
     * @param tile tile of the item.
     */
    protected void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Creates an image of the item at position (x,y).
     * @return image of the item.
     */
    protected ImageView createImageView() {
        imageView = new ImageView(Objects.requireNonNull(getClass()
                        .getResource(PATH_TO_IMAGES + imagePath))
                .toExternalForm());
        //imageView.setFitHeight(Tile.TILE_SIZE / 2);
        imageView.setPreserveRatio(true);
        imageView.relocate(
                (Tile.TILE_SIZE + Level.TILE_SPACING) * tile.getX(),
                Level.CANVAS_OFFSET_X
                        + (Tile.TILE_SIZE + Level.TILE_SPACING) * tile.getY());
        return imageView;
    }

    /**
     * Removes the item from level as it is collected.
     * @param collector collector of the item.
     */
    protected void interact(Collector collector) {
        level.removeItem(this);
        tile.setItem(null);
        imageView.setOpacity(0);
    }

    /**
     * @return Character 'L' indicating as an item
     * and id indicating its type of item with
     * a comma acting as a separator.
     */
    @Override
    public String toString() {
        return "L," + id + ",";
    }
}

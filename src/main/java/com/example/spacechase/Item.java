package com.example.spacechase;

import javafx.scene.image.ImageView;

import java.util.Objects;

public abstract class Item {
    /**
     * path to images.
     */
    protected static final String PATH_TO_IMAGES = "images/";
    /**
     * image path of the item.
     */
    protected String imagePath = "bin.png";
    /**
     * image view of the item.
     */
    protected ImageView imageView;
    /**
     * id of the item.
     */
    protected char id = '_';
    /**
     * level of the item.
     */
    protected Level level;
    /**
     * tile of the item.
     */
    protected Tile tile;

    /**
     * @return tile
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * @param level level of the item.
     */
    protected void setLevel(Level level) {
        this.level = level;
    }

    /**
     * @param tile tile of the item.
     */
    protected void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Creates an image of the item at position (x,y).
     * @param x x position.
     * @param y y position.
     * @return image of the item.
     */
    protected ImageView createImageView(int x, int y) {
        imageView = new ImageView(Objects.requireNonNull(getClass()
                        .getResource(PATH_TO_IMAGES + imagePath))
                .toExternalForm());
        imageView.setFitHeight(Tile.TILE_SIZE / 2);
        imageView.setPreserveRatio(true);
        imageView.relocate((Tile.TILE_SIZE + Level.TILE_SPACING) * x
                        + Tile.TILE_SIZE / 2 / 2,
                Level.CANVAS_OFFSET_X
                        + (Tile.TILE_SIZE + Level.TILE_SPACING) * y
                        + Tile.TILE_SIZE / 2 / 2);
        return imageView;
    }

    /**
     * removes the item from level as it is collected.
     * @param collector collector of the item.
     */
    protected void interact(Collector collector) {
        level.removeItem(this);
        tile.setItem(null);
        imageView.setOpacity(0);
    }

    /**
     * @return string of the item.
     */
    @Override
    public String toString() {
        return "L" + id;
    }
}

package com.example.spacechase.models.level;

import com.example.spacechase.models.Level;
import com.example.spacechase.utils.Data;
import javafx.scene.image.ImageView;


/**
 * This class represents an entity, where it is an object
 * that can be either a character or an item.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public abstract class Entity {
    /**
     * Url path to images directory that contains images.
     */
    protected static final String PATH_TO_IMAGES = "images/";

    /**
     * ID of the entity that defines the type.
     */
    protected char id;
    /**
     * Tile of the entity that is on.
     */
    protected Tile tile;
    /**
     * Url path of image of the entity.
     */
    protected String imagePath;
    /**
     * Image view of the entity.
     * @see javafx.scene.image.ImageView
     */
    protected ImageView imageView;
    /**
     * Level of the entity that is in.
     */
    protected Level level;

    /**
     * Creates an image view for the entity.
     */
    public void createImageView() {
        imageView = new ImageView(
                Data.getUrl(PATH_TO_IMAGES + imagePath)
                        .toExternalForm());
        imageView.setFitHeight(Tile.TILE_SIZE);
        imageView.setPreserveRatio(true);
        draw();
    }

    /**
     * Draws the image of the entity to the center of the tile.
     */
    public void draw() {
        imageView.relocate(
                (Tile.TILE_SIZE + Level.TILE_SPACING) * tile.getX(),
                Level.CANVAS_OFFSET_X
                        + (Tile.TILE_SIZE + Level.TILE_SPACING) * tile.getY());
    }

    /**
     * Gets the image view.
     * @return image view of the entity.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Gets the tile of the entity is on.
     * @return tile that the entity is on.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Sets the level of the entity.
     * @param level level of the entity is in.
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Sets the tile of the entity.
     * @param tile tile of the entity is on.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Gets the id of this entity in string.
     * @return string data of this entity.
     */
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

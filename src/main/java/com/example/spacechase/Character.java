package com.example.spacechase;

import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * This abstract class represents a character. A character contains
 * components of id, tile, image, level.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public abstract class Character {
    /**
     * path to images.
     */
    protected static final String PATH_TO_IMAGES = "images/";
    /**
     * image path of the character.
     */
    protected String imagePath;
    /**
     * id of the character.
     */
    protected char id;
    /**
     * tile of the character.
     */
    protected Tile tile;
    /**
     * image view of the character.
     */
    protected ImageView imageView;
    /**
     * level of the character.
     */
    protected Level level;


    /**
     * Creates an image view for the character.
     */
    protected void createImageView() {
        imageView = new ImageView(
                Objects.requireNonNull(getClass()
                                .getResource(PATH_TO_IMAGES + imagePath))
                        .toExternalForm());
        imageView.setFitHeight(Tile.TILE_SIZE / 2);
        imageView.setPreserveRatio(true);
    }

    /**
     * @param tile tile of the character.
     */
    protected void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Sets level of the character.
     * @param level level.
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * @return image view of the character.
     */
    protected ImageView getImageView() {
        return imageView;
    }

    /**
     * Changes the current tile to the new tile.
     * @param link the tile to move on to.
     */
    protected void changeTile(Tile link) {
        tile.setCharacter(null);
        link.setCharacter(this);
        tile = link;
    }

    /**
     * Removes the character from the level.
     */
    protected void remove() {
        level.removeCharacter(this);
        tile.setCharacter(null);
        imageView.setOpacity(0);
    }

    /**
     * draw the image of the character to the center of the tile.
     */
    public void draw() {
        imageView.relocate(
                (Tile.TILE_SIZE + Level.TILE_SPACING) * tile.getX()
                        + Tile.TILE_SIZE / 2 / 2,
                Level.CANVAS_OFFSET_X
                        + (Tile.TILE_SIZE + Level.TILE_SPACING) * tile.getY()
                        + Tile.TILE_SIZE / 2 / 2);
    }

    /**
     * movement of a character.
     */
    abstract void move();

    /**
     * moves the character and draws it.
     */
    protected void update() {
        move();
        draw();
    }

    /**
     * @return string for all characters.
     */
    @Override
    public String toString() {
        return String.format("C%s%s%s", id, tile.getX(), tile.getY());
    }
}

package com.example.spacechase;

import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * This abstract class represents a character. A character contains
 * components of id, tile, image, level.
 * @author Tristan Tsang
 * @author Alex Hallsworth
 * @version 1.0.1
 */
public abstract class Character {
    /**
     * Url path to images directory that contains images.
     */
    protected static final String PATH_TO_IMAGES = "images/";
    /**
     * Url path of image of the character.
     */
    protected String imagePath;
    /**
     * ID of the character that defines the type.
     */
    protected char id;
    /**
     * Tile of the character that is on.
     */
    protected Tile tile;
    /**
     * Image view of the character.
     * @see javafx.scene.image.ImageView
     */
    protected ImageView imageView;
    /**
     * Level of the character that is in.
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
     * Sets the tile of the character.
     * @param tile tile that the character is in.
     */
    protected void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Sets level of the character.
     * @param level level that the character is in.
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Gets the image view.
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
     * Draws the image of the character to the center of the tile.
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
     * Movement of a character.
     */
    abstract void move();

    /**
     * Moves the character and draws it.
     */
    protected void update() {
        move();
        draw();
    }

    /**
     * @return 'C' indicating as a character, an id defining its type,
     * and its x, y position from the tile.
     */
    @Override
    public String toString() {
        return String.format("C,%s,%s,%s", id, tile.getX(), tile.getY());
    }
}

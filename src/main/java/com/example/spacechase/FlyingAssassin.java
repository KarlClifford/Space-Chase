package com.example.spacechase;

/**
 * This class represents a flying assassin. A flying assassin contains
 * components of directions and all shared components from character.
 * @author Tristan Tsang
 * @author Daniel Halsall
 * @author Ben Thornber
 * @version 1.0.3
 */
public class FlyingAssassin extends Character {
    /**
     * Direction of flying assassin.
     */
    private Direction direction;

    /**
     * Creates a flying assassin instance where
     * the initial direction is determined by the id.
     * @param id the character used to identify the flying assassin.
     */
    public FlyingAssassin(char id) {
        this.id = id;
        this.imagePath = "FlyingAssassin.gif";
        this.direction = getDirection(id);
    }

    /**
     * Gets the initial direction of the flying assassin.
     * @param id the id used in the text file for the flying assassin.
     * @return the direction the flying assassin will be.
     */
    public Direction getDirection(char id) {
        return switch (id) {
            case '^' -> Direction.UP;
            case '>' -> Direction.RIGHT;
            case '<' -> Direction.LEFT;
            case '⌄' -> Direction.DOWN;
            default -> null;
        };
    }

    /**
     * Gets the opposite direction of the given direction.
     * @param direction direction.
     * @return opposite direction.
     */
    private Direction getOppositeDirection(Direction direction) {
        return  switch (direction) {
            case UP -> Direction.DOWN;
            case DOWN -> Direction.UP;
            case RIGHT -> Direction.LEFT;
            case LEFT -> Direction.RIGHT;
        };
    }

    /**
     * Moves on to neighbour tile in moving direction. Changes direction to the
     * opposite if there's no neighbour tile in direction.
     */
    @Override
    void move() {
        Tile neighbour = tile.getNeighbourTile(direction);
        /*
         * If there's no neighbour tile in that direction
         * reverse the direction.
         */
        if (neighbour == null) {
            direction = getOppositeDirection(direction);
            move();
        } else {
            Character character = neighbour.getCharacter();

            /*
             * Eliminate the character if there is a character
             * on the tile.
             */
            if (character != null) {
                character.remove();
            }

            changeTile(neighbour);
        }
    }
}

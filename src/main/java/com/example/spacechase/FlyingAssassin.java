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
     * @param var the character used to identify the flying assassin.
     */
    public FlyingAssassin(char var) {
        this.id = setid(var);
        this.imagePath = "FlyingAssassin.gif";
        this.direction = setDirection(id);
    }

    /**
     * Sets the initial direction of the flying assassin.
     * @param direc the id used in the text file for the flying assassin.
     * @return the direction the flying assassin will be.
     */
    public Direction setDirection(char direc) {
        switch (direc) {
            case '^':
                direction = Direction.UP;
                break;
            case '>':
                direction = Direction.RIGHT;
                break;
            case '<':
                direction = Direction.LEFT;
                break;
            case '⌄':
                direction = Direction.DOWN;
                break;

        }
        return direction;
    }

    /**
     * Sets the id to the char used in the text file.
     * @param id character representing
     * the flying assassin in the level text file.
     * @return the character representing
     * the id of the flying assassin.
     */
    public char setid(char id) {
        return id;
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
            switch (direction) {
                case UP:
                    direction = Direction.DOWN;
                    move();
                    break;
                case DOWN:
                    direction = Direction.UP;
                    move();
                    break;
                case RIGHT:
                    direction = Direction.LEFT;
                    move();
                    break;
                case LEFT:
                    direction = Direction.RIGHT;
                    move();
                    break;


            }
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

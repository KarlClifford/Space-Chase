package com.example.spacechase;

/**
 * This class represents a flying assassin. A flying assassin contains
 * components of directions and all shared components from character.
 * @author Tristan Tsang
 * @author Daniel Halsall
 * @version 1.0.0
 */
public class FlyingAssassin extends Character {
    /**
     * Direction of flying assassin.
     */
    private Direction direction;

    /**
     * Creates a flying assassin instance. Moving direction starts at left.
     */
    public FlyingAssassin(char var) {
        this.id = setid(var);
        this.imagePath = "flying_assassin.png";
        this.direction = setDirection(id);
    }

    public Direction setDirection(char direc) {
        if (direc == '^') {
            System.out.println("up");
            return Direction.UP;
        }
        if (direc == '<') {
            System.out.println("left");
            return Direction.LEFT;
        }
        if (direc == '>') {
            System.out.println("right");
             return Direction.RIGHT;
        }
        return  Direction.DOWN;
    }

    public char setid(char id) {
        if (id == '^') {
            return '^';
        }
        if (id == '<') {
            return '<';
        }
        if (id == '>') {
            return '>';
        }
        return '⌄';
    }

    /**
     * Moves on to neighbour tile in moving direction. Changes direction to the
     * opposite if there's no neighbour tile in direction.
     */
    @Override
    void move() {
        Tile neighbour = tile.getNeighbourTile(direction);
        /* If there's no neighbour tile in that direction,
         change direction to an opposite direction.
         Otherwise, move to that tile. */
        if (neighbour == null) {
            if (direction.equals(Direction.UP)
                    || direction.equals(Direction.DOWN)) {
                direction = direction == Direction.UP
                        ? Direction.DOWN
                        : Direction.UP;
                move();
            }
            if (direction.equals(Direction.LEFT)
                    || direction.equals(Direction.RIGHT)) {
                direction = direction == Direction.LEFT
                        ? Direction.RIGHT
                        : Direction.LEFT;
                move();
            }
        } else {
            Character character = neighbour.getCharacter();

            /* Eliminate the character if there is a character
             on the tile. */
            if (character != null) {
                character.remove();
            }

            changeTile(neighbour);
        }
    }
}

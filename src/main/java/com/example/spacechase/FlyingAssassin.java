package com.example.spacechase;

/**
 * This class represents a flying assassin. A flying assassin contains
 * components of directions and all shared components from character.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class FlyingAssassin extends Character {
    /**
     * direction of flying assassin.
     */
    private Direction direction;

    /**
     * Creates a flying assassin instance. Moving direction starts at left.
     */
    public FlyingAssassin() {
        this.id = '^';
        this.imagePath = "flying_assassin.png";
        this.direction = Direction.LEFT;
    }

    /**
     * Moves on to neighbour tile in moving direction. Changes direction if
     * there's no neighbour tile in direction.
     */
    @Override
    void move() {
        Tile neighbour = tile.getNeighbourTile(direction);
        if (neighbour == null) {
            direction = direction == Direction.LEFT
                    ? Direction.RIGHT
                    : Direction.LEFT;
        } else {
            Character character = neighbour.getCharacter();

            if (character != null) {
                character.remove();
            }

            changeTile(neighbour);
        }
    }
}

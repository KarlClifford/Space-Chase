package com.example.spacechase;

import javafx.scene.paint.Color;

/**
 * This class represents a lever.
 * A lever opens all gates of the same colour and can be picked up.
 * @author Alex Hallsworth
 * @version 1.0.0
 */
public class Lever extends Item {
    /**
     * Colour of the lever.
     */
    private final Color colour;


    /**
     * Creates a lever.
     * @param type colour of the gate.
     */
    public Lever(Color type) {
        this.id = '|';
        /*
         * Matches the right image file
         * to the corresponding colour of lever.
         */
        if (type == Color.RED) {
            this.imagePath = "lever_red.png";
        } else {
            this.imagePath = "lever_green.png";
        }
        this.colour = type;
    }

    /**
     * Gets the colour of the gate.
     * @return colour of the gate.
     */
    public Color getColour() {
        return this.colour;
    }

    /**
     * Removes all gates of the same colour as the lever.
     * @param opener opener of the gate.
     */
    public void removeGates(Collector opener) {
        Gate[] gates = level.getItems()
                .stream()
                .filter(item -> item instanceof Gate)
                .toArray(Gate[]::new)
                .clone();
        /*
         * Gets all the gates of the same colour
         * and adds it to a list with others.
         */
        for (Gate gate : gates) {
            /*
             * If gate's colour is the same as this lever
             * add it to the list of gates of that colour.
             */
            if (gate.getColour() == colour) {
                gate.interact(opener);
            }
        }
    }

    /**
     * Opens all gates of the same colour.
     * @param collector collector of this lever.
     */
    @Override
    public void interact(Collector collector) {
        super.interact(collector);
        removeGates(collector);
    }
}

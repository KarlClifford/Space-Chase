package com.example.spacechase;

import javafx.scene.paint.Color;
import java.util.ArrayList;

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
         * to the corresponding colour of lever
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
     * @return colour of the gate
     */
    public Color getColour() {
        return this.colour;
    }

    /**
     * Removes all gates of the same colour as the lever.
     * @param opener opener of the gate.
     */
    public void removeGates(Collector opener) {
        ArrayList<Item> gates = new ArrayList<>();
        /*
         * Gets all the gates of the same colour
         * and adds it to a list with others.
         */
        for (Item item : level.getItems()) {
            // If item's class is a Gate
            if (item.getClass() == Gate.class) {
                /*
                 * If gate's colour is the same as this lever
                 * add it to the list of gates of that colour
                 */
                if (((Gate) item).getColour() == getColour()) {
                    gates.add(item);
                }
            }
        }
        /*
         * Goes through a list of gates of the same colour
         * Removes them from the game.
         */
        for (Item gate : gates) {
            gate.interact(opener);
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

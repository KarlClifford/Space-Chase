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
     */
    public void removeGates() {
        ArrayList<Item> gates = new ArrayList<>();
        /*
        Gets all the gates of the same colour.
         */
        for (Item item : level.getItems()) {
            if (item.getClass() == Gate.class) {
                if (((Gate) item).getColour() == getColour()) {
                    gates.add(item);
                }
            }
        }
        for (Item gate : gates) {
            level.removeItem(gate);
            gate.tile.setItem(null);
            gate.imageView.setOpacity(0);
        }
    }

    /**
     * Opens all gates of the same colour.
     * @param collector collector of this lever.
     */
    @Override
    public void interact(Collector collector) {
        super.interact(collector);

        removeGates();
    }
}

package com.example.spacechase;

import javafx.scene.paint.Color;

/**
 * This class represents a gate.
 * A gate prevents the collectors from going on the tile it is on.
 * Can be opened with the corresponding colour lever.
 * @author Alex Hallsworth
 * @version 1.0.0
 */
public class Gate extends Item {
    /**
     * Colour of the Gate.
     */
    private final Color colour;

    /**
     * Creates a gate item.
     * @param type colour of the gate.
     */
    public Gate(Color type) {
        this.id = '#';
        if (type == Color.RED) {
            this.imagePath = "gate_red.png";
        } else {
            this.imagePath = "gate_green.png";
        }
        this.colour = type;
    }

    /**
     * Gets the colour of the gate.
     * @return the colour of the gate.
     */
    public Color getColour() {
        return this.colour;
    }
}

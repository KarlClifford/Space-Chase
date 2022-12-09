package com.example.spacechase;

import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * This class represents a gate.
 * A gate prevents the collectors from going on the tile it is on.
 * Can be opened with the corresponding colour lever.
 * @author Alex Hallsworth
 * @version 1.0.0
 */
public class Gate extends Item {
    /**
     * Stores the colours that can be used to set the gate.
     */
    private static final HashMap<java.lang.Character, Color>
            COLOUR_TYPES = new HashMap<>();
    static {
        COLOUR_TYPES.put('(', Color.RED);
        COLOUR_TYPES.put(')', Color.GREEN);
    }
    /**
     * Colour of the Gate.
     */
    private final Color colour;
    /**
     * Creates a gate item.
     * @param type colour of the gate.
     */
    public Gate(char type) {
        this.id = type;
        /*
         * Assigns the correct imagePath
         * to the gate. If the gate is red
         * the file will be the red gate etc.
         */
        if (type == '(') {
            this.imagePath = "redGate.gif";
        } else {
            this.imagePath = "greenGate.gif";
        }
        this.colour = COLOUR_TYPES.get(type);
    }

    /**
     * Gets the colour of the gate.
     * @return the colour of the gate.
     */
    public Color getColour() {
        return this.colour;
    }
}

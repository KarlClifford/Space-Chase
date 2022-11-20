package com.example.spacechase;

import java.util.ArrayList;

/**
 * This class represents a smart thief.
 * A smart thief contains components of all shared components
 * from character.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class SmartThief extends Collector {
    /**
     * Creates a smart thief.
     */
    public SmartThief() {
        this.id = 'S';
        this.imagePath = "smart_thief.png";
    }

    /**
     * Find the shortest path to the closest item.
     * @return path to item.
     */
    private Path getShortestPathToItem() {
        ArrayList<Item> items = level.getItems();
        int distance = Integer.MAX_VALUE;
        Path finalPath = null;

        for (Item item : items) {
            Tile itemTile = item.getTile();

            BFS graph = new BFS(tile);
            Path path = graph.search(itemTile);
            int tileDistance = path.size();

            if (tileDistance < distance) {
                distance = tileDistance;
                finalPath = path;
            }
        }
        return finalPath;
    }

    /**
     * Moves the character by following the path if a path exists.
     */
    @Override
    void move() {
        Path path = getShortestPathToItem();

        if (path != null) {
            path.dequeue();

            if (!path.isEmpty()) {
                Tile newTile = path.dequeue();
                changeTile(newTile);
            }
        }
    }
}

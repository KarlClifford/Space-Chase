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

        /* Calculate a path for each item in the map
        and use the path that has the shortest
        distance. */
        for (Item item : items) {
            Tile itemTile = item.getTile();

            BFS graph = new BFS(tile);
            Path path = graph.search(itemTile);
            int tileDistance = path.size();

            /* If this path is a shorter path than
             the last path, then use this path. */
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

        /* If a path is found, dequeue the first
         tile (as first would be current tile),
         and move onto the second tile if it exists. */
        if (path != null) {
            path.dequeue();

            /* If the path is not empty, dequeue the
            tile and move onto that tile. */
            if (!path.isEmpty()) {
                Tile newTile = path.dequeue();
                changeTile(newTile);
            }
        }
    }
}

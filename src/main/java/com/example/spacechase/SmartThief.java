package com.example.spacechase;

import java.util.ArrayList;

/**
 * This class represents a smart thief.
 * A smart thief contains components of all shared components
 * from character.
 * @author Tristan Tsang
 * @author Alex Hallsworth
 * @version 1.1.0
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
     * Find the shortest path to the closest interactive item.
     * @return path to item.
     */
    private Path getShortestPathToItem() {

        /*
         * Puts all the interactive items
         * in a list. (checks the item isn't a gate)
         */
        ArrayList<Item> items = new ArrayList<>();
        for (Item item: level.getItems()) {
            // Checks if item is not a gate
            if (!(item instanceof Gate)) {
                    items.add(item);
            }
        }
        int distance = Integer.MAX_VALUE;
        Path finalPath = null;

        /*
         * Calculate a path for each item in the map
         * and use the path that has the shortest
         * distance.
         */
        for (Item item : items) {
            Tile itemTile = item.getTile();

            BFS graph = new BFS(tile);
            Path path = graph.search(itemTile);

            /*
             * Checks if the path is null
             * meaning the path is unblocked
             */
            if (path != null) {
                int tileDistance = path.size();
                /*
                 * If this path is a shorter path than
                 * the last path, then use this path.
                 */
                if (tileDistance < distance) {
                    distance = tileDistance;
                    finalPath = path;
                }

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

        /*
         * If a path is found, dequeue the first
         * tile (as first would be current tile),
         * and move onto the second tile if it exists.
         */
        if (path != null) {
            path.dequeue();

            /*
             * If the path is not empty, dequeue the
             * tile and move onto that tile.
             */
            if (!path.isEmpty()) {
                Tile newTile = path.dequeue();
                changeTile(newTile);
            }
        }
    }
}

package com.example.spacechase.models.characters;

import com.example.spacechase.models.items.Gate;
import com.example.spacechase.models.items.Item;
import com.example.spacechase.models.items.Note;
import com.example.spacechase.models.level.Tile;
import com.example.spacechase.utils.BFS;
import com.example.spacechase.utils.Path;

/**
 * This class represents a smart thief.
 * A smart thief contains components of all shared components
 * from character.
 * @author Tristan Tsang
 * @author Alex Hallsworth
 * @author Ben Thornber
 * @version 1.0.1
 */
public class SmartThief extends Collector {
    /**
     * Creates a smart thief.
     */
    public SmartThief() {
        this.id = 'S';
        this.imagePath = "SmartThief.gif";
    }

    /**
     * Find the shortest path to the closest interactive item.
     * @return path to item.
     */
    private Path getShortestPathToItem() {
        int distance = Integer.MAX_VALUE;
        Path finalPath = null;

        /*
         * Calculate a path for each item in the map
         * and use the path that has the shortest
         * distance.
         */
        for (Item item : level.getItems()) {
            Tile itemTile = item.getTile();

            BFS graph = new BFS(tile);
            Path path = graph.search(itemTile);

            /*
             * Checks if the path is null
             * meaning the path is unblocked
             * ignores gates. Ignore all notes as they
             * should only be interacted by player.
             */
            if (path != null
                    && !(item instanceof Gate || item instanceof Note)) {
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

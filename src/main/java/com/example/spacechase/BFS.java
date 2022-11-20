package com.example.spacechase;

import java.util.PriorityQueue;

/**
 * This class represents the path finding algorithm of Breadth First Search.
 * It contains components of paths and nodes. A graph can be created by
 * providing a root tile, a path can then be calculated with an end tile.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class BFS {
    /**
     * All paths sorted with distance. Shorter path has higher priority.
     */
    private final PriorityQueue<Path> paths;

    /**
     * Creates a new graph starting with a path of origin tile.
     * @param root origin tile.
     */
    public BFS(Tile root) {
        Path path = new Path();
        path.enqueue(root);

        this.paths = new PriorityQueue<>();
        paths.add(path);
    }

    /**
     * Searches through the graph using Breadth First Search
     * until a path that ends at end tile is found or until
     * there are no more paths.
     * @param end end tile.
     * @return path to tile.
     */
    public Path search(Tile end) {
        while (!paths.isEmpty()) {
            Path path = paths.poll();
            Tile tile = path.getRear();

            if (tile == end) {
                return path;
            } else {
                for (Direction direction : Direction.values()) {
                    Tile link = tile.getLinkedTile(direction);

                    if (link != null
                            && link.getCharacter() == null
                            && !path.contains(link)) {
                        Path newPath = path.clone();
                        newPath.enqueue(link);
                        paths.add(newPath);
                    }
                }
            }
        }

        return null;
    }
}

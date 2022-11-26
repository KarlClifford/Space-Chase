package com.example.spacechase;

import java.util.HashSet;

/**
 * This class represents a path.
 * A path contains components of front node, rear node and
 * tile exists in the path.
 * and a back button.
 * @author Tristan Tsang
 * @version 1.0.0
 */
public class Path implements Comparable<Path> {
    /**
     * Tiles that exists in path.
     */
    private HashSet<Tile> exists;
    /**
     * Front node of path.
     */
    private Node<Tile> front;
    /**
     * Rear node of path.
     */
    private Node<Tile> rear;

    /**
     * Creates a path.
     */
    public Path() {
        this.exists = new HashSet<>();
    }

    /**
     * Enqueue a node with element tile.
     * @param t Tile
     */
    public void enqueue(Tile t) {
        exists.add(t);

        Node<Tile> node = new Node<>(t);
        /* If the list is empty, sets front and rear
         to the node. Otherwise, sets next node of rear
         to the node, and sets rear to node. */
        if (isEmpty()) {
            front = rear = node;
        } else {
            rear.setNext(node);
            rear = node;
        }
    }

    /**
     * Dequeue the path.
     * @return element of front node.
     */
    public Tile dequeue() {
        Node<Tile> node = front;
        /* If there is no front node then return null.
         Otherwise, sets the front to its next node,
         and return the front node. */
        if (node == null) {
            return null;
        } else {
            front = front.getNext();

            /* If there is no front node then set
            rear node to null */
            if (front == null) {
                rear = null;
            }

            return node.getElement();
        }
    }

    /**
     * Gets the size of the path.
     * @return size of the path.
     */
    public int size() {
        return exists.size();
    }

    /**
     * Gets boolean of path is empty or not.
     * @return path is empty or not.
     */
    public boolean isEmpty() {
        return this.front == null && this.rear == null;
    }

    /**
     * Searches for rear node by recursively getting the next node
     * starting from front.
     * @param current current node.
     * @return rear node.
     */
    private Node<Tile> recursiveGetRear(Node<Tile> current) {
        return current != null && current.getNext() != null
                ? recursiveGetRear(current.getNext())
                : current;
    }

    /**
     * Gets the rear tile.
     * @return element of rear node.
     */
    public Tile getRear() {
        Node<Tile> rear = recursiveGetRear(front);
        return rear == null ? null : rear.getElement();
    }

    /**
     * @param t tile
     * @return tile exists in path or not.
     */
    public boolean contains(Tile t) {
        return exists.contains(t);
    }

    /**
     * Sets the front node.
     * @param front front node.
     */
    public void setFront(Node<Tile> front) {
        this.front = front;
    }

    /**
     * Sets the rear node.
     */
    public void setRear() {
        this.rear = recursiveGetRear(front);
    }

    /**
     * Sets exists.
     * @param exists tiles exists in path.
     */
    public void setExists(HashSet<Tile> exists) {
        this.exists = exists;
    }

    /**
     * Clones the current path to a new path.
     * @return new path.
     */
    @Override
    public Path clone() {
        Path path = new Path();
        path.setFront(front.clone());
        path.setRear();
        path.setExists(exists);

        return path;
    }

    /**
     * Compares the sizes of this path and given path.
     * @param p the object to be compared.
     * @return 0 if both sizes of paths are equal; 1 if the size of this path is
     * greater than the path compared with; -1 if the size of this path is less
     * than the path compared with.
     */
    @Override
    public int compareTo(Path p) {
        return Integer.compare(this.size(), p.size());
    }

    /**
     * Gets the string of the node and its next node.
     * @param node current node.
     * @return string of the node.
     */
    private String getString(Node<Tile> node) {
        /* Return empty string if node is null.
         Otherwise, return string of current node
         and string of its next node. */
        if (node == null) {
            return "";
        } else {
            return node.getElement().toString() + getString(node.getNext());
        }
    }

    /**
     * Return string of all nodes starting from the front node
     * and call recursively to get its next nodes.
     * @return string of all nodes in path.
     */
    @Override
    public String toString() {
        return getString(front);
    }
}

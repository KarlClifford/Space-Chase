package com.example.spacechase.utils;

/**
 * This class represents a node.
 * A node contains components of an element and its next node.
 * A node is an element of a list or tree.
 * @author Tristan Tsang
 * @version 1.0.0
 * @param <T> type of node
 */
public class Node<T> {
    /**
     * Element of the node.
     */
    private T element;
    /**
     * Next node of the node.
     */
    private Node<T> next;

    /**
     * Creates a node with element t.
     * @param t element.
     */
    public Node(T t) {
        this.element = t;
    }

    /**
     * Gets element of the node.
     * @return element.
     */
    public T getElement() {
        return element;
    }

    /**
     * Gets next node of the node.
     * @return next node.
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Sets the element.
     * @param element element.
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Sets the next node.
     * @param next next node.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * Clones this node along with its next node.
     * @return new node.
     */
    public Node<T> clone() {
        Node<T> node = new Node<>(element);
        /* If there is next node, then sets
         next node to the clone of that node. */
        if (next != null) {
            node.setNext(next.clone());
        }
        return node;
    }
}

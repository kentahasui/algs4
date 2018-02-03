/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *  Dependencies: WeightedQuickUnionUF.java
 *
 *  This file an implementation of the Deque class.
 *  Deque is a double-ended queue that supports the Iterable interface.
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Double Ended Queue implementation
 * Supports all methods in worst-case constant time:
 * <ul>
 *  <li>isEmpty()</li>
 *  <li>size()</li>
 *  <li>addFirst()</li>
 *  <li>addLast()</li>
 *  <li>removeFirst()</li>
 *  <li>removeLast()</li>
 * </ul>
 * <p>
 *
 * Also implements iterable, so you may iterate over the Deque with a
 * for-each loop. Usage:
 *
 * <pre>
 * {@code
 *  Deque<Integer> deque = new Deque<>();
 *  deque.addFirst(1);
 *  deque.addLast(2);
 *  deque.addLast(3);
 *  deque.addFirst(0);
 *  for(Integer val : deque){
 *      System.out.println(val); // -> 0, 1, 2, 3
 * }
 * </pre>
 *
 * @param <Item> A generic class.
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    /**
     * Constructor. Initializes a 0-element Deque.
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    /**
     * @return true if the Deque is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return The number of elements in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Throws an exception for null inputs
     */
    private void validateAdd(Item item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "You cannot add null items to a Deque");
        }
    }

    /**
     * Throws an exception if the deque is already empty
     */
    private void validateRemove() {
        if (isEmpty()) {
            throw new NoSuchElementException("You cannot remove items " +
                    "from an empty Deque");
        }
    }

    /**
     * Adds an item to the front of the queue.
     * This will be the first item returned by removeFirst().
     *
     * @param item A non-null object.
     * @throws IllegalArgumentException if item is null.
     */
    public void addFirst(Item item) {
        validateAdd(item);
        Node newFirst = new Node(item);

        // Set first and last to new node if the deque is empty
        if (isEmpty()) {
            first = newFirst;
            last = newFirst;
        }

        // Add the first element to the head and update its next pointer
        else {
            newFirst.next = first;  // New node is at front: pointer to old
            first.prev = newFirst;  // Old node is second: back pointer to new
            first = newFirst;       // Update first reference to new node
        }
        size++;
    }

    /**
     * Adds an item to the back of the queue.
     * This will be the first item returned by removeLast().
     *
     * @param item A non-null object.
     * @throws IllegalArgumentException if item is null.
     */
    public void addLast(Item item) {
        validateAdd(item);
        Node newLast = new Node(item);

        // If the deque is empty, first and last both point to this node
        if (isEmpty()) {
            first = newLast;
            last = newLast;
        }

        // Otherwise update pointers
        else {
            newLast.prev = last;  // New node has back pointer to old
            last.next = newLast;  // Old node has a pointer to new node
            last = newLast;       // Last is now a reference to the new node
        }
        size++;
    }

    private void emptyDeque() {
        first = null;
        last = null;
    }

    /**
     * Removes the item at the front of the deque and returns it.
     *
     * @return The item at the front of the deque.
     * @throws NoSuchElementException if the deque is empty.
     */
    public Item removeFirst() {
        // Validate that this deque is not empty, and find item to remove
        validateRemove();
        Node oldFirst = first;

        // If the deque only has one item, empty the deque
        if (size == 1) {
            emptyDeque();
        }

        // There is more than one item in the deque: Set next item to head
        // Remove link to old head
        else {
            pointFirstToSecondNode();
        }

        size--;
        return oldFirst.item;
    }

    /**
     * Removes the link between the first node and second node.
     * Then updates the "first" instance variable to refer to the second node.
     * <p>
     * Only call this method when size > 1.
     * When size <= 1, call emptyDeque() instead.
     */
    private void pointFirstToSecondNode() {
        Node newFirst = first.next;  // Find the 2nd node
        newFirst.prev = null;        // Remove 1st node <- 2nd node pointer
        first.next = null;           // Remove 1st node -> 2nd node pointer
        first = newFirst;            // Set "first" variable to 2nd node
    }

    /**
     * Removes and returns the most recently added item from the queue.
     * @return The most recently added item in the queue.
     * @throws NoSuchElementException if the deque is empty
     */
    public Item removeLast() {
        // Validate that this deque is not empty, and find item to remove
        validateRemove();
        Node oldLast = last;

        // If we remove the only item, then first and last point to null
        if (size == 1) {
            emptyDeque();
        }

        // There is more than one item in the deque: Set next item to tail
        // Remove link to old tail
        else {
            pointLastToPenultimateNode();
        }

        size--;
        oldLast.prev = null; // garbage collection optimization
        return oldLast.item;
    }

    /**
     * Removes the link between the first node and second node.
     * Then updates the "last" instance variable point to the penultimate node.
     * <p>
     * Only call this method when size > 1.
     * When size <= 1, call emptyDeque() instead.
     */
    private void pointLastToPenultimateNode() {
        Node newLast = last.prev;  // Find the penultimate node
        newLast.next = null;       // Remove penult node -> last node pointer
        last.prev = null;          // Remove penult node <- last node pointer
        last = newLast;            // Set "last" variable to penultimate node
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    /**
     * Private Node class for Doubly-Linked List.
     */
    private class Node {
        private final Item item;
        private Node prev;
        private Node next;

        private Node(Item item) {
            this.item = item;
        }
    }

    /**
     * Iterator over the Deque.
     * Iterates front-to-back (FIFO) order.
     * Supports next() and hasNext(), but will throw UnsupportedOperationEx
     * for remove().
     */
    private class DequeIterator implements Iterator<Item> {
        Node current = first;

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node retVal = current;
            current = current.next;
            return retVal.item;
        }
    }

}

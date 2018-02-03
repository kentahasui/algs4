/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *  Dependencies: StdRandom.java
 *
 *  Randomized Queue implementation.
 *  Implemented via a resizing array.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generic Randomized Queue that holds non-null elements.
 * Supports isEmpty(), size(), enqueue(), dequeue(), and sample() methods.
 * Implements Iterable interface to support for-each loops.
 * Implemented via resizing array.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 2;

    private int size;
    private Item[] items;

    /**
     * Constructor. Builds a new RandomizedQueue with an initial capacity of
     * {@link RandomizedQueue#INITIAL_CAPACITY}.
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[INITIAL_CAPACITY];  // warning: [unchecked] unchecked cast
        size = 0;
    }

    /**
     * Determines whether the queue is empty.
     *
     * @return true if the queue is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items currently stored in the queue.
     *
     * @return the queue size (NOT the queue capacity, which dynamically
     * changes depending on the queue size).
     */
    public int size() {
        return size;
    }

    /**
     * Helper method for calculating the queue capacity
     */
    private int capacity() {
        return items.length;  // O(1) time, with no extra memory needed
    }

    /**
     * Adds a new item to this queue.
     * If the backing array is already at capacity, resizes the array.
     *
     * @param item Any non-null object of type Item.
     * @throws IllegalArgumentException if item is null.
     */
    public void enqueue(Item item) {
        validateNotNull(item);
        doubleCapacityIfFull();
        items[size++] = item;
    }

    /**
     * Retrieves, but does not remove, a random element from this queue.
     * Does not mutate the queue.
     *
     * @return An element from this queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    public Item sample() {
        validateNotEmpty();
        return items[randomIndex()];
    }

    /**
     * Retrieves and removes a random element from this queue.
     *
     * @return A random element in this queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    public Item dequeue() {
        validateNotEmpty();

        // Retrieve random item
        int randomIndex = randomIndex();
        Item randomItem = items[randomIndex];

        // Overwrite the array at the chosen index, and update size
        shiftLastItemToNewIndex(randomIndex);
        size--;

        // Resize array if needed
        halveCapacityIfQuarterFull();
        return randomItem;
    }

    /**
     * Returns a random index uniformly in [0, size)
     *
     * @return a random integer uniformly between 0 (inclusive) and {@code size} (exclusive).
     * @throws IllegalArgumentException if size == 0;
     */
    private int randomIndex() {
        assert size > 0;
        return StdRandom.uniform(size);
    }

    /**
     * Resizes the underlying array.
     *
     * @param newCapacity A new capacity. Must be greater than current size.
     */
    private void resize(int newCapacity) {
        assert newCapacity >= size;
        Item[] temp = (Item[]) new Object[newCapacity];  // warning: [unchecked] unchecked cast
        for (int i = 0; i < size; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    /**
     * Resizes when adding new items
     */
    private void doubleCapacityIfFull() {
        if (size() == capacity()) {
            resize(capacity() * 2);
        }
    }

    /**
     * Resize method to use when removing items
     */
    private void halveCapacityIfQuarterFull() {
        if (size() == 0) {
            return;
        }
        if (size() <= (capacity() / 4)) {
            resize(capacity() / 2);
        }
    }

    /**
     * Moves the item at the end of the items array to a specified index.
     * Then clears the item at the end of the array.
     * Maintains the invariant that ONLY indices (0, size] are filled in the
     * queue at any given time (0 inclusive, {@code size} exclusive.
     *
     * @param indexToReplace An integer between 0 (inclusive) and size (exclusive)
     */
    private void shiftLastItemToNewIndex(int indexToReplace) {
        items[indexToReplace] = items[size() - 1];
        items[size() - 1] = null;   // Garbage collection: avoid loitering
    }

    /**
     * Throws IllegalArgumentException if parameter is null
     */
    private void validateNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Null item");
        }
    }

    /**
     * Throws NoSuchElementException if queue is empty
     */
    private void validateNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    /**
     * Iterator for RandomizedQueue.
     * Iterates over all items in the queue in a random order.
     */
    private class RandomizedQueueIterator implements Iterator<Item> {
        // Initial size. Used to check if the underlying queue was
        // modified during iteration.
        private final int initSize;

        // Next item to access
        private int nextIndex;

        // Holds iteration order over queue
        private final int[] iterationIndices;

        /**
         * Iterator constructor.
         */
        private RandomizedQueueIterator() {
            nextIndex = 0;
            initSize = size;

            // Deal with empty queue
            if (size == 0) {
                iterationIndices = new int[1];
                return;
            }

            // Create random iteration order for queue contents
            iterationIndices = new int[initSize];
            for (int i = 0; i < size; i++) {
                iterationIndices[i] = i;
            }
            StdRandom.shuffle(iterationIndices);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove not supported");
        }

        @Override
        public boolean hasNext() {
            validateQueueNotChanged();
            return nextIndex < size;
        }

        @Override
        public Item next() {
            validateQueueNotChanged();
            if (!hasNext()) {
                throw new NoSuchElementException("No more items to iterate over");
            }

            return items[iterationIndices[nextIndex++]];
        }

        private void validateQueueNotChanged() {
            if (initSize != size) {
                throw new ConcurrentModificationException("Mutating the queue while iterating leads to undefined behavior");
            }
        }
    }

}

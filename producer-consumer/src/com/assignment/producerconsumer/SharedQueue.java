package com.assignment.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * SharedQueue represents a thread-safe blocking queue used by
 * producer and consumer threads for synchronized data exchange.
 *
 * Design & Assumptions:
 * - We implement our own blocking queue using wait() and notifyAll()
 *   to demonstrate low-level thread synchronization.
 * - Queue operations (put & take) block when full/empty.
 * - Uses intrinsic lock on 'this' for synchronization.
 */
public class SharedQueue {

    private final Queue<Integer> queue;
    private final int capacity;

    /**
     * Constructor to initialize shared queue with given capacity.
     * @param capacity maximum number of items this queue can hold
     */
    public SharedQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    /**
     * Adds an item to the queue.
     * If queue is full, waits until space becomes available.
     * @param value integer item to be added
     */
    public synchronized void put(int value) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();  // Wait until consumer removes item
        }
        queue.add(value);
        notifyAll();  // Notify waiting consumer threads
    }

    /**
     * Removes an item from the queue.
     * If empty, waits until producer adds an item.
     * @return integer item removed from queue
     */
    public synchronized int take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();  // Wait until producer adds item
        }
        int value = queue.remove();
        notifyAll();  // Notify producer threads
        return value;
    }

    /**
     * Getter for queue size (used only for testing).
     */
    public synchronized int size() {
        return queue.size();
    }
}
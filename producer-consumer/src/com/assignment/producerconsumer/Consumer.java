package com.assignment.producerconsumer;

import java.util.List;

/**
 * Consumer class that retrieves integers from the shared queue
 * and stores them in a destination list.
 *
 * Assumptions:
 * - Consumer stops after consuming predefined 'count' items.
 * - Destination list is shared only within test/main and is thread-safe externally.
 */
public class Consumer implements Runnable {

    private final SharedQueue queue;
    private final List<Integer> destination;
    private final int consumeCount;

    /**
     * Constructor defining Consumer behavior.
     * @param queue shared queue instance
     * @param destination list to store consumed items
     * @param consumeCount number of items to consume
     */
    public Consumer(SharedQueue queue, List<Integer> destination, int consumeCount) {
        this.queue = queue;
        this.destination = destination;
        this.consumeCount = consumeCount;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < consumeCount; i++) {
                int value = queue.take();
                destination.add(value);
                System.out.println("Consumed: " + value);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
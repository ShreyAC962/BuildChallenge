package com.assignment.producerconsumer;

import java.util.List;

/**
 * Producer class that reads integers from a source list
 * and places them into the shared blocking queue.
 *
 * Design Choice:
 * - Producer stops once it finishes reading the source list.
 * - Each produced element is logged for demonstration.
 */
public class Producer implements Runnable {

    private final SharedQueue queue;
    private final List<Integer> source;

    /**
     * Constructor to define Producer behavior.
     * @param queue shared queue instance
     * @param source list of integers to produce
     */
    public Producer(SharedQueue queue, List<Integer> source) {
        this.queue = queue;
        this.source = source;
    }

    @Override
    public void run() {
        try {
            for (int item : source) {
                queue.put(item);
                System.out.println("Produced: " + item);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
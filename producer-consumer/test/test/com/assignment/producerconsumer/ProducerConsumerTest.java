package test.com.assignment.producerconsumer;

import com.assignment.producerconsumer.SharedQueue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Producer-Consumer system using SharedQueue.
 *
 * Tests cover:
 * - Correct data transfer between producer and consumer
 * - Queue never exceeding its capacity
 * - Blocking behavior of consumer when queue is empty
 * - Blocking behavior of producer when queue is full
 *
 * Assumptions:
 * - Producer produces a fixed set of integers
 * - Consumer consumes exactly the number of items produced
 * - SharedQueue uses proper wait/notify synchronization
 */
public class ProducerConsumerTest {

    /**
     * Test that all produced items are correctly transferred
     * to the consumer's destination list.
     *
     * Demonstrates basic producer-consumer functionality with synchronized queue.
     *
     * @throws InterruptedException if threads are interrupted
     */
    @Test
    void testCorrectDataTransfer() throws InterruptedException {
        SharedQueue queue = new SharedQueue(3);

        List<Integer> source = List.of(10, 20, 30, 40);
        List<Integer> destination = new ArrayList<>();

        // Producer thread: produces integers from source list into shared queue
        Thread producerThread = new Thread(() -> {
            try {
                for (int item : source) {
                    /**
                     * Adds item to the shared queue.
                     * Blocks if queue is full until space is available.
                     */
                    queue.put(item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer thread: consumes exactly the number of items in source list
        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < source.size(); i++) {
                    /**
                     * Removes item from shared queue.
                     * Blocks if queue is empty until item is available.
                     */
                    destination.add(queue.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

        assertEquals(source, destination, "All produced items should be consumed in order");
    }

    /**
     * Test that the queue never exceeds its capacity.
     * Uses CountDownLatch to ensure deterministic testing point.
     *
     * @throws InterruptedException if threads are interrupted
     */
    @Test
    void testQueueNeverExceedsCapacity() throws InterruptedException {
        SharedQueue queue = new SharedQueue(1);

        List<Integer> source = List.of(1, 2, 3);
        List<Integer> destination = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        // Producer thread: produces integers and signals after first item
        Thread producerThread = new Thread(() -> {
            try {
                for (int item : source) {
                    /**
                     * Produce item into queue.
                     * Blocks if queue is full.
                     */
                    queue.put(item);
                    if (item == source.get(0)) latch.countDown(); // signal test
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Consumer thread: consumes all items
        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < source.size(); i++) {
                    /**
                     * Consume item from queue.
                     * Blocks if queue is empty.
                     */
                    destination.add(queue.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();
        latch.await(); // wait until first item produced
        assertTrue(queue.size() <= 1, "Queue should never exceed its capacity");

        consumerThread.start();
        producerThread.join();
        consumerThread.join();

        assertEquals(source, destination, "All items should be consumed correctly");
    }

    /**
     * Test that consumer waits when the queue is empty.
     * Ensures consumer does not consume before producer produces.
     *
     * @throws InterruptedException if threads are interrupted
     */
    @Test
    void testConsumerWaitsOnEmptyQueue() throws InterruptedException {
        SharedQueue queue = new SharedQueue(2);
        List<Integer> destination = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        // Consumer thread: tries to consume from empty queue
        Thread consumerThread = new Thread(() -> {
            try {
                latch.countDown(); // signal test that consumer started
                /** Consumer blocks until item is produced */
                destination.add(queue.take());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumerThread.start();
        latch.await();
        assertEquals(0, destination.size(), "Consumer should not consume from empty queue");

        // Producer thread: produces an item
        Thread producerThread = new Thread(() -> {
            try {
                queue.put(99);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();
        producerThread.join();
        consumerThread.join();

        assertEquals(List.of(99), destination, "Consumer should consume the produced item");
    }

    /**
     * Test that producer waits when the queue is full.
     * Ensures queue size never exceeds its capacity during production.
     *
     * @throws InterruptedException if threads are interrupted
     */
    @Test
    void testProducerWaitsWhenQueueFull() throws InterruptedException {
        SharedQueue queue = new SharedQueue(1);
        List<Integer> source = List.of(5, 6);
        List<Integer> destination = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        // Consumer thread: consumes all items
        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < source.size(); i++) {
                    destination.add(queue.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Producer thread: produces items, signals after first item
        Thread producerThread = new Thread(() -> {
            try {
                for (int item : source) {
                    /** Produces item; blocks if queue is full */
                    queue.put(item);
                    if (item == source.get(0)) latch.countDown();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producerThread.start();
        latch.await();
        assertTrue(queue.size() <= 1, "Queue should not exceed capacity when full");

        consumerThread.start();
        producerThread.join();
        consumerThread.join();

        assertEquals(source, destination, "All items should be consumed correctly after waiting");
    }
}

package com.assignment.producerconsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Main demonstrates the complete Producer-Consumer synchronized workflow.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        SharedQueue queue = new SharedQueue(5);

        List<Integer> source = List.of(1, 2, 3, 4, 5);
        List<Integer> destination = new ArrayList<>();

        Producer producer = new Producer(queue, source);
        Consumer consumer = new Consumer(queue, destination, source.size());

        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final destination: " + destination);
    }
}
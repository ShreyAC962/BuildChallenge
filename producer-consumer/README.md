# Producer-Consumer Pattern in Java

## Project Overview
This project implements the classic **Producer-Consumer pattern** using thread synchronization in Java. It demonstrates concurrent data transfer between a producer thread that produces data items and a consumer thread that consumes them, using a shared queue with proper synchronization.

### Short Description
- Implements **thread-safe blocking queue**
- Demonstrates **wait/notify mechanism**
- Simulates concurrent data transfer between producer and consumer threads
- Includes **unit tests** to validate synchronization and queue behavior

---

## Project Structure
```
ProducerConsumerProject/
│
├─ src/
│ ├─ main/java/com/assignment/producerconsumer/
│ │ ├─ SharedQueue.java
│ │ ├─ Producer.java
│ │ └─ Consumer.java
│ │
│ └─ test/java/test/com/assignment/producerconsumer/
│ └─ ProducerConsumerTest.java
│
├─ .gitignore
└─ README.md
```

---

## Classes

### 1. SharedQueue.java
- Implements a **blocking queue** for thread-safe producer-consumer communication
- **Methods:**
    - `produce(int value)` → Adds an item; waits if full
    - `consume()` → Removes an item; waits if empty
    - `size()` → Returns current queue size
- **Synchronization** via `synchronized`, `wait()`, and `notifyAll()`
- **Assumptions:**
    - Capacity-limited queue
    - Only integers are transferred
    - Supports multiple producers/consumers (optional extension)

### 2. Producer.java
- Reads integers from a **source list** and produces them into `SharedQueue`
- Runs as a **thread**
- **Example usage:**
```java
Thread producerThread = new Thread(new Producer(queue, sourceList));
producerThread.start();
```

### 3. Consumer.java

- Consumes integers from SharedQueue and stores them in a destination list
- Consumes a fixed number of items (matches source size)
- Runs as a thread
- **Example usage:**
```
Thread consumerThread = new Thread(new Consumer(queue, destinationList, sourceList.size()));
consumerThread.start();
```

### Unit Tests
**Test Class: ProducerConsumerTest.java**

Tests Covered:

- testCorrectDataTransfer → All produced items are consumed in order

- testQueueNeverExceedsCapacity → Queue never exceeds its capacity

- testConsumerWaitsOnEmptyQueue → Consumer blocks when queue is empty

- testProducerWaitsWhenQueueFull → Producer blocks when queue is full



#### **Notes:**

- Tests are deterministic using CountDownLatch
- No Thread.sleep() used
- Verifies thread synchronization, blocking queue behavior, and wait/notify mechanism

### Setup Instructions

Clone the repository:
``` git clone https://github.com/ShreyAC962/BuildChallenge.git
    cd BuildChallenge/producer-consumer
```


**Open in IntelliJ IDEA:**
- File → Open → Select project folder

- Add JUnit library (if not using Maven/Gradle):

- Project Structure → Libraries → Add JUnit 5 JARs

- Test class uses org.junit.jupiter.api.*

**Run Tests**:

- Right-click ProducerConsumerTest.java → Run

### Sample Output
```
Produced: 1
Produced: 2
Produced: 3
Produced: 4
Consumed: 1
Produced: 5
Consumed: 2
Consumed: 3
Consumed: 4
Consumed: 5
Final destination: [1, 2, 3, 4, 5]
```

**Note : On varying output**:
- The order of Produced and Consumed messages can vary because threads run concurrently
- Producer and consumer threads execute independently, and the CPU scheduler decides which thread runs first
  -Even though the final data transfer is consistent, the interleaving of messages in the console may differ between runs

### Assumptions & Design Choices
- The queue only stores integers for simplicity
- Producer produces all items; consumer consumes exactly that many
- Blocking behavior demonstrated with wait() and notifyAll()
- Unit tests verify both functional correctness and synchronization
- Designed to demonstrate thread-safe concurrent programming

### Learning Objectives
- Understanding Producer-Consumer pattern
- Practicing thread synchronization in Java
- Learning to implement blocking queues manually
- Writing robust unit tests for concurrent programs

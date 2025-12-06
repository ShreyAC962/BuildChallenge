# BuildChallenge Repository

Welcome to the **BuildChallenge** repository! This repository contains multiple Java projects demonstrating different programming concepts and data analysis techniques.

---

## Projects Overview

### 1. Producer-Consumer
- **Folder:** `producer-consumer/`
- **Description:** Implementation of the classic Producer-Consumer pattern in Java using threads and a synchronized blocking queue.
- **Key Concepts:**
  - Thread synchronization with `wait()` and `notifyAll()`
  - Thread-safe queues
  - Unit tests validating concurrent operations
- **Entry Point:** `Main.java` inside `producer-consumer/src/main/java/com/assignment/producerconsumer/`

### 2. Data Analysis
- **Folder:** `data-analysis/`
- **Description:** Performs sales data analysis using CSV input. Uses Java Streams and functional programming to aggregate and summarize sales.
- **Key Features:**
  - Total sales, sales by product/region
  - Top-N products, monthly sales, and average order value
  - Unit tests included for validation
- **Entry Point:** `Main.java` inside `data-analysis/src/main/java/com/assignment/salesdata/`

---

## How to Access & Run

1. **Clone the repository:**
```bash
git clone https://github.com/ShreyAC962/BuildChallenge.git
```
2. **Navigate to the desired project**:
```
cd BuildChallenge/producer-consumer
# or
cd BuildChallenge/data-analysis
```


3. **Open in your IDE (IntelliJ IDEA, Eclipse, VS Code)**

- Ensure Java JDK is installed (recommended: Java 17)
- Add required libraries (JUnit 5 for running tests)

4. **Run the projects**

- Execute Main.java for console demonstration
- Run unit tests in test/ folders to validate functionality

### **Notes**

The data-analysis project requires a CSV file inside data/ folder.

Both projects include unit tests to ensure correctness.

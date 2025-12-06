# Sales Data Analysis in Java

## Project Overview
This project demonstrates **data analysis on sales data** from a CSV file using Java. It leverages **functional programming paradigms** and **Java Streams** to perform various aggregation, grouping, and analytical operations.

The project reads a CSV containing sales records and calculates metrics such as total sales, sales by product or region, top-selling products, monthly sales, average order value, and salesperson performance.

---

## Short Description
- Performs **CSV data ingestion** using `CsvReader`
- Implements **SalesDataAnalyzer** with functional/stream operations
- Supports aggregation, grouping, sorting, and top-N queries
- Includes **JUnit 5 unit tests** to validate results
- Demonstrates best practices in **object-oriented and functional Java**

---

## Project Structure

```
SalesDataAnalysisProject/
│
├─ src/
│ ├─ main/java/com/assignment/salesdata/
│  ├─ CsvReader.java
│  ├─ SalesRecord.java
│  ├─ SalesDataAnalyzer.java
│  └─ Main.java
│ 
├─ test/java/com/assignment/salesdata/
│ └─ CsvReaderTest.java
│ └─ SalesDataAnalyzerTest.java
│
├─ data/
│ └─ sales.csv
├─ .gitignore
└─ README.md
```

---

## Classes

### 1. `SalesRecord.java`
Immutable model representing a single sales record.

**Assumptions & Notes:**
- `orderId` is unique per row.
- `orderDate` is parseable as `YYYY-MM-DD`.
- `quantity >= 0` and `unitPrice >= 0`.
- Provides a method `getSalesAmount()` for total value of the order line.

---

### 2. `CsvReader.java`
Reads a CSV file and returns a list of `SalesRecord`.

**Assumptions & Notes:**
- CSV format: `order_id,order_date,product,quantity,unit_price,region,salesperson`
- Simple split parser (`split(",")`), assumes no commas in fields.
- Skips malformed or empty rows.

---

### 3. `SalesDataAnalyzer.java`
Performs analytical operations on sales data using Java Streams:

**Methods:**
- `totalSales()` → Total sales amount
- `totalSalesByProduct()` → Sales grouped by product
- `totalSalesByRegion()` → Sales grouped by region
- `topNProductsBySales(int n)` → Top N products by sales
- `monthlySales()` → Monthly aggregated sales
- `averageOrderValue()` → Average sales per order
- `salesBySalesperson()` → Total sales per salesperson
- `ordersCountByProduct()` → Number of orders per product

**Design Choices:**
- Uses **functional programming and streams** for aggregation.
- Methods return **immutable results** (Maps and Lists).
- Easy to extend for additional queries.

---

### 4. `Main.java`
Console application demonstrating usage of `SalesDataAnalyzer`.

**Notes:**
- Reads CSV from `data/sales_data.csv`.
- Prints all analysis results to console.

---

### 5. `SalesDataAnalyzerTest.java` and `CsvReaderTest.java`
Unit tests using **JUnit 5** to validate functionality:

**Tests include:**
- `testTotalSalesNonZero()` → Total sales must be positive
- `testSalesByProductContainsWidgetA()` → Widget A exists in results
- `testMonthlySalesContainsYearMonth()` → Check March 2024 aggregation
- `testTopProductsDescending()` → Top-N products are returned correctly
- `testAverageOrderValue()` → Average order > 0
- `testSalesBySalespersonNonEmpty()` → Salesperson map is populated

**Notes:**
- CSV must be present at `data/sales.csv` relative to project root.
- Tests fail clearly if CSV is missing.

---

## Setup Instructions

1. **Clone repository:**

```
git clone https://github.com/ShreyAC962/BuildChallenge.git
cd BuildChallenge/data-analysis
```
2. Add CSV data
- Place sales_data.csv inside data/ folder.
3. Open in IntelliJ IDEA / Eclipse / VS Code
- Ensure JUnit 5 is added to project libraries.
4. Run Main.java
- Outputs analysis results to console.
5. Run Unit Tests
- SalesDataAnalyzerTest.java validates all analytical methods.
- CsvReaderTest.java validate CSV reading and parsing.


## Sample CSV Format
```
order_id,order_date,product,quantity,unit_price,region,salesperson
1001,2024-01-15,Widget A,10,9.99,North,Jane Doe
1002,2024-01-18,Widget B,5,19.99,South,John Smith
1003,2024-02-03,Widget A,7,9.99,East,Jane Doe
1004,2024-02-10,Widget C,3,29.99,West,Emily Jones
...

```

## Sample Output
```dtd
Total Sales: $5977.98

--- Sales by Product ---
Widget A: $709.29
Widget B: $1459.27
Widget C: $1109.63
Widget D: $1499.85
Widget E: $1199.94

--- Sales by Region ---
West: $1149.76
South: $1649.51
North: $1929.30
East: $1249.41

--- Top 3 Products ---
1. Widget D
2. Widget B
3. Widget E

--- Monthly Sales ---
2024-01: $199.85
2024-02: $159.90
2024-03: $479.82
2024-04: $559.85
2024-05: $629.73
2024-06: $789.81
2024-07: $879.72
2024-08: $569.80
2024-09: $1399.70
2024-10: $309.80

Average Order Value: $199.27

--- Sales by Salesperson ---
John Smith: $1459.27
Emily Jones: $1109.63
Alex Kim: $2699.79
Jane Doe: $709.29

```

**Note: Output may vary slightly depending on CSV data.**

### Learning Objectives
- Understand functional programming in Java
- Perform data aggregation and grouping with streams
- Work with immutable model classes
- Implement unit tests for data analysis
- Handle CSV file I/O and parsing

### Assumptions & Design Choices

- CSV contains well-formed data without missing fields.
- Quantity and unit price are numeric and non-negative.
- Analysis focuses on totals, averages, and top-N rankings.
- Code is modular for easy extension and testing.

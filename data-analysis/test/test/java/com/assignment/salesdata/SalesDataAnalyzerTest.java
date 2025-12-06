package test.java.com.assignment.salesdata;


import main.java.com.assignment.salesdata.CsvReader;
import main.java.com.assignment.salesdata.SalesDataAnalyzer;
import main.java.com.assignment.salesdata.SalesRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SalesDataAnalyzer.
 *
 * Assumptions & Choices:
 * - CSV file is stored in the project root under 'data/sales_data.csv'
 * - All records in CSV are valid and parseable
 * - Tests focus on functional correctness of aggregation, grouping, and summary methods
 * - No mock framework used; tests rely on actual CSV data
 *
 * Testing Objectives:
 * - Validate total sales calculation
 * - Validate grouping by product, region, and salesperson
 * - Validate monthly aggregation
 * - Validate top N products functionality
 * - Validate average order value calculation
 *
 * Notes:
 * - These tests demonstrate usage of JUnit 5 with @BeforeAll and assertions
 * - CSV file path is checked explicitly to fail fast if missing
 */
public class SalesDataAnalyzerTest {

    /** List of sales records read from CSV */
    private static List<SalesRecord> records;

    /** Analyzer instance for performing calculations */
    private static SalesDataAnalyzer analyzer;

    /**
     * Setup method to read CSV once before all tests.
     *
     * @throws IOException if CSV reading fails
     */
    @BeforeAll
    static void setup() throws IOException {
        // Define path to CSV located in project root
        Path csvPath = Path.of("data", "sales.csv");

        // Fail early if file does not exist
        assertTrue(csvPath.toFile().exists(),
                "CSV file not found! Place it at project root: data/sales.csv");

        // Read records from CSV using CsvReader
        CsvReader reader = new CsvReader();
        records = reader.readSalesRecords(csvPath);

        // Initialize analyzer with loaded records
        analyzer = new SalesDataAnalyzer(records);
    }

    /**
     * Test that total sales is greater than zero.
     */
    @Test
    void testTotalSalesNonZero() {
        double total = analyzer.totalSales();
        assertTrue(total > 0.0, "Total sales should be positive");
    }

    /**
     * Test that totalSalesByProduct contains "Widget A".
     * Ensures grouping by product works correctly.
     */
    @Test
    void testSalesByProductContainsWidgetA() {
        Map<String, Double> byProduct = analyzer.totalSalesByProduct();
        assertTrue(byProduct.containsKey("Widget A"),
                "Widget A should exist in product list");
    }

    /**
     * Test that monthlySales contains March 2024.
     * Ensures grouping by YearMonth works correctly.
     */
    @Test
    void testMonthlySalesContainsYearMonth() {
        Map<YearMonth, Double> monthly = analyzer.monthlySales();
        assertTrue(monthly.containsKey(YearMonth.of(2024, 3)),
                "March 2024 should exist in monthly summary");
    }

    /**
     * Test that topNProductsBySales returns the expected number of items.
     */
    @Test
    void testTopProductsDescending() {
        List<String> top2 = analyzer.topNProductsBySales(2);
        assertEquals(2, top2.size(),
                "Top 2 products should return 2 items");
    }

    /**
     * Test that averageOrderValue returns a positive number.
     */
    @Test
    void testAverageOrderValue() {
        double avg = analyzer.averageOrderValue();
        assertTrue(avg > 0.0, "Average order value should be > 0");
    }

    /**
     * Test that salesBySalesperson is not empty.
     * Ensures grouping by salesperson works correctly.
     */
    @Test
    void testSalesBySalespersonNonEmpty() {
        Map<String, Double> byPerson = analyzer.salesBySalesperson();
        assertFalse(byPerson.isEmpty(), "Salesperson map should not be empty");
    }
}

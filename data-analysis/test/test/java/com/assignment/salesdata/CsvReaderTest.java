package test.java.com.assignment.salesdata;

import main.java.com.assignment.salesdata.CsvReader;
import main.java.com.assignment.salesdata.SalesRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CsvReader.
 *
 * Assumptions & Choices:
 * - CSV file is stored in the project root under 'data/sales_data.csv'
 * - CSV is well-formed with 7 columns: order_id,order_date,product,quantity,unit_price,region,salesperson
 * - All rows are parseable into SalesRecord objects
 * - No mock framework is used; tests rely on actual CSV data
 *
 * Testing Objectives:
 * - Validate CSV reading and parsing into SalesRecord instances
 * - Ensure correct parsing of types (String, LocalDate, int, double)
 * - Verify that all records are loaded
 * - Validate computed sales amounts
 *
 * Notes:
 * - Demonstrates usage of JUnit 5 with @BeforeAll
 * - CSV path is checked to fail fast if missing
 */
public class CsvReaderTest {

    /** List of SalesRecord loaded from CSV */
    private static List<SalesRecord> records;

    /** CsvReader instance for reading CSV */
    private static CsvReader reader;

    /**
     * Setup method to initialize CsvReader and load CSV once for all tests.
     *
     * @throws IOException if CSV reading fails
     */
    @BeforeAll
    static void setup() throws IOException {
        // Initialize reader
        reader = new CsvReader();

        // Path to CSV in project root
        Path csvPath = Path.of("data", "sales.csv");

        // Fail fast if CSV file is missing
        assertTrue(csvPath.toFile().exists(),
                "CSV file not found! Place it at project root: data/sales.csv");

        // Read all records from CSV
        records = reader.readSalesRecords(csvPath);
    }

    /**
     * Test that CSV reading returns a non-empty list.
     */
    @Test
    void testRecordsNotEmpty() {
        assertFalse(records.isEmpty(), "CSV records should not be empty");
    }

    /**
     * Test that the first record has expected non-null fields.
     * Ensures correct parsing of each column.
     */
    @Test
    void testFirstRecordFieldsNotNull() {
        SalesRecord first = records.get(0);

        assertNotNull(first.getOrderId(), "Order ID should not be null");
        assertNotNull(first.getOrderDate(), "Order date should not be null");
        assertNotNull(first.getProduct(), "Product should not be null");
        assertTrue(first.getQuantity() > 0, "Quantity should be positive");
        assertTrue(first.getUnitPrice() > 0, "Unit price should be positive");
        assertNotNull(first.getRegion(), "Region should not be null");
        assertNotNull(first.getSalesperson(), "Salesperson should not be null");
    }

    /**
     * Test that the first record values match expected sample data.
     * Ensures CSV is read deterministically.
     */
    @Test
    void testFirstRecordValues() {
        SalesRecord first = records.get(0);

        assertEquals("1001", first.getOrderId(), "First order ID mismatch");
        assertEquals(LocalDate.of(2024, 1, 15), first.getOrderDate(), "First order date mismatch");
        assertEquals("Widget A", first.getProduct(), "First product mismatch");
        assertEquals(10, first.getQuantity(), "First quantity mismatch");
        assertEquals(9.99, first.getUnitPrice(), 0.001, "First unit price mismatch");
    }

    /**
     * Test that all SalesRecord objects have positive sales amount.
     */
    @Test
    void testAllSalesAmountsPositive() {
        for (SalesRecord record : records) {
            assertTrue(record.getSalesAmount() > 0,
                    "Sales amount must be positive for order: " + record.getOrderId());
        }
    }

    /**
     * Test that number of records matches expected minimum.
     * Ensures CSV reader did not skip rows unexpectedly.
     */
    @Test
    void testRecordCountAtLeastMinimum() {
        assertTrue(records.size() >= 10, "Should read at least 10 records from sample CSV");
    }
}

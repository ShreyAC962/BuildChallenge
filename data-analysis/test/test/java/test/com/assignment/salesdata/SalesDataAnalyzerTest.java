package test.java.test.com.assignment.salesdata;


import com.assignment.salesanalyzer.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SalesDataAnalyzer. Uses the sample CSV data file.
 *
 * Ensure JUnit 5 is on classpath when running tests.
 */
public class SalesDataAnalyzerTest {

    private static List<SalesRecord> records;
    private static SalesDataAnalyzer analyzer;

    @BeforeAll
    static void setup() throws IOException {
        CsvReader reader = new CsvReader();
        records = reader.readSalesRecords(Path.of("data", "sales_data.csv"));
        analyzer = new SalesDataAnalyzer(records);
    }

    @Test
    void testTotalSalesNonZero() {
        double total = analyzer.totalSales();
        assertTrue(total > 0.0, "Total sales should be positive");
    }

    @Test
    void testSalesByProductContainsWidgetA() {
        Map<String, Double> byProduct = analyzer.totalSalesByProduct();
        assertTrue(byProduct.containsKey("Widget A"));
        assertEquals( (10*9.99 + 7*9.99 + 4*9.99), byProduct.get("Widget A"), 0.0001);
    }

    @Test
    void testMonthlySalesContainsYearMonth() {
        Map<YearMonth, Double> monthly = analyzer.monthlySales();
        assertTrue(monthly.containsKey(YearMonth.of(2024, 3)));
    }

    @Test
    void testTopProductsDescending() {
        List<String> top2 = analyzer.topNProductsBySales(2);
        assertEquals(2, top2.size());
    }

    @Test
    void testAverageOrderValue() {
        double avg = analyzer.averageOrderValue();
        assertTrue(avg > 0.0);
    }

    @Test
    void testSalesBySalespersonNonEmpty() {
        Map<String, Double> bySalesperson = analyzer.salesBySalesperson();
        assertTrue(bySalesperson.containsKey("Jane Doe"));
    }
}

package main.java.com.assignment.salesdata;

import java.nio.file.Path;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

/**
 * Simple console demo to run analyses and print results.
 *
 * Run this class after placing data/sales_data.csv in project root.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Path csvPath = Path.of("data", "sales.csv");
        CsvReader reader = new CsvReader();
        List<SalesRecord> records = reader.readSalesRecords(csvPath);

        SalesDataAnalyzer analyzer = new SalesDataAnalyzer(records);

        System.out.printf("Total Sales: $%.2f%n", analyzer.totalSales());
        System.out.println("--- Sales by Product ---");
        analyzer.totalSalesByProduct().forEach((p, v) -> System.out.printf("%s: $%.2f%n", p, v));

        System.out.println("\n--- Sales by Region ---");
        analyzer.totalSalesByRegion().forEach((r, v) -> System.out.printf("%s: $%.2f%n", r, v));

        System.out.println("\n--- Top 3 Products ---");
        List<String> top3 = analyzer.topNProductsBySales(3);
        for (int i = 0; i < top3.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, top3.get(i));
        }

        System.out.println("\n--- Monthly Sales ---");
        Map<YearMonth, Double> monthly = analyzer.monthlySales();
        monthly.forEach((m, v) -> System.out.printf("%s: $%.2f%n", m, v));

        System.out.printf("%nAverage Order Value: $%.2f%n", analyzer.averageOrderValue());

        System.out.println("\n--- Sales by Salesperson ---");
        analyzer.salesBySalesperson().forEach((s, v) -> System.out.printf("%s: $%.2f%n", s, v));
    }
}

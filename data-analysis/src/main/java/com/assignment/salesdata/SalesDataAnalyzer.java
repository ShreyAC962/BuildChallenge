package main.java.com.assignment.salesdata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Performs various analysis on sales data using functional programming and streams.
 *
 * Assumptions:
 * - CSV file is correctly formatted
 * - Each row represents a single sales record
 */
public class SalesDataAnalyzer {
    private List<SalesRecord> salesRecords;

    /**
     * Constructor reads CSV data into SalesRecord objects
     * @param csvFilePath path to CSV file
     * @throws IOException if file not found or read error
     */
    public SalesDataAnalyzer(String csvFilePath) throws IOException {
        salesRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String date = parts[0];
                String region = parts[1];
                String product = parts[2];
                int quantity = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);
                salesRecords.add(new SalesRecord(date, region, product, quantity, price));
            }
        }
    }

    /** Get all sales records */
    public List<SalesRecord> getSalesRecords() {
        return salesRecords;
    }

    /** Total sales per product */
    public Map<String, Double> totalSalesByProduct() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(SalesRecord::getProduct,
                        Collectors.summingDouble(SalesRecord::getTotal)));
    }

    /** Total quantity sold per region */
    public Map<String, Integer> totalQuantityByRegion() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(SalesRecord::getRegion,
                        Collectors.summingInt(SalesRecord::getQuantity)));
    }

    /** Average sale amount per product */
    public Map<String, Double> averageSalePerProduct() {
        return salesRecords.stream()
                .collect(Collectors.groupingBy(SalesRecord::getProduct,
                        Collectors.averagingDouble(SalesRecord::getTotal)));
    }

    /** Top selling product by total sales */
    public Optional<Map.Entry<String, Double>> topProductBySales() {
        return totalSalesByProduct().entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }
}

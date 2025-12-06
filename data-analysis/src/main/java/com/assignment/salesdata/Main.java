package main.java.com.assignment.salesdata;

import java.io.IOException;

/**
 * Demonstrates the SalesDataAnalyzer usage and prints results.
 */
public class Main {
    public static void main(String[] args) {
        try {
            SalesDataAnalyzer analyzer = new SalesDataAnalyzer("data/sales.csv");

            System.out.println("=== Total Sales by Product ===");
            analyzer.totalSalesByProduct().forEach((product, total) ->
                    System.out.println(product + ": " + total));

            System.out.println("\n=== Total Quantity by Region ===");
            analyzer.totalQuantityByRegion().forEach((region, qty) ->
                    System.out.println(region + ": " + qty));

            System.out.println("\n=== Average Sale per Product ===");
            analyzer.averageSalePerProduct().forEach((product, avg) ->
                    System.out.println(product + ": " + avg));

            System.out.println("\n=== Top Product by Sales ===");
            analyzer.topProductBySales().ifPresent(entry ->
                    System.out.println(entry.getKey() + ": " + entry.getValue()));

        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
}


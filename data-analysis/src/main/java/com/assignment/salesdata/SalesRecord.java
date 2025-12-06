package main.java.com.assignment.salesdata;


/**
 * Represents a single sales record from the CSV file.
 *
 * Assumptions:
 * - CSV contains columns: Date, Region, Product, Quantity, Price
 * - Quantity and Price are numeric
 * - Date format is "yyyy-MM-dd"
 */
public class SalesRecord {
    private String date;
    private String region;
    private String product;
    private int quantity;
    private double price;

    /**
     * Constructor to initialize a sales record
     * @param date date of sale
     * @param region region of sale
     * @param product product name
     * @param quantity quantity sold
     * @param price price per unit
     */
    public SalesRecord(String date, String region, String product, int quantity, double price) {
        this.date = date;
        this.region = region;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters
    public String getDate() { return date; }
    public String getRegion() { return region; }
    public String getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    /** Returns total sale amount */
    public double getTotal() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return date + "," + region + "," + product + "," + quantity + "," + price;
    }
}

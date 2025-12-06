package main.java.com.assignment.salesdata;

import java.time.LocalDate;

/**
 * Immutable model class representing a single sales record (one order line).
 *
 * Assumptions:
 * - orderId is unique per row in CSV
 * - orderDate is parseable as YYYY-MM-DD
 * - quantity >= 0 and unitPrice >= 0
 */
public final class SalesRecord {
    private final String orderId;
    private final LocalDate orderDate;
    private final String product;
    private final int quantity;
    private final double unitPrice;
    private final String region;
    private final String salesperson;

    /**
     * Constructor to create a SalesRecord.
     *
     * @param orderId    order identifier
     * @param orderDate  order date (LocalDate)
     * @param product    product name
     * @param quantity   quantity sold
     * @param unitPrice  price per unit
     * @param region     sales region
     * @param salesperson salesperson name
     */
    public SalesRecord(String orderId, LocalDate orderDate, String product,
                       int quantity, double unitPrice, String region, String salesperson) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.region = region;
        this.salesperson = salesperson;
    }

    public String getOrderId() { return orderId; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public String getRegion() { return region; }
    public String getSalesperson() { return salesperson; }

    /**
     * Convenience: compute total sales amount for this row.
     *
     * @return quantity * unitPrice
     */
    public double getSalesAmount() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return "SalesRecord{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", region='" + region + '\'' +
                ", salesperson='" + salesperson + '\'' +
                '}';
    }
}

package main.java.com.assignment.salesdata;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * SalesDataAnalyzer performs streaming/functional analyses on sales records.
 *
 * Methods are implemented with Java Streams and are unit-testable.
 */
public class SalesDataAnalyzer {

    private final List<SalesRecord> records;

    /**
     * Constructor to build analyzer from a list of sales records.
     *
     * @param records list of SalesRecord (non-null)
     */
    public SalesDataAnalyzer(List<SalesRecord> records) {
        this.records = Objects.requireNonNull(records, "records must not be null");
    }

    /**
     * Total sales sum across all records.
     *
     * @return total sales amount
     */
    public double totalSales() {
        return records.stream()
                .mapToDouble(SalesRecord::getSalesAmount)
                .sum();
    }

    /**
     * Total sales grouped by product.
     *
     * @return Map product -> total sales
     */
    public Map<String, Double> totalSalesByProduct() {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getProduct,
                        Collectors.summingDouble(SalesRecord::getSalesAmount)
                ));
    }

    /**
     * Total sales grouped by region.
     *
     * @return Map region -> total sales
     */
    public Map<String, Double> totalSalesByRegion() {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getRegion,
                        Collectors.summingDouble(SalesRecord::getSalesAmount)
                ));
    }

    /**
     * Top N products by sales amount (descending).
     *
     * @param n number of top products to return
     * @return list of product names sorted by sales desc
     */
    public List<String> topNProductsBySales(int n) {
        return totalSalesByProduct().entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue(Comparator.reverseOrder()))
                .limit(n)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Monthly sales totals (YearMonth -> total).
     *
     * @return map of YearMonth to total sales
     */
    public Map<YearMonth, Double> monthlySales() {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> YearMonth.from(r.getOrderDate()),
                        Collectors.summingDouble(SalesRecord::getSalesAmount)
                ));
    }

    /**
     * Average order value across orders (average sales amount per order).
     *
     * @return average sales amount per order (0.0 if no records)
     */
    public double averageOrderValue() {
        return records.stream()
                .mapToDouble(SalesRecord::getSalesAmount)
                .average()
                .orElse(0.0);
    }

    /**
     * Sales totals grouped by salesperson.
     *
     * @return Map salesperson -> total sales
     */
    public Map<String, Double> salesBySalesperson() {
        return records.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getSalesperson,
                        Collectors.summingDouble(SalesRecord::getSalesAmount)
                ));
    }

    /**
     * Count of orders per product.
     *
     * @return Map product -> order count
     */
    public Map<String, Long> ordersCountByProduct() {
        return records.stream()
                .collect(Collectors.groupingBy(SalesRecord::getProduct, Collectors.counting()));
    }

    /**
     * Generic helper to compute top K keys from a Map by value descending.
     *
     * @param map map of key->value
     * @param k   top k
     * @param <K> key type
     * @return list of keys top k by value
     */
    public static <K> List<K> topKByValue(Map<K, Double> map, int k) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<K, Double>comparingByValue(Comparator.reverseOrder()))
                .limit(k)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

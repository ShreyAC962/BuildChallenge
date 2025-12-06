package main.java.com.assignment.salesdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple CSV reader for the expected sales CSV format:
 * order_id,order_date,product,quantity,unit_price,region,salesperson
 *
 * Note: this uses a simple split(",") parser and assumes no commas inside fields.
 * For production, use a CSV library (OpenCSV or Apache Commons CSV) for robustness.
 */
public class CsvReader {

    /**
     * Reads a CSV file and returns list of SalesRecord.
     *
     * @param csvPath path to CSV file
     * @return list of SalesRecord
     * @throws IOException if reading fails
     */
    public List<SalesRecord> readSalesRecords(Path csvPath) throws IOException {
        List<SalesRecord> rows = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(csvPath)) {
            String header = br.readLine(); // skip header
            if (header == null) {
                return rows;
            }
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1); // keep empty fields
                if (parts.length < 7) {
                    // skip malformed row or log - for now skip
                    continue;
                }
                String orderId = parts[0].trim();
                LocalDate orderDate = LocalDate.parse(parts[1].trim());
                String product = parts[2].trim();
                int quantity = Integer.parseInt(parts[3].trim());
                double unitPrice = Double.parseDouble(parts[4].trim());
                String region = parts[5].trim();
                String salesperson = parts[6].trim();

                rows.add(new SalesRecord(orderId, orderDate, product, quantity, unitPrice, region, salesperson));
            }
        }
        return rows;
    }
}

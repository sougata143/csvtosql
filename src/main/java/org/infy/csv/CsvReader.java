package org.infy.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvReader {
    private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

    private final String filePath;
    private String[] headers;
    private List<String[]> records;

    public CsvReader(String filePath) {
        this.filePath = filePath;
    }

    public void read() throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            logger.info("Reading CSV file: {}", filePath);
            headers = reader.readNext(); // Read headers
            records = reader.readAll();   // Read all records
            logger.info("Successfully read {} records", records.size());
        }
    }

    public String[] getHeaders() {
        return headers;
    }

    public List<String[]> getRecords() {
        return records;
    }
} 
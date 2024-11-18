package org.infy;

import org.infy.csv.CsvReader;
import org.infy.db.DatabaseType;
import org.infy.db.SqlGenerator;
import org.infy.util.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;

@Command(name = "csvtosql", mixinStandardHelpOptions = true, version = "1.0",
        description = "Converts CSV files to SQL insert statements.")
public class Main implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Parameters(index = "0", description = "The CSV file to process")
    private String csvFile;

    @Option(names = {"-d", "--database"}, description = "Database type (mysql, postgresql, oracle, sqlserver)")
    private String databaseType = "mysql";

    @Option(names = {"-t", "--table"}, description = "Target table name (optional, defaults to CSV filename)")
    private String tableName;

    @Option(names = {"-o", "--output"}, description = "Output SQL file path")
    private String outputFile;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        try {
            logger.info("Starting CSV to SQL conversion for file: {}", csvFile);
            
            // Initialize CSV reader
            CsvReader csvReader = new CsvReader(csvFile);
            csvReader.read();

            // Determine table name if not provided
            if (tableName == null) {
                tableName = StringUtils.getTableNameFromFile(csvFile);
            }

            // Initialize SQL generator
            DatabaseType dbType = DatabaseType.fromString(databaseType);
            String[] sanitizedHeaders = Arrays.stream(csvReader.getHeaders())
                    .map(StringUtils::sanitizeColumnName)
                    .toArray(String[]::new);
            SqlGenerator sqlGenerator = new SqlGenerator(dbType, tableName, sanitizedHeaders);

            // Generate SQL statements
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile != null ? outputFile : csvFile + ".sql"))) {
                for (String[] record : csvReader.getRecords()) {
                    String sql = sqlGenerator.generateInsertStatement(record);
                    writer.println(sql);
                }
            }

            logger.info("Conversion completed successfully");
        } catch (Exception e) {
            logger.error("Error during conversion: {}", e.getMessage(), e);
        }
    }
}
package org.infy.db;

import org.infy.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class SqlGenerator {
    private static final Logger logger = LoggerFactory.getLogger(SqlGenerator.class);

    private final DatabaseType databaseType;
    private final String tableName;
    private final String[] columns;

    public SqlGenerator(DatabaseType databaseType, String tableName, String[] columns) {
        this.databaseType = databaseType;
        this.tableName = tableName;
        this.columns = columns;
    }

    public String generateInsertStatement(String[] values) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
           .append(tableName)
           .append(" (")
           .append(String.join(", ", columns))
           .append(") VALUES (");

        String valuesList = java.util.Arrays.stream(values)
                .map(this::formatValue)
                .collect(Collectors.joining(", "));

        sql.append(valuesList).append(");");
        return sql.toString();
    }

    private String formatValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return "NULL";
        }
        // Basic SQL injection prevention
        value = value.replace("'", "''");
        return "'" + value + "'";
    }
} 
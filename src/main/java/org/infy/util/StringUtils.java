package org.infy.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StringUtils {
    
    public static String getTableNameFromFile(String filePath) {
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        // Remove file extension and sanitize for SQL table name
        return fileName.replaceFirst("[.][^.]+$", "") // Remove extension
                      .replaceAll("[^a-zA-Z0-9_]", "_") // Replace invalid chars with underscore
                      .toLowerCase();
    }

    public static String sanitizeColumnName(String columnName) {
        return columnName.trim()
                        .replaceAll("[^a-zA-Z0-9_]", "_")
                        .toLowerCase();
    }
} 
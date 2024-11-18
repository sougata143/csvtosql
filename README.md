# CSV to SQL Converter

A command-line utility that converts CSV files to SQL insert statements. This tool helps in bulk data migration by converting CSV data into SQL insert statements compatible with various database systems.

## Features

- Converts CSV files to SQL INSERT statements
- Supports multiple database types (MySQL, PostgreSQL, Oracle, SQL Server)
- Automatic table name generation from CSV filename
- Configurable output file location
- Handles null values and basic data sanitization
- SQL injection prevention
- Comprehensive logging

## Prerequisites

- Java 21 or higher (LTS version)
- Maven 3.6 or higher

## Building the Project

Clone the repository and build using Maven: 
# Clean and package the project with dependencies
mvn clean package

# The executable JAR will be created in the target directory with two files:
# 1. CsvtoSql-1.0-SNAPSHOT.jar (without dependencies)
# 2. CsvtoSql-1.0-SNAPSHOT-jar-with-dependencies.jar (with all dependencies included)

## Running the Project

# Basic usage
java -jar target/CsvtoSql-1.0-SNAPSHOT-jar-with-dependencies.jar input.csv

# With options
java -jar target/CsvtoSql-1.0-SNAPSHOT-jar-with-dependencies.jar input.csv -d mysql -t mytable -o output.sql
package com.mathverse.legacy.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private static final String URL = System.getenv().getOrDefault(
            "LEGACY_DB_URL", "jdbc:postgresql://localhost:5432/math_verse_db");
    private static final String USERNAME = System.getenv().getOrDefault(
            "LEGACY_DB_USERNAME", "math_user");
    private static final String PASSWORD = System.getenv().getOrDefault(
            "LEGACY_DB_PASSWORD", "admin");

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("PostgreSQL JDBC-драйвер не найден: " + e.getMessage());
        }
    }

    private DatabaseConfig() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
package com.ems.it;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

final class ConnectionManager {
    private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
    private static final String CONNECTION_URI = "jdbc:oracle:thin:@" +
            System.getProperty("ems.database.uri") + ":o11g";

    private static final String DATABASE_USERNAME = System.getProperty("ems.database.username");
    private static final String DATABASE_PASSWORD = System.getProperty("ems.database.password");

    private ConnectionManager() {
        throw new AssertionError("This class is not meant to be instantiated");
    }

    public static Optional<Connection> getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(CONNECTION_URI, DATABASE_USERNAME, DATABASE_PASSWORD);

        } catch (final ClassNotFoundException driverNotFound) {
            System.err.println("Oracle driver not found in classpath");
            return Optional.empty();
        } catch (final SQLException badConnectionAttempt) {
            System.err.println("Failed to create the database connection");
            return Optional.empty();
        }

        return Optional.of(connection);
    }

}

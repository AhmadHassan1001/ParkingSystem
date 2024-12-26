package com.database.parking.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnection {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/parking";
    private final String username = "root";
    private final String password = "root";

    private static Connection connection;

    public MySQLConnection() {
        System.out.println("Connecting to MySQL database...");
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // add destructor to close connection
    @Override
    protected void finalize() throws Throwable {
        connection.close();
    }

    /**
     * Creates a new SQL statement.
     *
     * @return a new SQL statement
     * @example
     * MySQLConnection mySQLConnection = new MySQLConnection();
     * Statement statement = mySQLConnection.createStatement();
     * ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
     */
    public Statement createStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
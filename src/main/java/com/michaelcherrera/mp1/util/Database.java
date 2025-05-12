package com.michaelcherrera.mp1.util;

import com.mysql.cj.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class provides access to the database required for this application.
 *
 * @author Michael C. Herrera
 */
public class Database {

    /**
     * Opens a database connection.
     *
     * @return a database connection
     * @throws SQLException if there is a database access error or other errors
     */
    public static Connection connection() throws SQLException {

        // Load the Driver. This is required for the DriverManager to connect to the database.
        @SuppressWarnings("unused") Driver driver = new Driver();
        String username = "root";
        String password = "root";
        String ip = "localhost";
        String database = "nyse";
        String url = "jdbc:mysql://" + ip + ":8889/" + database + "?allowPublicKeyRetrieval=true&useSSL=false";

        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Closes a database connection.
     *
     * @param connection the connection
     * @throws SQLException if there is a database access error or other errors
     */
    @SuppressWarnings("unused")
    public static void closeConnection(Connection connection) throws SQLException {

        connection.close();
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = connection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

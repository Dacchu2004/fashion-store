package com.fashionstore.util;

import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String URL;
    private static String USERNAME;
    private static String PASSWORD;

    // Called once at app startup from a ServletContextListener
    public static void init(ServletContext context) {
        URL      = context.getInitParameter("DB_URL");
        USERNAME = context.getInitParameter("DB_USERNAME");
        PASSWORD = context.getInitParameter("DB_PASSWORD");
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database Connected Successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver Not Found");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database Connection Failed");
            e.printStackTrace();
        }
        return connection;
    }
}

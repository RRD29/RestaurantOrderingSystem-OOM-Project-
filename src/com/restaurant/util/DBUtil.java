package com.restaurant.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
private static final String URL = "jdbc:mysql://localhost:3306/restaurant?serverTimezone=UTC";
private static final String USER = "root";
private static final String PASS = "your_mysql_password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Connecting to DB...");
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        System.out.println("Connected!");
        return conn;
    }

    public static void main(String[] args) throws SQLException {
    Connection c = DBUtil.getConnection();
    System.out.println("Test connection successful!");
}

}


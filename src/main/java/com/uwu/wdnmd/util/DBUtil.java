package com.uwu.wdnmd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        try {
            String url = "jdbc:mysql://localhost:3306/jsp";
            String user = "root";
            String password = "P@ssw0rd";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}

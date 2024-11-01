package com.uwu.wdnmd.dao;

import com.uwu.wdnmd.model.User;
import com.uwu.wdnmd.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    private static User user;

    public static User getUser(String username, String password) {
        user = null;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM user WHERE username=? AND password=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = new User();
                        user.username = rs.getString("username");
                        user.password = rs.getString("password");
                        user.role = rs.getString("role");
                    }
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean exists(String username) {
        boolean exists = false;
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet re = ps.executeQuery()) {
                    if (re.next()) {
                        exists = true;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exists;
    }

    public static boolean addUser(User user) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user.username);
                ps.setString(2, user.password);
                ps.setString(3, user.email);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

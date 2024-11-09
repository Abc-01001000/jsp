package com.uwu.wdnmd.dao;

import com.uwu.wdnmd.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.uwu.wdnmd.util.DatabaseConnectionPool.ds;

public class UserDao {

    public static User getUser(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        ArrayList<String> args = new ArrayList<>();
        args.add(username);
        args.add(password);

        ArrayList<User> users = startQuery(sql, args);
        if (users != null && !users.isEmpty())
            return users.getFirst();
        return null;
    }

    public static ArrayList<User> getUsers() {
        String sql = "SELECT * FROM user";
        ArrayList<String> args = new ArrayList<>();
        return startQuery(sql, args);
    }

    public static void deleteUser(int user_id) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        ArrayList<String> args = new ArrayList<>();
        args.add(String.valueOf(user_id));
        startDelete(sql, args);
    }

    public static boolean exists(String username) {
        String sql = "SELECT * FROM user WHERE username =?";
        ArrayList<String> args = new ArrayList<>();
        args.add(username);
        ArrayList<User> users = startQuery(sql, args);
        return users!= null && !users.isEmpty();
    }

    public static boolean addUser(User user) throws SQLException {
        try (Connection conn = ds.getConnection()) {
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

    public static boolean updateUser(User user) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            String sql = "UPDATE user SET username =?, password =?, email =? WHERE user_id =?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, user.username);
                ps.setString(2, user.password);
                ps.setString(3, user.email);
                ps.setInt(4, user.user_id);
                ps.executeUpdate();
                if (updateBlogAuthor(user.user_id, user.username))
                    return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean updateBlogAuthor(int user_id, String username) throws SQLException {
        String sql = "UPDATE blog SET author = ? WHERE author_id = ?";
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setInt(2, user_id);
                ps.executeUpdate();
                return true;
            }
        }
    }

    protected static ArrayList<User> startQuery(String sql, ArrayList<String> args) {
        ArrayList<User> users = new ArrayList<>();
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
               for (int i = 0; i < args.size(); i++) {
                   ps.setString(i + 1, args.get(i));
               }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        User user = new User();
                        user.user_id = rs.getInt("user_id");
                        user.username = rs.getString("username");
                        user.password = rs.getString("password");
                        user.role = rs.getString("role");
                        users.add(user);
                    }
                    return users;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void startDelete(String sql, ArrayList<String> args) {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < args.size(); i++) {
                    ps.setString(i + 1, args.get(i));
                }
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

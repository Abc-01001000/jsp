package com.uwu.wdnmd.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.*;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

class User {
    int id;
    String name;
    String email;
    String pwd;
}

@WebServlet(name = "Test", value = "/test")
public class Test extends HttpServlet {
    private String username = "root";
    private String password = "P@ssw0rd";
    private String url = "jdbc:mysql://localhost:3306/jsp";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "SELECT * FROM account";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    ArrayList<User> users = new ArrayList<>();
                    while (rs.next()) {
                        User user = new User();
                        user.id = rs.getInt("id");
                        user.name = rs.getString("name");
                        user.email = rs.getString("email");
                        user.pwd = rs.getString("pwd");
                        users.add(user);
                    }
                    Gson gson = new Gson();
                    String json = gson.toJson(users);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

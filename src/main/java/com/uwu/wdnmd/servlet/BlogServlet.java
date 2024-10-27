package com.uwu.wdnmd.servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.uwu.wdnmd.util.DBUtil;
import com.uwu.wdnmd.model.Blog;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "blogServlet", value = "/blog-servlet")
public class BlogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (Connection conn = DBUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM blog")) {
                try (ResultSet rs = ps.executeQuery()) {
                    ArrayList<Blog> blogs = new ArrayList<>();
                    while (rs.next()) {
                        Blog blog = new Blog();
                        blog.blog_id = rs.getInt("blog_id");
                        blog.title = rs.getString("title");
                        blog.description = rs.getString("description");
                        blog.author = rs.getString("author");
                        blog.url = rs.getString("url");
                        blog.view = rs.getInt("view");
                        blog.likes = rs.getInt("likes");
                        blog.star = rs.getInt("star");
                        blogs.add(blog);
                    }

                    Gson gson = new Gson();
                    String json = gson.toJson(blogs);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // post blogs
    }
}

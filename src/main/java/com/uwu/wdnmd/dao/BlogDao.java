package com.uwu.wdnmd.dao;

import com.uwu.wdnmd.model.Blog;
import com.uwu.wdnmd.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BlogDao {
    private static ArrayList<Blog> blogs;

    public static ArrayList<Blog> getBlogs() {
        blogs = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM blog";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Blog blog = new Blog();
                        blog.blog_id = rs.getInt("blog_id");
                        blog.title = rs.getString("title");
                        blog.author = rs.getString("author");
                        blog.description = rs.getString("description");
                        blog.url = rs.getString("url");
                        blog.view = rs.getInt("view");
                        blog.likes = rs.getInt("likes");
                        blog.star = rs.getInt("star");
                        blogs.add(blog);
                    }
                    return blogs;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

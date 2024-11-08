package com.uwu.wdnmd.dao;

import com.uwu.wdnmd.model.Blog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.uwu.wdnmd.util.DatabaseConnectionPool.ds;

public class BlogDao {

    public static ArrayList<Blog> getBlogs() {
        String sql = "SELECT * FROM blog";
        ArrayList<String> args = new ArrayList<>();
        return startQuery(sql, args);
    }

    public static Blog getBlog(int blog_id) {
        String sql = "SELECT * FROM blog WHERE blog_id = ?";
        ArrayList<String> args = new ArrayList<>();
        args.add(String.valueOf(blog_id));
        ArrayList<Blog> blogs = startQuery(sql, args);

        if (blogs != null && !blogs.isEmpty())
            return blogs.getFirst();
        return null;
    }

    public static ArrayList<Blog> getBlogsByAuthorId(int author_id) {
        String sql = "SELECT * FROM blog WHERE author_id = ?";
        ArrayList<String> args = new ArrayList<>();
        args.add(String.valueOf(author_id));
        return startQuery(sql, args);
    }

    public static void deleteBlog(int blog_id) {
        String sql = "DELETE FROM blog WHERE blog_id = ?";
        ArrayList<String> args = new ArrayList<>();
        args.add(String.valueOf(blog_id));
        startDelete(sql, args);
    }

    private static ArrayList<Blog> startQuery(String sql, ArrayList<String> args) {
        ArrayList<Blog> blogs = new ArrayList<>();
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < args.size(); i++) {
                    ps.setString(i + 1, args.get(i));
                }
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

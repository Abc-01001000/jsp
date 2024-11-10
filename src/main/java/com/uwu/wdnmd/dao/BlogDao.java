package com.uwu.wdnmd.dao;

import com.uwu.wdnmd.model.Blog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.uwu.wdnmd.util.DatabaseConnectionPool.ds;

public class BlogDao {

    public static boolean addBlog(Blog blog, String content) throws SQLException {
        String sql = "INSERT INTO blog (author_id, title, description, author, url) VALUES (?,?,?,?,?)";
        String path = "C:\\Users\\abc\\Project\\wdnmd\\public";
        blog.url = path + "\\" + blog.author_id + "_" + blog.title + ".txt";

        content = content.replaceAll("<br>", "\n");
        ArrayList<String> args = new ArrayList<>();
        args.add(String.valueOf(blog.author_id));
        args.add(blog.title);
        args.add(blog.description);
        args.add(blog.author);
        args.add(blog.url);
        return startInsert(sql, args) != 0 && FileDao.writeFile(blog.url, content);
    }

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

    public static boolean updateBlog(Blog blog, String content) throws SQLException {
        String sql = "UPDATE blog SET title = ?, description = ?, url = ? WHERE blog_id = ?";
        String path = "C:\\Users\\abc\\Project\\wdnmd\\public";
        blog.url = path + "\\" + blog.author_id + "_" + blog.title + ".txt";
        ArrayList<String> args = new ArrayList<>();
        args.add(blog.title);
        args.add(blog.description);
        args.add(blog.url);
        args.add(String.valueOf(blog.blog_id));
        return startUpdate(sql, args) != 0 && FileDao.writeFile(blog.url, content);
    }

    protected static ArrayList<Blog> startQuery(String sql, ArrayList<String> args) {
        ArrayList<Blog> blogs = new ArrayList<>();
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < args.size(); i++) {
                    ps.setString(i + 1, args.get(i));
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Blog blog = new Blog();
                        blog.author_id = rs.getInt("author_id");
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

    protected static int startInsert(String sql, ArrayList<String> args) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < args.size(); i++) {
                    ps.setString(i + 1, args.get(i));
                }
                return ps.executeUpdate();
            }
        }
    }

    protected static int startUpdate(String sql, ArrayList<String> args) throws SQLException {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (int i = 0; i < args.size(); i++) {
                    ps.setString(i + 1, args.get(i));
                }
                return ps.executeUpdate();
            }
        }
    }
}

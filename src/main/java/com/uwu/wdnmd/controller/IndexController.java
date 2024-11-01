package com.uwu.wdnmd.controller;

import com.uwu.wdnmd.framework.Controller;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.util.DBUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.uwu.wdnmd.model.Blog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index(HttpServletResponse response, HttpSession session) {
        String username = (String) session.getAttribute("username");

        response.setContentType("application/json;charset=utf-8");
        try (Connection conn = DBUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select * from blog")) {
                try (ResultSet rs = ps.executeQuery()) {
                    ArrayList<Blog> blogs = new ArrayList<>();
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

                    System.out.println(blogs);
                    return new ModelAndView("forward:view/index.jsp", "blogs", blogs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

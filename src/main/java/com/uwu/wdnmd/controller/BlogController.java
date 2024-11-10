package com.uwu.wdnmd.controller;

import com.uwu.wdnmd.dao.BlogDao;
import com.uwu.wdnmd.dao.FileDao;
import com.uwu.wdnmd.dao.UserDao;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.framework.PostMapping;
import com.uwu.wdnmd.model.Blog;
import com.uwu.wdnmd.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlogController {

    @GetMapping("/blogs")
    public ModelAndView blogs(HttpServletRequest req) {
        ArrayList<Blog> blogs = BlogDao.getBlogs();
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        User user = UserDao.getUser(username, password);
        Map<String, Object> model = new HashMap<>();
        model.put("blogs", blogs);
        if (user != null && user.role.equals("admin")) {
            model.put("main", "Manage");
            model.put("manageScope", "blog");
        } else {
            model.put("main", "Blog");
        }
        return new ModelAndView("forward:view/index.jsp", model);
    }

    @GetMapping("/blog-content")
    public ModelAndView blogContent(HttpServletRequest req) {
        int blogId = Integer.parseInt(req.getParameter("blogId"));
        Blog blog = BlogDao.getBlog(blogId);
        if (blog == null) {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "Invalid blog id");
            model.put("main", "Error");
            return new ModelAndView("forward:view/index.jsp", model);
        }

        String content = FileDao.readFile(blog.url);
        if (content == null) {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "File not found");
            model.put("main", "Error");
            return new ModelAndView("forward:view/index.jsp", model);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("blog", blog);
        model.put("content", content);
        model.put("main", "Content");

        return new ModelAndView("forward:view/index.jsp", model);
    }

    @PostMapping("/blog-add")
    public ModelAndView blogAdd(HttpServletRequest req) throws SQLException {
        Blog blog = new Blog();
        HttpSession session = req.getSession();
        blog.title = req.getParameter("title");
        blog.description = req.getParameter("description");
        blog.author = (String) session.getAttribute("username");
        blog.author_id = (int) session.getAttribute("user_id");
        String content = req.getParameter("content");

        if (!BlogDao.addBlog(blog, content)) {
            Map<String, Object> model = new HashMap<>();
            model.put("main", "Add");
            return new ModelAndView("forward:view/index.jsp", model);
        }
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/blog-delete")
    public ModelAndView blogDelete(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("blogId"));
        BlogDao.deleteBlog(id);
        Map<String, Object> model = new HashMap<>();
        model.put("blogs", BlogDao.getBlogs());
        if (req.getSession().getAttribute("role") != null && req.getSession().getAttribute("role").equals("admin")) {
            model.put("main", "Manage");
            model.put("manageScope", "blog");
            return new ModelAndView("forward:view/index.jsp", model);
        }
        model.put("error", "Invalid blog id");
        model.put("main", "Error");
        return new ModelAndView("forward:view/index.jsp", model);
    }

    @PostMapping("/blog-edit")
    public ModelAndView blogEdit(HttpServletRequest req) throws SQLException {
        int blogId = Integer.parseInt(req.getParameter("blog_id"));
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String content = req.getParameter("content");

        Blog blog = BlogDao.getBlog(blogId);
        if (blog != null) {
            blog.title = title;
            blog.description = description;
        }

        if (blog != null && BlogDao.updateBlog(blog, content)) {
            Map<String, Object> model = new HashMap<>();
            model.put("blog", blog);
            model.put("content", content);
            model.put("main", "Content");
            return new ModelAndView("forward:view/index.jsp", model);
        }
        Map<String, Object> model = new HashMap<>();
        model.put("error", "Invalid blog id");
        model.put("main", "Error");
        return new ModelAndView("forward:view/index.jsp", model);
    }
}

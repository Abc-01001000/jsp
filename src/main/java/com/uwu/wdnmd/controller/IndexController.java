package com.uwu.wdnmd.controller;

import com.uwu.wdnmd.dao.BlogDao;
import com.uwu.wdnmd.dao.UserDao;
import com.uwu.wdnmd.framework.Controller;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.uwu.wdnmd.model.Blog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        ArrayList<Blog> blogs = BlogDao.getBlogs();
        Map<String, Object> model = new HashMap<>();
        model.put("blogs", blogs);
        model.put("main", "Blog");

        return new ModelAndView("forward:view/index.jsp", model);
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        int userId = Integer.parseInt(req.getParameter("userId"));

        User user = UserDao.getUser(username, password);
        if (user == null) {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "Invalid username or password");
            model.put("main", "Error");
            return new ModelAndView("forward:view/index.jsp", model);
        }

        Map<String, Object> model = new HashMap<>();
        if (user.role.equals("admin")) {
            model.put("blogs", BlogDao.getBlogs());
            model.put("main", "Manage");
        } else {
            model.put("blogs", BlogDao.getBlogsByAuthorId(userId));
            model.put("main", "Profile");
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

        File file = new File(blog.url);
        BufferedReader reader = null;
        StringBuilder fileContent = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();

            Map<String, Object> model = new HashMap<>();
            model.put("content", "something went wrong");
            model.put("main", "Content");

            return new ModelAndView("forward:view/index.jsp", model);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("content", fileContent.toString());
        model.put("main", "Content");

        return new ModelAndView("forward:view/index.jsp", model);
    }
}

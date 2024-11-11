package com.uwu.wdnmd.controller;

import com.google.gson.Gson;
import com.uwu.wdnmd.dao.BlogDao;
import com.uwu.wdnmd.dao.UserDao;
import com.uwu.wdnmd.framework.Controller;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.uwu.wdnmd.model.Blog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        ArrayList<Blog> blogs = BlogDao.getBlogs();
        Map<String, Object> model = new HashMap<>();
        model.put("blogs", blogs);
        model.put("main", "Blog");

        System.out.println(new Gson().toJson(blogs));

        return new ModelAndView("forward:view/index.jsp", model);
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        int userId = (int) session.getAttribute("user_id");

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

    @GetMapping("/new")
    public ModelAndView newBlog(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        Map<String, Object> model = new HashMap<>();
        if (UserDao.getUser(username, password) != null) {
            model.put("main", "Add");
            return new ModelAndView("forward:view/index.jsp", model);
        }
        model.put("error", "Invalid username or password");
        return new ModelAndView("forward:view/index.jsp", model);
    }
}
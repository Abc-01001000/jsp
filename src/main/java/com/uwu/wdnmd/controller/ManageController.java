package com.uwu.wdnmd.controller;

import com.uwu.wdnmd.dao.BlogDao;
import com.uwu.wdnmd.dao.UserDao;
import com.uwu.wdnmd.framework.Controller;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.model.Blog;
import com.uwu.wdnmd.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ManageController {

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

    @GetMapping("/blog-delete")
    public ModelAndView blogDelete(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("blogId"));
        BlogDao.deleteBlog(id);
        Map<String, Object> model = new HashMap<>();
        model.put("blogs", BlogDao.getBlogs());
        model.put("main", "Manage");
        model.put("manageScope", "blog");
        return new ModelAndView("forward:view/index.jsp", model);
    }

    @GetMapping("/users")
    public ModelAndView users(HttpServletRequest req) {
        ArrayList<User> users = UserDao.getUsers();
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        User user = UserDao.getUser(username, password);

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);
        if (user != null && user.role.equals("admin")) {
            model.put("main", "Manage");
            model.put("manageScope", "user");
        }
        return new ModelAndView("forward:view/index.jsp", model);
    }

    @GetMapping("/user-delete")
    public ModelAndView userDelete(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("userId"));
        UserDao.deleteUser(id);
        Map<String, Object> model = new HashMap<>();
        model.put("users", UserDao.getUsers());
        model.put("main", "Manage");
        model.put("manageScope", "user");
        return new ModelAndView("forward:view/index.jsp", model);
    }
}

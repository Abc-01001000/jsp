package com.uwu.wdnmd.controller;

import com.uwu.wdnmd.dao.BlogDao;
import com.uwu.wdnmd.dao.UserDao;
import com.uwu.wdnmd.framework.Controller;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.framework.PostMapping;
import com.uwu.wdnmd.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.uwu.wdnmd.model.Blog;

import java.sql.Connection;
import java.util.ArrayList;

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        ArrayList<Blog> blogs = BlogDao.getBlogs();
        return new ModelAndView("forward:view/index.jsp", "blogs", blogs);
    }

    @PostMapping("/profile")
    public ModelAndView profile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        User user = UserDao.getUser(username, password);
        if (user == null) {
            return new ModelAndView("redirect:view/login.jsp");
        }

        return new ModelAndView("redirect:view/profile.jsp");
    }
}

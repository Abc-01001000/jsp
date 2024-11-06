package com.uwu.wdnmd.controller;

import com.uwu.wdnmd.dao.BlogDao;
import com.uwu.wdnmd.dao.UserDao;
import com.uwu.wdnmd.framework.Controller;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.framework.PostMapping;
import com.uwu.wdnmd.model.User;
import com.uwu.wdnmd.util.DBUtil;
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

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index(HttpServletRequest req, HttpServletResponse res) {
        ArrayList<Blog> blogs = BlogDao.getBlogs();

        Map<String, Object> model = new HashMap<>();
        model.put("blogs", blogs);
        model.put("main", "Blog");

        return new ModelAndView("forward:view/index.jsp", model);
    }

    @GetMapping("/profile")
    public ModelAndView profile(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        User user = UserDao.getUser(username, password);
        if (user == null) {
            return new ModelAndView("forward:view/login.jsp");
        }

        Map<String, Object> model = new HashMap<>();
        model.put("main", "Profile");
        return new ModelAndView("forward:view/index.jsp", model);
    }

    @GetMapping("/blog-content")
    public ModelAndView blogContent(HttpServletRequest req) {
        File file = new File(req.getParameter("url"));
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

        System.out.println(fileContent);

        return new ModelAndView("forward:view/index.jsp", model);
    }
}

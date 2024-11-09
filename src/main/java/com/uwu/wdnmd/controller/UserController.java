package com.uwu.wdnmd.controller;

import com.google.gson.Gson;
import com.uwu.wdnmd.dao.UserDao;
import com.uwu.wdnmd.framework.Controller;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.framework.PostMapping;
import com.uwu.wdnmd.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @PostMapping("/login")
    public ModelAndView login(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserDao.getUser(username, password);
        if (user == null) {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "Invalid username or password");
            model.put("main", "Error");
            return new ModelAndView("forward:view/index.jsp", model);
        }

        Map<String, Object> model = new HashMap<>();
        model.put("user_id", user.user_id);
        model.put("username", user.username);
        model.put("password", user.password);
        model.put("main", "Blog");

        return new ModelAndView("redirect:/", model);
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();

        return new ModelAndView("redirect:/");
    }

    @PostMapping("/check-username")
    public ModelAndView checkUsername(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            sb.append(line);
        }

        boolean exists = false;
        String username = new Gson().fromJson(sb.toString(), InputData.class).username;

        if (UserDao.exists(username)) {
            exists = true;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("exists", exists);

        return new ModelAndView("json", model);
    }

    @PostMapping("/register")
    public ModelAndView register(HttpServletRequest req) throws SQLException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = new User();
        user.username = username;
        user.password = password;
        user.email = email;

        if (UserDao.addUser(user)) {
            Map<String, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("password", password);

            return new ModelAndView("forward:/login", model);
        } else {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "username already exists");
            model.put("main", "Error");
            return new ModelAndView("forward:view/index.jsp", model);
        }
    }

    private static class InputData {
        public String username;
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

    @PostMapping("/user-edit")
    public ModelAndView userEdit(HttpServletRequest req) throws SQLException {
        HttpSession session = req.getSession();
        String id = session.getAttribute("user_id").toString();

        User user = new User();
        user.username = req.getParameter("username");
        user.password = req.getParameter("password");
        user.email = req.getParameter("email");
        user.user_id = Integer.parseInt(id);

        if (UserDao.updateUser(user)) {
            Map<String, Object> model = new HashMap<>();
            model.put("username", user.username);
            model.put("password", user.password);
            model.put("main", "Blog");
            return new ModelAndView("redirect:/", model);
        } else {
            Map<String, Object> model = new HashMap<>();
            model.put("error", "Invalid username or password");
            model.put("main", "Error");
            return new ModelAndView("forward:view/index.jsp", model);
        }
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

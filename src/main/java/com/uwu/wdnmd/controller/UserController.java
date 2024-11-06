package com.uwu.wdnmd.controller;

import com.google.gson.Gson;
import com.uwu.wdnmd.dao.UserDao;
import com.uwu.wdnmd.framework.Controller;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.framework.PostMapping;
import com.uwu.wdnmd.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
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
            return new ModelAndView("forward:view/error.jsp", "error", "Invalid username or password");
        }

        Map<String, Object> model = new HashMap<>();
        model.put("username", user.username);
        model.put("password", user.password);

        return new ModelAndView("redirect:/", model);
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();

        return new ModelAndView("redirect:/");
    }

    @PostMapping("/check-username")
    public ModelAndView checkUsername(HttpServletRequest req, HttpServletResponse res) throws IOException {
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
            model.put("username", user.username);
            model.put("password", user.password);

            return new ModelAndView("redirect:/", model);
        } else {
            return new ModelAndView("forward:view/error.jsp", "error", "User creation failed");
        }
    }

    private static class InputData {
        public String username;
    }
}

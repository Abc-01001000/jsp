package com.uwu.wdnmd.controller;

import com.google.gson.Gson;
import com.uwu.wdnmd.dao.UserDao;
import com.uwu.wdnmd.framework.GetMapping;
import com.uwu.wdnmd.framework.ModelAndView;
import com.uwu.wdnmd.framework.PostMapping;
import com.uwu.wdnmd.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class UserController {

    @PostMapping("/login")
    public ModelAndView login(HttpServletRequest req) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserDao.getUser(username, password);
        if (user == null) {
            return new ModelAndView("forward:view/error.jsp", "error", "Invalid username or password");
        }
        HttpSession session = req.getSession();
        session.setAttribute("username", user.username);
        session.setAttribute("password", user.password);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();

        return new ModelAndView("redirect:/");
    }

    @PostMapping("/check-username")
    public void checkUsername(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

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

        PrintWriter out = res.getWriter();
        out.write(new Gson().toJson(new OutputData(exists)));
        out.flush();
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
            HttpSession session = req.getSession();
            session.setAttribute("username", user.username);
            session.setAttribute("password", user.password);
            return new ModelAndView("redirect:/");
        } else {
            return new ModelAndView("forward:view/error.jsp", "error", "User creation failed");
        }
    }

    private static class InputData {
        public String username;
    }

    private static class OutputData {
        public boolean exists;

        OutputData(boolean exists) {
            this.exists = exists;
        }
    }
}

package com.fashionstore.controller;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;

import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {

        userDAO = new UserDAOImpl();
    }

    // SHOW LOGIN PAGE

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                        "/WEB-INF/views/login.jsp")
                .forward(request, response);
    }

    // HANDLE LOGIN

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // GET FORM DATA

            String email =
                    request.getParameter("email");

            String password =
                    request.getParameter("password");

            // VALIDATION

            if (email == null || email.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {

                request.setAttribute(
                        "errorMessage",
                        "Please enter email and password.");

                request.getRequestDispatcher(
                                "/WEB-INF/views/login.jsp")
                        .forward(request, response);

                return;
            }

            // LOGIN USER

            User user =
                    userDAO.loginUser(email, password);

            // INVALID LOGIN

            if (user == null) {

                request.setAttribute(
                        "errorMessage",
                        "Invalid email or password.");

                request.getRequestDispatcher(
                                "/WEB-INF/views/login.jsp")
                        .forward(request, response);

                return;
            }

            // CREATE SESSION

            HttpSession session =
                    request.getSession();

            session.setAttribute("loggedInUser", user);

            // REDIRECT

            response.sendRedirect(
                    request.getContextPath() + "/home");

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "errorMessage",
                    "Something went wrong.");

            request.getRequestDispatcher(
                            "/WEB-INF/views/login.jsp")
                    .forward(request, response);
        }
    }
}
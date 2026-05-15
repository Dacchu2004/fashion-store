package com.fashionstore.controller;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;

import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {

        userDAO = new UserDAOImpl();
    }

    // SHOW REGISTER PAGE

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                        "/WEB-INF/views/register.jsp")
                .forward(request, response);
    }

    // HANDLE REGISTER FORM

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // GET FORM DATA

            String name =
                    request.getParameter("name");

            String email =
                    request.getParameter("email");

            String phone =
                    request.getParameter("phone");

            String password =
                    request.getParameter("password");

            String address =
                    request.getParameter("address");

            String city =
                    request.getParameter("city");

            String state =
                    request.getParameter("state");

            String pincode =
                    request.getParameter("pincode");

            // BASIC VALIDATION

            if (name == null || name.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || phone == null || phone.trim().isEmpty()
                    || password == null || password.trim().isEmpty()) {

                request.setAttribute(
                        "errorMessage",
                        "Please fill all required fields.");

                request.getRequestDispatcher(
                                "/WEB-INF/views/register.jsp")
                        .forward(request, response);

                return;
            }

            // EMAIL EXISTS

            if (userDAO.emailExists(email)) {

                request.setAttribute(
                        "errorMessage",
                        "Email already exists.");

                request.getRequestDispatcher(
                                "/WEB-INF/views/register.jsp")
                        .forward(request, response);

                return;
            }

            // PHONE EXISTS

            if (userDAO.phoneExists(phone)) {

                request.setAttribute(
                        "errorMessage",
                        "Phone number already exists.");

                request.getRequestDispatcher(
                                "/WEB-INF/views/register.jsp")
                        .forward(request, response);

                return;
            }

            // CREATE USER OBJECT

            User user = new User();

            user.setName(name);

            user.setEmail(email);

            user.setPhone(phone);

            user.setPassword(password);

            user.setAddress(address);

            user.setCity(city);

            user.setState(state);

            user.setPincode(pincode);

            // REGISTER USER

            boolean registered =
                    userDAO.registerUser(user);

            if (registered) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/login?success=registered");

            } else {

                request.setAttribute(
                        "errorMessage",
                        "Registration failed.");

                request.getRequestDispatcher(
                                "/WEB-INF/views/register.jsp")
                        .forward(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "errorMessage",
                    "Something went wrong.");

            request.getRequestDispatcher(
                            "/WEB-INF/views/register.jsp")
                    .forward(request, response);
        }
    }
}
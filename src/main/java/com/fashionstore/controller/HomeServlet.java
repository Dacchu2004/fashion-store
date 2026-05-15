package com.fashionstore.controller;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {

        productDAO = new ProductDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // CHECK IF USER IS LOGGED IN
        HttpSession session = request.getSession(false);
        if (session == null ||
                session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        List<Product> latestProducts =
                productDAO.getLatestProducts();

        request.setAttribute(
                "latestProducts",
                latestProducts
        );

        request.getRequestDispatcher(
                "/WEB-INF/views/home.jsp"
        ).forward(request, response);
    }
}
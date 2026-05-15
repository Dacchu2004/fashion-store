package com.fashionstore.controller;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Category;
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
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException {

        productDAO = new ProductDAOImpl();
        categoryDAO = new CategoryDAOImpl();
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

        // FETCH CATEGORIES FROM DATABASE
        List<Category> categories =
                categoryDAO.getAllCategories();

        request.setAttribute("categories", categories);

        // FETCH LATEST PRODUCTS
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
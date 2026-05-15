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

import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

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

        String categoryParam = request.getParameter("category");
        String searchParam = request.getParameter("search");
        String sortParam = request.getParameter("sort");

        List<Product> products;
        List<Category> categories;

        try {

            categories = categoryDAO.getAllCategories();

            // SEARCH
            if (searchParam != null &&
                    !searchParam.trim().isEmpty()) {

                products = productDAO.searchProducts(searchParam);

            }

            // CATEGORY FILTER
            else if (categoryParam != null &&
                    !categoryParam.trim().isEmpty()) {

                int categoryId =
                        Integer.parseInt(categoryParam);

                products =
                        productDAO.getProductsByCategory(categoryId);

            }

            // SORT LOW TO HIGH
            else if ("lowToHigh".equals(sortParam)) {

                products =
                        productDAO.sortProductsByPriceLowToHigh();

            }

            // SORT HIGH TO LOW
            else if ("highToLow".equals(sortParam)) {

                products =
                        productDAO.sortProductsByPriceHighToLow();

            }

            // DEFAULT
            else {

                products = productDAO.getAllProducts();
            }

            request.setAttribute("products", products);

            request.setAttribute("categories", categories);

            request.getRequestDispatcher(
                            "/WEB-INF/views/products.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to load products.");
        }
    }
}
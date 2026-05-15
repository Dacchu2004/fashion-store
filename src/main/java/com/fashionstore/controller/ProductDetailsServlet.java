package com.fashionstore.controller;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductVariantDAO;

import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;

import com.fashionstore.model.Product;
import com.fashionstore.model.ProductVariant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {

    private ProductDAO productDAO;

    private ProductVariantDAO productVariantDAO;

    @Override
    public void init() throws ServletException {

        productDAO = new ProductDAOImpl();

        productVariantDAO =
                new ProductVariantDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String productIdParam =
                    request.getParameter("id");

            // VALIDATION

            if (productIdParam == null ||
                    productIdParam.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/products");

                return;
            }

            int productId =
                    Integer.parseInt(productIdParam);

            // FETCH PRODUCT

            Product product =
                    productDAO.getProductById(productId);

            // PRODUCT NOT FOUND

            if (product == null) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/products");

                return;
            }

            // FETCH VARIANTS

            List<ProductVariant> variants =
                    productVariantDAO
                            .getVariantsByProductId(productId);

            // SEND DATA TO JSP

            request.setAttribute("product", product);

            request.setAttribute("variants", variants);

            // FORWARD

            request.getRequestDispatcher(
                            "/WEB-INF/views/product-details.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to load product details.");
        }
    }
}
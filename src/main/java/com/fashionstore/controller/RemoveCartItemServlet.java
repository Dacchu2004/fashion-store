package com.fashionstore.controller;

import com.fashionstore.dao.CartItemDAO;

import com.fashionstore.dao.impl.CartItemDAOImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/remove-cart-item")
public class RemoveCartItemServlet extends HttpServlet {

    private CartItemDAO cartItemDAO;

    @Override
    public void init() throws ServletException {

        cartItemDAO =
                new CartItemDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String cartItemIdParam =
                    request.getParameter("id");

            // VALIDATION

            if (cartItemIdParam == null ||
                    cartItemIdParam.trim().isEmpty()) {

                response.sendRedirect(
                        request.getContextPath() + "/cart");

                return;
            }

            int cartItemId =
                    Integer.parseInt(cartItemIdParam);

            // REMOVE ITEM

            cartItemDAO.removeCartItem(cartItemId);

            // REDIRECT BACK TO CART

            response.sendRedirect(
                    request.getContextPath() + "/cart");

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to remove cart item.");
        }
    }
}
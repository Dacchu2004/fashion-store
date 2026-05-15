package com.fashionstore.controller;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;

import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;

import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private CartDAO cartDAO;

    private CartItemDAO cartItemDAO;

    @Override
    public void init() throws ServletException {

        cartDAO =
                new CartDAOImpl();

        cartItemDAO =
                new CartItemDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            // SESSION

            HttpSession session =
                    request.getSession(false);

            // NOT LOGGED IN

            if (session == null ||
                    session.getAttribute("loggedInUser") == null) {

                response.sendRedirect(
                        request.getContextPath() + "/login");

                return;
            }

            // USER

            User user =
                    (User) session.getAttribute("loggedInUser");

            // CART

            Cart cart =
                    cartDAO.getCartByUserId(
                            user.getUserId()
                    );

            // NO CART

            if (cart == null) {

                response.sendRedirect(
                        request.getContextPath() + "/cart");

                return;
            }

            // CART ITEMS

            List<CartItem> cartItems =
                    cartItemDAO.getCartItemsByCartId(
                            cart.getCartId()
                    );

            // EMPTY CART

            if (cartItems == null ||
                    cartItems.isEmpty()) {

                response.sendRedirect(
                        request.getContextPath() + "/cart");

                return;
            }

            // TOTAL

            BigDecimal cartTotal =
                    cartItemDAO.getCartTotal(
                            cart.getCartId()
                    );

            // SEND TO JSP

            request.setAttribute(
                    "cartItems",
                    cartItems
            );

            request.setAttribute(
                    "cartTotal",
                    cartTotal
            );

            request.setAttribute(
                    "user",
                    user
            );

            // FORWARD

            request.getRequestDispatcher(
                    "/WEB-INF/views/checkout.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to load checkout page."
            );
        }
    }
}
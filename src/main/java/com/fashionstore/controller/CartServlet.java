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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;
    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAOImpl();
        cartItemDAO = new CartItemDAOImpl();
    }
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // GET SESSION
            HttpSession session =
                    request.getSession(false);
            // NOT LOGGED IN
            if (session == null ||
                    session.getAttribute("loggedInUser") == null) {
                response.sendRedirect(
                        request.getContextPath() + "/login");
                return;
            }
            // GET USER
            User user =
                    (User) session.getAttribute("loggedInUser");
            // GET CART
            Cart cart =
                    cartDAO.getCartByUserId(user.getUserId());
            // CREATE CART IF NOT EXISTS
            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUserId(user.getUserId());
                cartDAO.createCart(newCart);
                cart =
                        cartDAO.getCartByUserId(user.getUserId());
            }
            // GET CART ITEMS
            List<CartItem> cartItems =
                    cartItemDAO.getCartItemsByCartId(
                            cart.getCartId()
                    );
            // GET TOTAL
            BigDecimal total =
                    cartItemDAO.getCartTotal(
                            cart.getCartId()
                    );
            // SEND DATA TO JSP
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("cartTotal", total);
            // FORWARD
            request.getRequestDispatcher(
                            "/WEB-INF/views/cart.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to load cart.");
        }
    }
}
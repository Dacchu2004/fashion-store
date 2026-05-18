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

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
        private CartDAO cartDAO;
        private CartItemDAO cartItemDAO;

        @Override
        public void init() throws ServletException {
                cartDAO = new CartDAOImpl();
                cartItemDAO = new CartItemDAOImpl();
        }

        @Override
        protected void doPost(HttpServletRequest request,
                        HttpServletResponse response)
                        throws ServletException, IOException {
                try {
                        // GET SESSION
                        HttpSession session = request.getSession(false);
                        // NOT LOGGED IN
                        if (session == null ||
                                        session.getAttribute("loggedInUser") == null) {
                                response.sendRedirect(
                                                request.getContextPath() + "/login");
                                return;
                        }
                        // GET USER
                        User user = (User) session.getAttribute("loggedInUser");
                        // GET VARIANT ID
                        String variantIdParam = request.getParameter("variantId");
                        if (variantIdParam == null ||
                                        variantIdParam.trim().isEmpty()) {
                                response.sendRedirect(
                                                request.getContextPath() + "/products");
                                return;
                        }
                        int variantId = Integer.parseInt(variantIdParam);
                        // GET USER CART
                        Cart cart = cartDAO.getCartByUserId(
                                        user.getUserId());
                        // CREATE CART IF NOT EXISTS
                        if (cart == null) {
                                Cart newCart = new Cart();
                                newCart.setUserId(user.getUserId());
                                cartDAO.createCart(newCart);
                                cart = cartDAO.getCartByUserId(
                                                user.getUserId());
                        }
                        // CHECK EXISTING ITEM
                        CartItem existingCartItem = cartItemDAO.getCartItemByCartAndVariant(
                                        cart.getCartId(),
                                        variantId);
                        // IF ITEM ALREADY EXISTS
                        if (existingCartItem != null) {
                                existingCartItem.setQuantity(
                                                existingCartItem.getQuantity() + 1);
                                cartItemDAO.updateCartItem(
                                                existingCartItem);
                        }
                        // NEW CART ITEM
                        else {
                                CartItem cartItem = new CartItem();

                                cartItem.setCartId(
                                                cart.getCartId());
                                cartItem.setVariantId(
                                                variantId);
                                cartItem.setQuantity(1);
                                cartItemDAO.addCartItem(cartItem);
                        }
                        // REDIRECT TO CART
                        response.sendRedirect(
                                        request.getContextPath() + "/cart");
                } catch (Exception e) {
                        e.printStackTrace();
                        response.sendError(
                                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                                        "Unable to add item to cart.");
                }
        }
}
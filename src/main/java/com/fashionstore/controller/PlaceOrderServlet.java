package com.fashionstore.controller;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductVariantDAO;

import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.dao.impl.OrderItemDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;

import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    private CartDAO cartDAO;

    private CartItemDAO cartItemDAO;

    private OrderDAO orderDAO;

    private OrderItemDAO orderItemDAO;

    private ProductDAO productDAO;

    private ProductVariantDAO productVariantDAO;

    @Override
    public void init() throws ServletException {

        cartDAO =
                new CartDAOImpl();

        cartItemDAO =
                new CartItemDAOImpl();

        orderDAO =
                new OrderDAOImpl();

        orderItemDAO =
                new OrderItemDAOImpl();

        productDAO =
                new ProductDAOImpl();

        productVariantDAO =
                new ProductVariantDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request,
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

            // FORM DATA

            String address =
                    request.getParameter("address");

            String city =
                    request.getParameter("city");

            String state =
                    request.getParameter("state");

            String pincode =
                    request.getParameter("pincode");

            String paymentMethod =
                    request.getParameter("paymentMethod");

            // CREATE ORDER

            Order order =
                    new Order();

            order.setUserId(
                    user.getUserId()
            );

            order.setTotalAmount(
                    cartTotal
            );

            order.setStatus(
                    "Placed"
            );

            order.setPaymentMethod(
                    paymentMethod
            );

            order.setShippingAddress(
                    address
            );

            order.setShippingCity(
                    city
            );

            order.setShippingState(
                    state
            );

            order.setShippingPincode(
                    pincode
            );

            // INSERT ORDER

            int orderId =
                    orderDAO.placeOrder(order);

            // INSERT FAILED

            if (orderId == -1) {

                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Unable to place order."
                );

                return;
            }

            // ORDER ITEMS

            List<OrderItem> orderItems =
                    new ArrayList<>();

            for (CartItem cartItem : cartItems) {

                ProductVariant variant =
                        productVariantDAO.getVariantById(
                                cartItem.getVariantId()
                        );

                Product product =
                        productDAO.getProductById(
                                variant.getProductId()
                        );

                OrderItem orderItem =
                        new OrderItem();

                orderItem.setOrderId(
                        orderId
                );

                orderItem.setVariantId(
                        variant.getVariantId()
                );

                orderItem.setQuantity(
                        cartItem.getQuantity()
                );

                orderItem.setPrice(
                        product.getPrice()
                );

                orderItems.add(orderItem);
            }

            // INSERT ORDER ITEMS

            boolean orderItemsInserted =
                    orderItemDAO.addOrderItems(
                            orderItems
                    );

            // FAILED

            if (!orderItemsInserted) {

                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Unable to insert order items."
                );

                return;
            }

            // CLEAR CART

            cartItemDAO.clearCartItems(
                    cart.getCartId()
            );

            // REDIRECT SUCCESS

            response.sendRedirect(
                    request.getContextPath()
                            + "/order-success"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to place order."
            );
        }
    }
}
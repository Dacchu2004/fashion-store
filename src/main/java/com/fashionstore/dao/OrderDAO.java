package com.fashionstore.dao;

import com.fashionstore.model.Order;

import java.util.List;

public interface OrderDAO {

    int placeOrder(Order order);

    boolean updateOrder(Order order);

    boolean cancelOrder(int orderId);

    Order getOrderById(int orderId);

    List<Order> getOrdersByUserId(int userId);

    List<Order> getAllOrders();

    List<Order> getOrdersByStatus(String status);

    int getTotalOrdersCount();

    int getUserOrdersCount(int userId);
}
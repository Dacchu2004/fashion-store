package com.fashionstore.dao.impl;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.Order;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public int placeOrder(Order order) {

        String query =
                "INSERT INTO orders " +
                        "(user_id, total_amount, status, payment_method, " +
                        "shipping_address, shipping_city, " +
                        "shipping_state, shipping_pincode) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             query,
                             PreparedStatement.RETURN_GENERATED_KEYS
                     )) {

            preparedStatement.setInt(
                    1,
                    order.getUserId()
            );

            preparedStatement.setBigDecimal(
                    2,
                    order.getTotalAmount()
            );

            preparedStatement.setString(
                    3,
                    order.getStatus()
            );

            preparedStatement.setString(
                    4,
                    order.getPaymentMethod()
            );

            preparedStatement.setString(
                    5,
                    order.getShippingAddress()
            );

            preparedStatement.setString(
                    6,
                    order.getShippingCity()
            );

            preparedStatement.setString(
                    7,
                    order.getShippingState()
            );

            preparedStatement.setString(
                    8,
                    order.getShippingPincode()
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            if (rowsAffected > 0) {

                ResultSet generatedKeys =
                        preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {

                    return generatedKeys.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @Override
    public boolean updateOrder(Order order) {

        String query =
                "UPDATE orders SET " +
                        "total_amount = ?, status = ?, " +
                        "payment_method = ?, shipping_address = ?, " +
                        "shipping_city = ?, shipping_state = ?, " +
                        "shipping_pincode = ? " +
                        "WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(
                    1,
                    order.getTotalAmount()
            );

            preparedStatement.setString(
                    2,
                    order.getStatus()
            );

            preparedStatement.setString(
                    3,
                    order.getPaymentMethod()
            );

            preparedStatement.setString(
                    4,
                    order.getShippingAddress()
            );

            preparedStatement.setString(
                    5,
                    order.getShippingCity()
            );

            preparedStatement.setString(
                    6,
                    order.getShippingState()
            );

            preparedStatement.setString(
                    7,
                    order.getShippingPincode()
            );

            preparedStatement.setInt(
                    8,
                    order.getOrderId()
            );

            int rowsAffected =
                    preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean cancelOrder(int orderId) {

        String query =
                "UPDATE orders SET status = 'Cancelled' " +
                        "WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Order getOrderById(int orderId) {

        String query =
                "SELECT * FROM orders WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return extractOrderFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {

        List<Order> orders = new ArrayList<>();

        String query =
                "SELECT * FROM orders " +
                        "WHERE user_id = ? " +
                        "ORDER BY created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                orders.add(
                        extractOrderFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        String query =
                "SELECT * FROM orders " +
                        "ORDER BY created_at DESC";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query);
             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                orders.add(
                        extractOrderFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {

        List<Order> orders = new ArrayList<>();

        String query =
                "SELECT * FROM orders WHERE status = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, status);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                orders.add(
                        extractOrderFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public int getTotalOrdersCount() {

        String query =
                "SELECT COUNT(*) AS total_orders FROM orders";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query);
             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            if (resultSet.next()) {

                return resultSet.getInt("total_orders");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int getUserOrdersCount(int userId) {

        String query =
                "SELECT COUNT(*) AS total_orders " +
                        "FROM orders WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt("total_orders");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Helper Method
    private Order extractOrderFromResultSet(
            ResultSet resultSet) throws Exception {

        Order order = new Order();

        order.setOrderId(
                resultSet.getInt("order_id")
        );

        order.setUserId(
                resultSet.getInt("user_id")
        );

        order.setTotalAmount(
                resultSet.getBigDecimal("total_amount")
        );

        order.setStatus(
                resultSet.getString("status")
        );

        order.setPaymentMethod(
                resultSet.getString("payment_method")
        );

        order.setShippingAddress(
                resultSet.getString("shipping_address")
        );

        order.setShippingCity(
                resultSet.getString("shipping_city")
        );

        order.setShippingState(
                resultSet.getString("shipping_state")
        );

        order.setShippingPincode(
                resultSet.getString("shipping_pincode")
        );

        Timestamp createdAt =
                resultSet.getTimestamp("created_at");

        order.setCreatedAt(createdAt);

        return order;
    }
}
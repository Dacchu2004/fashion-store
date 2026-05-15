package com.fashionstore.dao.impl;

import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {

    @Override
    public boolean addOrderItem(OrderItem orderItem) {

        String query =
                "INSERT INTO order_items " +
                        "(order_id, variant_id, quantity, price) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(
                    1,
                    orderItem.getOrderId()
            );

            preparedStatement.setInt(
                    2,
                    orderItem.getVariantId()
            );

            preparedStatement.setInt(
                    3,
                    orderItem.getQuantity()
            );

            preparedStatement.setBigDecimal(
                    4,
                    orderItem.getPrice()
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
    public boolean addOrderItems(List<OrderItem> orderItems) {

        String query =
                "INSERT INTO order_items " +
                        "(order_id, variant_id, quantity, price) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            for (OrderItem orderItem : orderItems) {

                preparedStatement.setInt(
                        1,
                        orderItem.getOrderId()
                );

                preparedStatement.setInt(
                        2,
                        orderItem.getVariantId()
                );

                preparedStatement.setInt(
                        3,
                        orderItem.getQuantity()
                );

                preparedStatement.setBigDecimal(
                        4,
                        orderItem.getPrice()
                );

                preparedStatement.addBatch();
            }

            int[] rowsAffected =
                    preparedStatement.executeBatch();

            return rowsAffected.length > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId) {

        String query =
                "SELECT * FROM order_items " +
                        "WHERE order_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderItemId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return extractOrderItemFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {

        List<OrderItem> orderItems = new ArrayList<>();

        String query =
                "SELECT * FROM order_items " +
                        "WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                orderItems.add(
                        extractOrderItemFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderItems;
    }

    @Override
    public boolean deleteOrderItem(int orderItemId) {

        String query =
                "DELETE FROM order_items " +
                        "WHERE order_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderItemId);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteOrderItemsByOrderId(int orderId) {

        String query =
                "DELETE FROM order_items WHERE order_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            return rowsAffected >= 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Helper Method
    private OrderItem extractOrderItemFromResultSet(
            ResultSet resultSet) throws Exception {

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(
                resultSet.getInt("order_item_id")
        );

        orderItem.setOrderId(
                resultSet.getInt("order_id")
        );

        orderItem.setVariantId(
                resultSet.getInt("variant_id")
        );

        orderItem.setQuantity(
                resultSet.getInt("quantity")
        );

        orderItem.setPrice(
                resultSet.getBigDecimal("price")
        );

        return orderItem;
    }
}
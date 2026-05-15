package com.fashionstore.dao.impl;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAOImpl implements CartItemDAO {

    @Override
    public boolean addCartItem(CartItem cartItem) {

        String query =
                "INSERT INTO cart_items " +
                        "(cart_id, variant_id, quantity) " +
                        "VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(
                    1,
                    cartItem.getCartId()
            );

            preparedStatement.setInt(
                    2,
                    cartItem.getVariantId()
            );

            preparedStatement.setInt(
                    3,
                    cartItem.getQuantity()
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
    public boolean updateCartItem(CartItem cartItem) {

        String query =
                "UPDATE cart_items SET quantity = ? " +
                        "WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(
                    1,
                    cartItem.getQuantity()
            );

            preparedStatement.setInt(
                    2,
                    cartItem.getCartItemId()
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
    public boolean removeCartItem(int cartItemId) {

        String query =
                "DELETE FROM cart_items " +
                        "WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cartItemId);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {

        String query =
                "SELECT * FROM cart_items " +
                        "WHERE cart_item_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cartItemId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return extractCartItemFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public CartItem getCartItemByCartAndVariant(
            int cartId,
            int variantId) {

        String query =
                "SELECT * FROM cart_items " +
                        "WHERE cart_id = ? AND variant_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, variantId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return extractCartItemFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {

        List<CartItem> cartItems = new ArrayList<>();

        String query =
                "SELECT * FROM cart_items WHERE cart_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cartId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                cartItems.add(
                        extractCartItemFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    @Override
    public int getCartItemCount(int cartId) {

        String query =
                "SELECT COUNT(*) AS total_items " +
                        "FROM cart_items WHERE cart_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cartId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt("total_items");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public BigDecimal getCartTotal(int cartId) {

        String query =
                "SELECT SUM(ci.quantity * p.price) AS cart_total " +
                        "FROM cart_items ci " +
                        "JOIN product_variants pv " +
                        "ON ci.variant_id = pv.variant_id " +
                        "JOIN products p " +
                        "ON pv.product_id = p.product_id " +
                        "WHERE ci.cart_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cartId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                BigDecimal total =
                        resultSet.getBigDecimal("cart_total");

                return total != null
                        ? total
                        : BigDecimal.ZERO;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    @Override
    public boolean clearCartItems(int cartId) {

        String query =
                "DELETE FROM cart_items WHERE cart_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, cartId);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            return rowsAffected >= 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Helper Method
    private CartItem extractCartItemFromResultSet(
            ResultSet resultSet) throws Exception {

        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(
                resultSet.getInt("cart_item_id")
        );

        cartItem.setCartId(
                resultSet.getInt("cart_id")
        );

        cartItem.setVariantId(
                resultSet.getInt("variant_id")
        );

        cartItem.setQuantity(
                resultSet.getInt("quantity")
        );

        return cartItem;
    }
}
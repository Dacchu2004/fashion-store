package com.fashionstore.dao.impl;

import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantDAOImpl implements ProductVariantDAO {

    @Override
    public boolean addProductVariant(ProductVariant productVariant) {

        String query =
                "INSERT INTO product_variants " +
                        "(product_id, size, color, stock) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(
                    1,
                    productVariant.getProductId()
            );

            preparedStatement.setString(
                    2,
                    productVariant.getSize()
            );

            preparedStatement.setString(
                    3,
                    productVariant.getColor()
            );

            preparedStatement.setInt(
                    4,
                    productVariant.getStock()
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
    public boolean updateProductVariant(ProductVariant productVariant) {

        String query =
                "UPDATE product_variants SET " +
                        "product_id = ?, size = ?, color = ?, stock = ? " +
                        "WHERE variant_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(
                    1,
                    productVariant.getProductId()
            );

            preparedStatement.setString(
                    2,
                    productVariant.getSize()
            );

            preparedStatement.setString(
                    3,
                    productVariant.getColor()
            );

            preparedStatement.setInt(
                    4,
                    productVariant.getStock()
            );

            preparedStatement.setInt(
                    5,
                    productVariant.getVariantId()
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
    public boolean deleteProductVariant(int variantId) {

        String query =
                "DELETE FROM product_variants " +
                        "WHERE variant_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, variantId);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public ProductVariant getVariantById(int variantId) {

        String query =
                "SELECT * FROM product_variants " +
                        "WHERE variant_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, variantId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return extractVariantFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<ProductVariant> getVariantsByProductId(int productId) {

        String query =
                "SELECT * FROM product_variants " +
                        "WHERE product_id = ?";

        List<ProductVariant> variants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                variants.add(
                        extractVariantFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return variants;
    }

    @Override
    public List<ProductVariant> getVariantsByColor(String color) {

        String query =
                "SELECT * FROM product_variants " +
                        "WHERE color = ?";

        List<ProductVariant> variants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, color);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                variants.add(
                        extractVariantFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return variants;
    }

    @Override
    public List<ProductVariant> getVariantsBySize(String size) {

        String query =
                "SELECT * FROM product_variants " +
                        "WHERE size = ?";

        List<ProductVariant> variants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, size);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                variants.add(
                        extractVariantFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return variants;
    }

    @Override
    public boolean updateStock(int variantId, int stock) {

        String query =
                "UPDATE product_variants SET stock = ? " +
                        "WHERE variant_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, stock);
            preparedStatement.setInt(2, variantId);

            int rowsAffected =
                    preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public int getStockByVariantId(int variantId) {

        String query =
                "SELECT stock FROM product_variants " +
                        "WHERE variant_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, variantId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt("stock");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Helper Method
    private ProductVariant extractVariantFromResultSet(
            ResultSet resultSet) throws Exception {

        ProductVariant variant = new ProductVariant();

        variant.setVariantId(
                resultSet.getInt("variant_id")
        );

        variant.setProductId(
                resultSet.getInt("product_id")
        );

        variant.setSize(
                resultSet.getString("size")
        );

        variant.setColor(
                resultSet.getString("color")
        );

        variant.setStock(
                resultSet.getInt("stock")
        );

        return variant;
    }
}
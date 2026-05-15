package com.fashionstore.dao.impl;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public boolean addProduct(Product product) {

        String query = "INSERT INTO products " +
                "(category_id, name, description, brand, price, image_url) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, product.getCategoryId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getBrand());
            preparedStatement.setBigDecimal(5, product.getPrice());
            preparedStatement.setString(6, product.getImageUrl());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateProduct(Product product) {

        String query = "UPDATE products SET " +
                "category_id = ?, name = ?, description = ?, " +
                "brand = ?, price = ?, image_url = ? " +
                "WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, product.getCategoryId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getBrand());
            preparedStatement.setBigDecimal(5, product.getPrice());
            preparedStatement.setString(6, product.getImageUrl());
            preparedStatement.setInt(7, product.getProductId());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteProduct(int productId) {

        String query =
                "DELETE FROM products WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Product getProductById(int productId) {

        String query =
                "SELECT * FROM products WHERE product_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, productId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            if (resultSet.next()) {

                return extractProductFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() {

        String query = "SELECT * FROM products";

        return getProductsByQuery(query);
    }

    @Override
    public List<Product> getProductsByCategory(int categoryId) {

        String query =
                "SELECT * FROM products WHERE category_id = ?";

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, categoryId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                products.add(
                        extractProductFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> searchProducts(String keyword) {

        String query =
                "SELECT * FROM products " +
                        "WHERE name LIKE ? OR brand LIKE ?";

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            String searchKeyword = "%" + keyword + "%";

            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                products.add(
                        extractProductFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {

        String query =
                "SELECT * FROM products WHERE brand = ?";

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setString(1, brand);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                products.add(
                        extractProductFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> getProductsByPriceRange(
            BigDecimal minPrice,
            BigDecimal maxPrice) {

        String query =
                "SELECT * FROM products " +
                        "WHERE price BETWEEN ? AND ?";

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setBigDecimal(1, minPrice);
            preparedStatement.setBigDecimal(2, maxPrice);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                products.add(
                        extractProductFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> sortProductsByPriceLowToHigh() {

        String query =
                "SELECT * FROM products ORDER BY price ASC";

        return getProductsByQuery(query);
    }

    @Override
    public List<Product> sortProductsByPriceHighToLow() {

        String query =
                "SELECT * FROM products ORDER BY price DESC";

        return getProductsByQuery(query);
    }

    @Override
    public List<Product> getLatestProducts() {

        String query =
                "SELECT * FROM products " +
                        "ORDER BY created_at DESC LIMIT 12";

        return getProductsByQuery(query);
    }

    @Override
    public List<Product> getRelatedProducts(int categoryId,
                                            int productId) {

        String query =
                "SELECT * FROM products " +
                        "WHERE category_id = ? " +
                        "AND product_id != ? LIMIT 4";

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query)) {

            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, productId);

            ResultSet resultSet =
                    preparedStatement.executeQuery();

            while (resultSet.next()) {

                products.add(
                        extractProductFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    // Helper Method
    private Product extractProductFromResultSet(
            ResultSet resultSet) throws Exception {

        Product product = new Product();

        product.setProductId(
                resultSet.getInt("product_id")
        );

        product.setCategoryId(
                resultSet.getInt("category_id")
        );

        product.setName(
                resultSet.getString("name")
        );

        product.setDescription(
                resultSet.getString("description")
        );

        product.setBrand(
                resultSet.getString("brand")
        );

        product.setPrice(
                resultSet.getBigDecimal("price")
        );

        product.setImageUrl(
                resultSet.getString("image_url")
        );

        Timestamp createdAt =
                resultSet.getTimestamp("created_at");

        product.setCreatedAt(createdAt);

        return product;
    }

    // Common Query Helper
    private List<Product> getProductsByQuery(String query) {

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query);
             ResultSet resultSet =
                     preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                products.add(
                        extractProductFromResultSet(resultSet)
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
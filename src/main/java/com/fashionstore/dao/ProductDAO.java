package com.fashionstore.dao;

import com.fashionstore.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDAO {

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);

    Product getProductById(int productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(int categoryId);

    List<Product> searchProducts(String keyword);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByPriceRange(BigDecimal minPrice,
                                          BigDecimal maxPrice);

    List<Product> sortProductsByPriceLowToHigh();

    List<Product> sortProductsByPriceHighToLow();

    List<Product> getLatestProducts();

    List<Product> getRelatedProducts(int categoryId,
                                     int productId);
}
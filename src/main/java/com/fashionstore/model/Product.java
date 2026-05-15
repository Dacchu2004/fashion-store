package com.fashionstore.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Product {

    private int productId;
    private int categoryId;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String imageUrl;
    private Timestamp createdAt;

    // Default Constructor
    public Product() {
    }

    // Parameterized Constructor
    public Product(int productId, int categoryId, String name,
                   String description, String brand,
                   BigDecimal price, String imageUrl,
                   Timestamp createdAt) {

        this.productId = productId;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.brand = brand;
        this.price = price;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
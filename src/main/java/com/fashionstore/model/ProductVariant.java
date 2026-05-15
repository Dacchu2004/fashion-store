package com.fashionstore.model;

public class ProductVariant {

    private int variantId;
    private int productId;
    private String size;
    private String color;
    private int stock;

    // Default Constructor
    public ProductVariant() {
    }

    // Parameterized Constructor
    public ProductVariant(int variantId, int productId,
                          String size, String color, int stock) {

        this.variantId = variantId;
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.stock = stock;
    }

    // Getters and Setters

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
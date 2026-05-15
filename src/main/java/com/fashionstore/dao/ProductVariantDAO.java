package com.fashionstore.dao;

import com.fashionstore.model.ProductVariant;

import java.util.List;

public interface ProductVariantDAO {

    boolean addProductVariant(ProductVariant productVariant);

    boolean updateProductVariant(ProductVariant productVariant);

    boolean deleteProductVariant(int variantId);

    ProductVariant getVariantById(int variantId);

    List<ProductVariant> getVariantsByProductId(int productId);

    List<ProductVariant> getVariantsByColor(String color);

    List<ProductVariant> getVariantsBySize(String size);

    boolean updateStock(int variantId, int stock);

    int getStockByVariantId(int variantId);
}
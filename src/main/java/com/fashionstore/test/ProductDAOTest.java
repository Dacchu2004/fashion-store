package com.fashionstore.test;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

import java.util.List;

public class ProductDAOTest {

    public static void main(String[] args) {

        ProductDAO productDAO = new ProductDAOImpl();

        List<Product> products =
                productDAO.getAllProducts();

        if (products.isEmpty()) {

            System.out.println("No products found.");

        } else {

            System.out.println("Products List:");

            for (Product product : products) {

                System.out.println("--------------------");

                System.out.println(
                        "Product ID: "
                                + product.getProductId());

                System.out.println(
                        "Name: "
                                + product.getName());

                System.out.println(
                        "Brand: "
                                + product.getBrand());

                System.out.println(
                        "Price: "
                                + product.getPrice());

                System.out.println(
                        "Category ID: "
                                + product.getCategoryId());
            }
        }
    }
}
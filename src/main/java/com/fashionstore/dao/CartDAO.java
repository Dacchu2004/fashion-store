package com.fashionstore.dao;

import com.fashionstore.model.Cart;

public interface CartDAO {

    boolean createCart(Cart cart);

    Cart getCartById(int cartId);

    Cart getCartByUserId(int userId);

    boolean deleteCart(int cartId);

    boolean clearCart(int cartId);
}
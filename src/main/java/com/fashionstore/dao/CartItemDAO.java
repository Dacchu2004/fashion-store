package com.fashionstore.dao;

import com.fashionstore.model.CartItem;

import java.math.BigDecimal;
import java.util.List;

public interface CartItemDAO {

    boolean addCartItem(CartItem cartItem);

    boolean updateCartItem(CartItem cartItem);

    boolean removeCartItem(int cartItemId);

    CartItem getCartItemById(int cartItemId);

    CartItem getCartItemByCartAndVariant(int cartId,
                                         int variantId);

    List<CartItem> getCartItemsByCartId(int cartId);

    int getCartItemCount(int cartId);

    BigDecimal getCartTotal(int cartId);

    boolean clearCartItems(int cartId);
}
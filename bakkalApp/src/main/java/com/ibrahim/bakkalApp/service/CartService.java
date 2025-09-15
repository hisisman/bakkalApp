package com.ibrahim.bakkalApp.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "cart";

    public void addProductToCart(Long productId, HttpSession session) {
        List<Long> cart = (List<Long>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(productId);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public int getCartItemCount(HttpSession session) {
        List<Long> cart = (List<Long>) session.getAttribute(CART_SESSION_KEY);
        return cart != null ? cart.size() : 0;
    }
}

package com.ibrahim.bakkalApp.controller;

import com.ibrahim.bakkalApp.entity.Product;
import com.ibrahim.bakkalApp.service.CartService;
import com.ibrahim.bakkalApp.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
@SessionAttributes("cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

//    @ModelAttribute("cart")
//    public List<CartItem> cart() {
//        return new ArrayList<>();
//    }

//    @PostMapping("/add")
//    public String addToCart(@RequestParam Long productId,
//                            @ModelAttribute("cart") List<CartItem> cart,
//                            @RequestParam int quantity,
//                            @RequestParam String name,
//                            @RequestParam double price,
//                            HttpSession session) {
//
//        Optional<CartItem> existing = cart.stream()
//                .filter(item -> item.getId().equals(productId))
//                .findFirst();
//
//        if (existing.isPresent()) {
//            existing.get().setQuantity(existing.get().getQuantity() + quantity);
//        } else {
//            cart.add(new CartItem(productId, name, price, quantity));
//        }
//
//
//        // Sepete ekle
//        cartService.addProductToCart(productId, session);
//
//        return "redirect:/"; // Ana sayfaya dön
//    }


//    @ModelAttribute("cartItemCount")
//    public int getCartItemCount(HttpSession session) {
//        return cartService.getCartItemCount(session);
//    }

//    @GetMapping("/cart")
//    public String viewCart(HttpSession session, Model model) {
//        // Sepet LocalStorage'den geliyorsa backend yoksa frontend gösterecek.
//        // Burada backend entegrasyonu için session/cart kullanabiliriz
//        Map<Long, Integer> cart = (Map<Long, Integer>) session.getAttribute("cart");
//        if (cart == null) {
//            cart = Map.of(); // boş sepet
//        }
//
//        List<Product> products = productService.findAllByIds(cart.keySet());
//        model.addAttribute("cartProducts", products);
//        model.addAttribute("cartQuantities", cart);
//        return "cart"; // cart.html
//    }
}

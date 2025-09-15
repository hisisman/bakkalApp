package com.ibrahim.bakkalApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {

    // Ödeme sayfası - HTML uzantısını kaldırın
    @GetMapping("/checkout")
    public String checkout() {
        return "checkout"; // checkout.html (Spring otomatik olarak .html ekler)
    }

    // Ödeme başarı sayfası
    @GetMapping("/checkout/success")
    public String checkoutSuccess() {
        return "success"; // success.html
    }
}
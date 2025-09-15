package com.ibrahim.bakkalApp.controller;
import com.ibrahim.bakkalApp.entity.Product;
import com.ibrahim.bakkalApp.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class HomeController {

    private final ProductRepository productRepository;

    public HomeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

	@GetMapping("/home")
	public String homePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
		if (userDetails != null) {
			model.addAttribute("username", userDetails.getUsername());
		}
		return "home"; // home.html dosyasına yönlendirme
	}

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
}
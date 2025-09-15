package com.ibrahim.bakkalApp.controller;
import com.ibrahim.bakkalApp.entity.Product;
import com.ibrahim.bakkalApp.entity.User;
import com.ibrahim.bakkalApp.repository.ProductRepository;
import com.ibrahim.bakkalApp.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    public HomeController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

	@GetMapping("/home")
	public String homePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
		if (userDetails != null) {
			model.addAttribute("username", userDetails.getUsername());
            Optional<User> byUsername = userRepository.findByUsername(userDetails.getUsername());
            model.addAttribute("name",byUsername.get().getName());
		}
		return "home"; // home.html dosyasına yönlendirme
	}

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }
}
package com.ibrahim.bakkalApp.controller;
import com.ibrahim.bakkalApp.entity.Campaign;
import com.ibrahim.bakkalApp.entity.Product;
import com.ibrahim.bakkalApp.entity.User;
import com.ibrahim.bakkalApp.repository.CampaignRepository;
import com.ibrahim.bakkalApp.repository.ProductRepository;
import com.ibrahim.bakkalApp.repository.UserRepository;
import com.ibrahim.bakkalApp.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final CampaignRepository campaignRepository;

    @Autowired
    private ProductService productService;
    private static final Logger logger = LogManager.getLogger(HomeController.class);

    public HomeController(ProductRepository productRepository, UserRepository userRepository, CampaignRepository campaignRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
    }

	@GetMapping("/home")
	public String homePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Product> products = productRepository.findAll();
        List<Campaign> campaigns = campaignRepository.findAll();
        model.addAttribute("campaigns", campaigns);
        model.addAttribute("products", products);
		if (userDetails != null) {
			model.addAttribute("username", userDetails.getUsername());
            Optional<User> byUsername = userRepository.findByUsername(userDetails.getUsername());
            model.addAttribute("name",byUsername.get().getName());
		}
		return "home"; // home.html dosyasına yönlendirme
	}

    @GetMapping("/campaigns/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        return campaignRepository.findById(id)
                .map(c -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, c.getMimeType())
                        .body(c.getImage()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public String home(Model model) {
        // Sadece stokta olan ürünleri getir
        List<Product> availableProducts = productService.findByStockQuantityGreaterThan(0);
        model.addAttribute("products", availableProducts);
        return "home";
    }

    // Eğer başka bir metod gerekiyorsa, farklı bir URL kullanın
    @GetMapping("/redirect")
    public String redirectToHome() {
        return "redirect:/";
    }
}
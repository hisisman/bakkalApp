package com.ibrahim.bakkalApp.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String homePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		if (userDetails != null) {
			model.addAttribute("username", userDetails.getUsername());
		}
		return "home"; // home.html dosyasına yönlendirme
	}
}
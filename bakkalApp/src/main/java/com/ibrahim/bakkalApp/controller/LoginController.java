package com.ibrahim.bakkalApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login"; // login.html sayfasına yönlendirme
	}

	/*
	@GetMapping("/home")
	public String home() {
		return "home"; // Başarılı giriş sonrası ana sayfaya yönlendirme
	}*/
}
package com.ibrahim.bakkalApp.controller;

import com.ibrahim.bakkalApp.entity.User;
import com.ibrahim.bakkalApp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {

	private final UserRepository userRepository;

	public RegisterController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@Transactional
	@PostMapping
	public String registerUser(@ModelAttribute("user") User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("Kayıt isteği alındı."); // Debug log




		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
			return "register";
		}

		// Kullanıcı adı kontrolü
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			model.addAttribute("usernameError", "Bu kullanıcı zaten mevcut! Şifrenizi unuttuysanız Lütfen yenileme linki alınız");
			System.out.println("Kullanıcı adı zaten mevcut!");
			return "register";
		}

		// Şifreyi hashle
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		System.out.println("Şifre hashlendi.");

		// Varsayılan rol
		if (user.getRole() == null || user.getRole().isEmpty()) {
			user.setRole("USER");
		}
		System.out.println("Varsayılan rol atandı: " + user.getRole());

		// Veritabanına kaydet
		userRepository.save(user);
		System.out.println("Kullanıcı kaydedildi: " + user.getUsername());

		redirectAttributes.addFlashAttribute("successMessage", "Kayıt başarılı. Lütfen giriş yapınız.");

		return "redirect:/login";
	}

}

package com.ibrahim.bakkalApp.service;

import com.ibrahim.bakkalApp.entity.User;
import com.ibrahim.bakkalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public void createUser(String username, String password, String role) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));  // Şifreyi şifrele
		user.setRole(role);
		userRepository.save(user);
	}
}
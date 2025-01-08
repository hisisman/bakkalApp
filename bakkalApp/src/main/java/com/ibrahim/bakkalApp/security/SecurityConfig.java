package com.ibrahim.bakkalApp.security;

import com.ibrahim.bakkalApp.repository.UserRepository;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.ibrahim.bakkalApp" })
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(authorizeRequests ->
						authorizeRequests
								.requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()  // Login sayfasına ve statik dosyalara erişim izni
								.anyRequest().authenticated()  // Diğer sayfalara kimlik doğrulama
				)
				.formLogin(formLogin ->
						formLogin
								.loginPage("/login")  // Login sayfasına özel yönlendirme
								.defaultSuccessUrl("/home", true)  // Başarılı giriş sonrası yönlendirme
								.permitAll()
				)
				.logout(logout -> logout
						.logoutUrl("/logout")  // Çıkış URL'si
						.logoutSuccessUrl("/login?logout")  // Çıkış başarılı olduğunda yönlendirme
						.permitAll());
		return http.build();
	}

	/*
	@Bean
	@Primary
	public @Lazy UserDetailsService userDetailsService() {
		// Bellekte örnek kullanıcılar

		UserDetails user = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("admin123"))
				.roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
	@Bean
	public @Lazy UserDetailsService userDetailsService(UserRepository userRepository) {
		return new CustomUserDetailsService(userRepository);  // Burada CustomUserDetailsService'i kullanıyoruz
	}
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Şifreleri hashlemek için kullanılır
	}

}

package com.ibrahim.bakkalApp.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.ibrahim.bakkalApp" })
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf
//                        .ignoringRequestMatchers("/h2-console/**")
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                        .ignoringRequestMatchers("/api/update-stock") // CSRF'yi bu endpoint için ignore et
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/login",
//                                "/register",
//                                "/checkout",
//                                "/checkout/success",
//                                "/css/**",
//                                "/js/**",
//                                "/images/**",
//                                "/h2-console/**"
//                        ).permitAll()
//                        .requestMatchers("/api/update-stock").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .headers(headers -> headers
//                        .frameOptions(frame -> frame.disable())
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login") // DÜZELTME: /home yerine /login
//                        .loginProcessingUrl("/login") // Formun submit edileceği URL
//                        .defaultSuccessUrl("/home", true) // Başarılı girişte yönlendirme
//                        .failureUrl("/login?error=true") // Hatalı girişte yönlendirme
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout")
//                        .permitAll()
//                );
//
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Geliştirme için CSRF'yi devre dışı bırak
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/h2-console/**").permitAll() // H2 Console için izin ver
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers("/api/**").permitAll() // Tüm API endpoint'lerine izin ver
                        .requestMatchers("/", "/home", "/css/**", "/js/**", "/images/**",
                                "/register", "/login", "/checkout/**").permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)) // H2 Console için gerekli
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/default", true) // Yeni endpoint
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

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

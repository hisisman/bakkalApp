package com.ibrahim.bakkalApp.security;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/register",
                                "/checkout",           // BU SATIRI EKLEYİN
                                "/checkout/success",   // BU SATIRI EKLEYİN
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/h2-console/**"   // H2 console ve tüm alt endpointler
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()) // iframe için
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // CSRF kontrolünden muaf tut
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/home")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
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

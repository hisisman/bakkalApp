package com.ibrahim.bakkalApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BakkalAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void createEncodedPass(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashedPassword = encoder.encode("123456");
		System.out.println(hashedPassword);
	}

}

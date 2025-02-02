package com.ibrahim.bakkalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.ibrahim.bakkalApp.*" })
public class BakkalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BakkalAppApplication.class, args);
	}

}

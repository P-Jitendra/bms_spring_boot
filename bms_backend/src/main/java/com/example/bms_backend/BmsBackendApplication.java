package com.example.bms_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BmsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmsBackendApplication.class, args);
	}

}

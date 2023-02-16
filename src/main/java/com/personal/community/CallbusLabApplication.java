package com.personal.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CallbusLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(CallbusLabApplication.class, args);
	}

}
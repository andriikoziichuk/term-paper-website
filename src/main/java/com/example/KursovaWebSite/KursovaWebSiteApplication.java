package com.example.KursovaWebSite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KursovaWebSiteApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(KursovaWebSiteApplication.class, args);
		PasswordEncoder encoder = run.getBean(PasswordEncoder.class);
		System.out.println(encoder.encode("qq"));
	}
}

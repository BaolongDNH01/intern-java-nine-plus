package com.example.demo;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

	@Bean
	CommandLineRunner run(UserService userService) {
		return args ->{
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_USER"));
			
			userService.saveUser(new User(null, "Diep TT", "dieptt", "123123", new ArrayList<>()));
			userService.saveUser(new User(null, "Dip TT", "diptt", "123123", new ArrayList<>()));
			userService.saveUser(new User(null, "Dep TT", "deptt", "123123", new ArrayList<>()));
			userService.saveUser(new User(null, "Jeep TT", "jeeptt", "123123", new ArrayList<>()));
			
			userService.addRoleToUSer("dieptt", "ROLE_ADMIN");
			userService.addRoleToUSer("dieptt", "ROLE_MANAGER");
			userService.addRoleToUSer("diptt", "ROLE_USER");
			userService.addRoleToUSer("deptt", "ROLE_MANAGER");
			userService.addRoleToUSer("jeeptt", "ROLE_ADMIN");
			userService.addRoleToUSer("jeeptt", "ROLE_MANAGER");
			userService.addRoleToUSer("jeeptt", "ROLE_USER");
		};
	}
}

package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

//	@Bean
//	CommandLineRunner run(UserService userService) {
//		return args -> {
//			userService.saveRole(new Role(null, "ROLE_ADMIN"));
//			userService.saveRole(new Role(null, "ROLE_MANAGER"));
//			userService.saveRole(new Role(null, "ROLE_USER"));
//
//			userService.saveUser(new User("Diep TT", "dieptt", "123123Aa@", Date.valueOf("2000-02-02"), Gender.FEMALE,
//					"1111111111", "diep@gmail.com", "+84111020201", "Da Nang", new ArrayList<>()));
//			userService.saveUser(new User("Dip TT", "diptt", "123123Aa@", Date.valueOf("2001-02-02"), Gender.MALE,
//					"1111111112", "dip@gmail.com", "+84111020202", "Ha Noi", new ArrayList<>()));
//			userService.saveUser(new User("Dep TT", "deptt", "123123Aa@", Date.valueOf("1998-02-03"), Gender.OTHER,
//					"1111111113", "dep@gmail.com", "+84111020203", "Sai Gon", new ArrayList<>()));
//			userService.saveUser(new User("Jeep TT", "jeeptt", "123123Aa@", Date.valueOf("1997-03-02"), Gender.FEMALE,
//					"1111111115", "jeep@gmail.com", "+84111020205", "Quang Nam", new ArrayList<>()));
//
//			userService.addRoleToUSer("dieptt", "ROLE_ADMIN");
//			userService.addRoleToUSer("dieptt", "ROLE_MANAGER");
//			userService.addRoleToUSer("diptt", "ROLE_USER");
//			userService.addRoleToUSer("deptt", "ROLE_MANAGER");
//			userService.addRoleToUSer("jeeptt", "ROLE_ADMIN");
//			userService.addRoleToUSer("jeeptt", "ROLE_MANAGER");
//			userService.addRoleToUSer("jeeptt", "ROLE_USER");
//		};
//	}
	

}

package com.hsn.restaurant;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hsn.restaurant.entity.User;
import com.hsn.restaurant.repository.UserRepository;
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class RestaurantFriedChickenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantFriedChickenApplication.class, args);
	}
	@Bean
	CommandLineRunner run(UserRepository repository,PasswordEncoder encoder) {
	return args->{  	
	 var admin=User.builder()
			 .email("admin@gmail.com")
			 .password(encoder.encode("admin12345678"))
			 .enable(true)
			 .fullName("Admin 1")
			 .role("ADMIN")
			  .phoneNumber("01012345678").build();
	   if(repository.findByEmail("admin@gmail.com").isEmpty()) {
		   repository.save(admin);
	   }
	};
	}


}

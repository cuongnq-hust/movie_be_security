package security.example.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import security.example.security.model.Role;
import security.example.security.model.User;
import security.example.security.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootApplication
@EnableWebSecurity
@EnableJpaRepositories
public class SecurityApplication {
	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(false).maxAge(3600);
			}
		};
	}
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	CommandLineRunner run(UserService userService){
//		return args -> {
//			userService.saveRole(new Role(null, "ROLE_USER","this is User"));
//			userService.saveRole(new Role(null, "ROLE_ADMIN","this is User"));
//			userService.saveRole(new Role(null, "ROLE_MANAGER","this is User"));
//			userService.saveUser(new User("123456", "yasuo","tom1@gmail.com","pass", new HashSet<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>()));
//			userService.saveUser(new User("123456", "zed","tom2@gmail.com","pass", new HashSet<>(), new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
//			userService.saveUser(new User("123456", "tom","tom3@gmail.com","pass", new HashSet<>(), new ArrayList<>(),new ArrayList<>(),new ArrayList<>()));
//			userService.addToUser("tom1@gmail.com", "ROLE_USER");
//			userService.addToUser("tom2@gmail.com", "ROLE_ADMIN");
//			userService.addToUser("tom3@gmail.com", "ROLE_MANAGER");
//		};
//	}
}

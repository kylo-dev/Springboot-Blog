package com.blogproject.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyblogApplication {

//	@Bean
//	Hibernate5Module hibernate5Module() {
//		return new Hibernate5Module();
//	}

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}

}

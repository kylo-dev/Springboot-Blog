package com.blogproject.myblog;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyblogApplication {

	@Bean
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}

}

package com.blogproject.myblog;

import com.blogproject.myblog.test.EncodingTest;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

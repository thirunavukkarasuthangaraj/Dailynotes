package com.thiru.daliyworklog.daliyworklog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.thiru.daliyworklog.daliyworklog.repository")
@EntityScan(basePackages = "com.thiru.daliyworklog.daliyworklog.model")
@ComponentScan(basePackages = "com.thiru.daliyworklog.daliyworklog")
public class DaliyworklogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaliyworklogApplication.class, args);
	}

}

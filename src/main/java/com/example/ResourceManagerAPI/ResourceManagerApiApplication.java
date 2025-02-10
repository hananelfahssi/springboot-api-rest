package com.example.ResourceManagerAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ResourceManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceManagerApiApplication.class, args);
	}

}

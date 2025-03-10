package com.Ankssss.LoanManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LoanManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanManagementApplication.class, args);
	}

}

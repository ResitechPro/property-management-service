package com.resitechpro.propertymanagmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UserManagmentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagmentServiceApplication.class, args);
	}

}

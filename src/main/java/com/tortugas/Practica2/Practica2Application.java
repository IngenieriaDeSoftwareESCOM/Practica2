package com.tortugas.Practica2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Practica2Application {

	public static void main(String[] args) {
		SpringApplication.run(Practica2Application.class, args);
	}

}

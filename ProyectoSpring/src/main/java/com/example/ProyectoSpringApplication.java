package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ioc.Saluda;

@SpringBootApplication
public class ProyectoSpringApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProyectoSpringApplication.class, args);
	}

	@Autowired
	Saluda saluda;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada......");
		saluda.saluda("Mundo");
	}

}

package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.ioc.Entorno;
import com.example.ioc.Saluda;

@SpringBootApplication
public class ProyectoSpringApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProyectoSpringApplication.class, args);
	}

	@Autowired
	Saluda saludaEs;
	@Autowired
	Saluda saludaEn;
	@Autowired
	Entorno entorno;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada......");
		saludaEs.saluda("Mundo");
		saludaEn.saluda("Mundo");
		saludaEs.saluda("Mundo");
		System.out.println(entorno.getCount());

	}

}

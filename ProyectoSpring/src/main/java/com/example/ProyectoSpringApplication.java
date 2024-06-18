package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	Saluda saluda;
	@Autowired
	Saluda saluda2;
	@Autowired
	Entorno entorno;
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada......");
		saluda.saluda("Mundo");
		saluda2.saluda("Mundo");
		saluda.saluda("Mundo");
		System.out.println(entorno.getCount());

	}

}

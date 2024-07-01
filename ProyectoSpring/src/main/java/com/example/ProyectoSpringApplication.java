package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.domains.contracts.proxies.CalculatorProxy;

@SpringBootApplication
public class ProyectoSpringApplication implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(ProyectoSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner lookup(CalculatorProxy client) {
        return args -> { 
            System.err.println("Add Result --> " + client.add(0.9, 3));
            System.err.println("Subtract Result --> " + client.subtract(1, 0.9));  
            System.err.println("Multiply Result --> " + client.multiply(2, 3));  
            System.err.println("Divide Result --> " + client.divide(1, 2));
        };
    }

	@Override
	public void run(String... args) throws Exception {
        System.err.println("Aplicacion arrancada");  
		
	}
}

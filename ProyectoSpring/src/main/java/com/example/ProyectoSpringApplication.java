package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.application.proxies.CalculatorProxyImpl;
import com.example.domains.contracts.proxies.CalculatorProxy;
import com.example.webservice.schema.AddRequest;
import com.example.webservice.schema.AddResponse;
import com.example.webservice.schema.DivideRequest;
import com.example.webservice.schema.DivideResponse;
import com.example.webservice.schema.MultiplyRequest;
import com.example.webservice.schema.MultiplyResponse;
import com.example.webservice.schema.SubtractRequest;
import com.example.webservice.schema.SubtractResponse;

@SpringBootApplication
public class ProyectoSpringApplication implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(ProyectoSpringApplication.class, args);
    }

 

    @Bean
    CommandLineRunner lookup(CalculatorProxy client) {
        return args -> { 
            System.err.println("Add Result --> " + client.add(2, 3));
            System.err.println("Subtract Result --> " + client.subtract(2, 3));  
            System.err.println("Multiply Result --> " + client.multiply(2, 3));  
            System.err.println("Divide Result --> " + client.divide(10, 5));
        };
    }

	@Override
	public void run(String... args) throws Exception {
        System.err.println("Aplicacion arrancada");  
		
	}
}

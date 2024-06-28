package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.webservice.schema.AddRequest;
import com.example.webservice.schema.AddResponse;

@SpringBootApplication
public class ProyectoSpringApplication implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(ProyectoSpringApplication.class, args);
	}
	
//	@Autowired
//	ActorService srv;
//	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
//		srv.getByProjection(ActorDTO.class).forEach(System.out::println);
	}
	
//	@Bean
//	CommandLineRunner lookup(CalculatorProxy client) {
//		return args -> { System.err.println("Calculo remoto --> " + client.add(2, 3)); };
//	}
	
	@Bean
	CommandLineRunner lookup(Jaxb2Marshaller marshaller) {
		return args -> {		
			WebServiceTemplate ws = new WebServiceTemplate(marshaller);
			var request = new AddRequest();
			request.setOp1(2);
			request.setOp2(3);
			var response = (AddResponse) ws.marshalSendAndReceive("http://localhost:8090/ws/calculator", 
					 request, new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
			System.err.println("Calculo remoto --> " + response.getAddResult());
		};
	}
}

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

            var addRequest = new AddRequest();
            addRequest.setOp1(2);
            addRequest.setOp2(3);
            var addResponse = (AddResponse) ws.marshalSendAndReceive("http://localhost:8090/ws/calculator", 
                    addRequest, new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
            System.err.println("Add Result --> " + addResponse.getAddResult());

            var subtractRequest = new SubtractRequest();
            subtractRequest.setOp1(5);
            subtractRequest.setOp2(3);
            var subtractResponse = (SubtractResponse) ws.marshalSendAndReceive("http://localhost:8090/ws/calculator", 
                    subtractRequest, new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
            System.err.println("Subtract Result --> " + subtractResponse.getSubtractResult());

            var multiplyRequest = new MultiplyRequest();
            multiplyRequest.setOp1(4);
            multiplyRequest.setOp2(3);
            var multiplyResponse = (MultiplyResponse) ws.marshalSendAndReceive("http://localhost:8090/ws/calculator", 
                    multiplyRequest, new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
            System.err.println("Multiply Result --> " + multiplyResponse.getMultiplyResult());

            var divideRequest = new DivideRequest();
            divideRequest.setOp1(10);
            divideRequest.setOp2(2);
            var divideResponse = (DivideResponse) ws.marshalSendAndReceive("http://localhost:8090/ws/calculator", 
                    divideRequest, new SoapActionCallback("http://example.com/webservices/schemas/calculator"));
            System.err.println("Divide Result --> " + divideResponse.getDivideResult());
        };
	}
}

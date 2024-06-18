package com.example.ioc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;

@Configuration
public class MyConfiguration {
	
	@Value("${app.count.init:0}")
	int initValue;
	@Bean
	int countInit() {
		return initValue;
	} 
	@EventListener
	void tratarEvento(SaludaImpl.SaludaEventRecord ev) {
		System.err.println("Evento -> " +ev.tipo() +" -> "+ ev.destinatario());
	}

}

package com.example.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MyConfiguration {
	
	@Bean
	@Scope("prototype")
	Entorno entorno() {
		return new EntornoImpl(0);
	}

}

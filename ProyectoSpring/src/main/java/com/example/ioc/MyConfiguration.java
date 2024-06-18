package com.example.ioc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {
	@Value("${app.count.init:0}")
	int initValue;
	@Bean
	int countInit() {
		return initValue;
	}


}

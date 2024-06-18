package com.example.ioc;

import org.springframework.stereotype.Service;

@Service
public class Entorno {

	public void write(String texto) {
		System.out.println(texto);
	}
}

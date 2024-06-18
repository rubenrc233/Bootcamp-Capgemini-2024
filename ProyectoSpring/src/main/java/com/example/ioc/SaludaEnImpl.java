package com.example.ioc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component("saludaEn")
@Profile("en")
public class SaludaEnImpl implements Saluda {

	Entorno entorno;
	
	public SaludaEnImpl(Entorno e) {
		this.entorno = e;
	}
	@Override
	public void saluda (@NonNull String nombre) {
		entorno.write("Hi "+ nombre);
	}
}

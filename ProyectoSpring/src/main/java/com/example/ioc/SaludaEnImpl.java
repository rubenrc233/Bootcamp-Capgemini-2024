package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("saludaEn")
@Qualifier("es")
public class SaludaEnImpl implements Saluda {

	Entorno entorno;
	
	public SaludaEnImpl(Entorno e) {
		this.entorno = e;
	}
	@Override
	public void saluda (String nombre) {
		entorno.write("Hi "+ nombre);
	}
}

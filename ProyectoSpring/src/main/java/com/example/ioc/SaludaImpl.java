package com.example.ioc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("saludaEs")
@Profile("es")
public class SaludaImpl implements Saluda {

	Entorno entorno;
	
	public SaludaImpl(Entorno e) {
		this.entorno = e;
	}
	@Override
	public void saluda (String nombre) {
		if(nombre == null) {
			throw new IllegalArgumentException("El nombre es obligatorio");
		}
		entorno.write("Hola "+ nombre + " ("+entorno.getCount()+")");
	}
}

package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("saludaEs")
@Qualifier("es")
public class SaludaImpl implements Saluda {

	Entorno entorno;
	
	public SaludaImpl(Entorno e) {
		this.entorno = e;
	}
	@Override
	public void saluda (String nombre) {
		entorno.write("Hola "+ nombre + " ("+entorno.getCount()+")");
	}
}

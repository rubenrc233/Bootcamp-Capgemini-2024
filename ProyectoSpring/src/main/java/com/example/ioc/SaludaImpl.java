package com.example.ioc;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
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

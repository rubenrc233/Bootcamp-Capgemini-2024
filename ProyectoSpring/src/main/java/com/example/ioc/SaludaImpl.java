package com.example.ioc;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.NonNull;

@Component("saludaEs")
@Profile("es")
public class SaludaImpl implements Saluda {
	
	public static record SaludaEventRecord(String tipo, String destinatario) {}
	private ApplicationEventPublisher publisher;
	private Entorno entorno;
	
	public SaludaImpl(Entorno e,ApplicationEventPublisher publisher) {
		this.entorno = e;
		this.publisher = publisher;
	}
	
	protected void onSaluda(@NonNull String tipo,@NonNull String destinatario) {
		publisher.publishEvent(new SaludaEventRecord(tipo, destinatario));
	}
	
	@Override
	public void saluda (String nombre) {
		if(nombre == null) {
			throw new IllegalArgumentException("El nombre es obligatorio");
		}
		entorno.write("Hola "+ nombre + " ("+entorno.getCount()+")");
		onSaluda("saluda", nombre);
	}
	
	public void saluda() {
		entorno.write("Hola");
		onSaluda("saluda", "Sin nombre");
	}
}

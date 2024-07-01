package com.example.domains.entities.models;

import java.io.Serializable;
import lombok.Data;

@Data
public class ActorOtroDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String apellidos;
	
	public ActorOtroDTO(int actorId, String firstName, String lastName) {
		super();
		this.id = actorId;
		this.nombre = firstName;
		this.apellidos = lastName;
	}
}

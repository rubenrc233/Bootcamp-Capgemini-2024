package com.example;

import java.util.Optional;

import lombok.NonNull;

public class Persona {

	private int id;
	private String nombre;
	private String apellidos;

	public Persona(int id, String nombre, String apellidos) {
		setId(id);
		setNombre(nombre);
		setApellidos(apellidos);
	}
	public Persona(int id, String nombre) { 
		setId(id);
		setNombre(nombre);
	}
	
	public int getId() {
		return id;
	}
  
	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(@NonNull String nombre) {
		if("".equals(nombre.trim())){
			throw new IllegalArgumentException("falta el nombre");
		}
		this.nombre = nombre;
	}

	public Optional<String> getApellidos() {
		return Optional.ofNullable(apellidos);
	}

	public void setApellidos(@NonNull String apellidos) {
		this.apellidos = apellidos;
	}
	
	public void clearApellidos() {
		this.apellidos = null;
	}

	
}

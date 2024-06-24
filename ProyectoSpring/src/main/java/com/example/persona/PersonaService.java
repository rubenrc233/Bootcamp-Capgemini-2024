package com.example.persona;

public class PersonaService {
	
	private PersonaRepository daoPR;
	
	public PersonaService(PersonaRepository daoPR) {
		this.daoPR = daoPR;
	}

	public void ponMayus(int id) {
		var item = daoPR.getOne(id);
		if(item.isEmpty()) {
			throw new IllegalArgumentException("No existe una persona con ese ID");
		}
		var p = item.get();
		p.setNombre(p.getNombre().toUpperCase());
		daoPR.modify(p);
	}
}

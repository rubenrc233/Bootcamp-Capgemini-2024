package com.example.application.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.exceptions.NotFoundException;

@RestController
@RequestMapping("/api/actors/v1")
public class ActorResource {
	
	private ActorService service;
	
	public ActorResource(ActorService sv) {
		this.service = sv;
	}
	
	 @GetMapping
	 public List<ActorShort> getAll(){
		 return service.getByProjection(ActorShort.class);
	 }
	 
	 @GetMapping(path = "/{id}")
	 public ActorDTO getOne(@PathVariable int id) throws NotFoundException{
		 var item = service.getOne(id);
		 if(item.isEmpty()) {
			 throw new NotFoundException();
		 }
		 return ActorDTO.from(item.get());
		 
	 }

}


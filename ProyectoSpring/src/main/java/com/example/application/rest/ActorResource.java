package com.example.application.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.entities.Actor;

@RestController
@RequestMapping("/api/actors/v1")
public class ActorResource {
	
	private ActorService service;
	
	public ActorResource(ActorService sv) {
		this.service = sv;
	}
	
	 @GetMapping
	 public List<Actor> getAll(){
		 return srv.getByProjection(ActorShort.class)
	 }

}


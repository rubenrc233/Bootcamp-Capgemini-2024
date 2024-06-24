package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;

@SpringBootApplication
public class ProyectoSpringApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ProyectoSpringApplication.class, args);
	}

	@Autowired
	ActorRepository daoActorRepository;

	@Override
	public void run(String... args) throws Exception {
		var item = daoActorRepository.findById(201);
		if(item.isPresent()) {
			var actor =  item.get();
			actor.getFilmActors().forEach(f -> System.out.println("Titulo: " + f.getFilm().getTitle()));
			System.out.println("Actor encontrado: " + item.toString());
		} else {
			System.out.println("Actor no encontrado");
		}
		
		//var actor = new Actor(0,"Jose","Gonzalez");
		//System.out.println("Actor guardado: "+ daoActorRepository.save(actor));


		

	}


}

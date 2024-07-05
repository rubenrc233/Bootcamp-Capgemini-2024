package com.example.domains.contracts.repositories;


import com.example.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Film;
import com.example.domains.entities.FilmActor;
import com.example.domains.entities.FilmCategory;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer> {

}

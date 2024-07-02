package com.example.domains.contracts.repositories;


import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.example.domains.entities.Film;

import lombok.NonNull;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer> {

	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(@NonNull Timestamp fecha);

}

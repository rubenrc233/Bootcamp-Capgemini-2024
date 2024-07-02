package com.example.domains.contracts.services;



import java.sql.Timestamp;
import java.util.List;

import com.example.domains.core.contracts.services.ProjectionDomainService;
import com.example.domains.entities.Film;

import lombok.NonNull;

public interface FilmService extends ProjectionDomainService<Film, Integer>{

	List<Film> novedades(@NonNull Timestamp fecha);


}

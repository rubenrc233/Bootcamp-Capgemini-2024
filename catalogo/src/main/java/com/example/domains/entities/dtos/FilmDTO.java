package com.example.domains.entities.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;

import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("serial")
@Data @AllArgsConstructor
public class FilmDTO implements Serializable {
	private int filmId;
	private String description;
	private Integer length;
	private String rating;
	private Short releaseYear;
	private Byte rentalDuration;
	private BigDecimal rentalRate;
	private BigDecimal replacementCost;
	private String title;
	private String language;
	private String languageVO;
	private List<String> actors;
	private List<String> categories;
	
	public static FilmDTO from(Film source) {
		return new FilmDTO(
				source.getFilmId(), 
				source.getDescription(),
				source.getLength(),
				source.getRating() == null ? null : source.getRating(),
				source.getReleaseYear(),
				source.getRentalDuration(),
				source.getRentalRate(),
				source.getReplacementCost(),
				source.getTitle(),
				source.getLanguage() == null ? null : source.getLanguage().getName(),
				source.getLanguageVO() == null ? null : source.getLanguageVO().getName(),
				source.getActors().stream().map(item -> item.getFirstName() + " " + item.getLastName())
					.sorted().toList(),
				source.getCategories().stream().map(item -> item.getName()).sorted().toList()
				);
	}
	
	

}

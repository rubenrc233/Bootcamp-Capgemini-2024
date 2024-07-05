package com.example.domains.entities.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.Film;
import com.example.domains.entities.Film.Rating;
import com.example.domains.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO implements Serializable {
    private int filmId;
    private String description;
    private Timestamp lastUpdate;
    private int length;
    private Rating rating;
    private Short releaseYear;
    private byte rentalDuration;
    private BigDecimal rentalRate;
    private BigDecimal replacementCost;
    private String title;
    private Language language;
    private Language languageVO;
    private List<ActorDTO> actores;
    private List<CategoryDTO> categories;

    public static FilmDTO from(Film source) {
        return new FilmDTO(
            source.getFilmId(),
            source.getDescription(),
            source.getLastUpdate(),
            source.getLength(),
            source.getRating(),
            source.getReleaseYear(),
            source.getRentalDuration(),
            source.getRentalRate(),
            source.getReplacementCost(),
            source.getTitle(),
            source.getLanguage(),
            source.getLanguageVO(),
            source.getActors().stream().map(o -> ActorDTO.from(o)).toList(),
            source.getCategories().stream().map(o -> CategoryDTO.from(o)).toList()
        );
    }

    public static Film from(FilmDTO source) {
        Film film = new Film(
            source.getFilmId(),
            source.getTitle(),
            source.language,
            source.getRentalDuration(),
            source.getRentalRate(),
            source.getReplacementCost()
        );
        film.setDescription(source.getDescription());
        film.setLastUpdate(source.getLastUpdate());
        film.setLength(source.getLength());
        film.setRating(source.getRating());
        film.setReleaseYear(source.getReleaseYear());
        film.setActors(source.getActores().stream().map(o -> ActorDTO.from(o)).toList());
        film.setCategories(source.getCategories().stream().map(o -> CategoryDTO.from(o)).toList());
        film.setLanguageVO(source.getLanguageVO());
        return film;
    }
}
package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * The persistent class for the film_category database table.
 * 
 */
@Entity
@Table(name="film_category")
@NamedQuery(name="FilmCategory.findAll", query="SELECT f FROM FilmCategory f")
@Schema(name = "Relacion entre pelicula y categoría")
public class FilmCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@Schema(description = "Identificador de la relación")
	private FilmCategoryPK id;

	@Column(name="last_update", insertable = false, updatable = false)
	@Schema(description = "Ultima actualización de la información, formato: yyyy-MM-dd hh:mm:ss")
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="category_id", insertable=false, updatable=false)
	@NotNull
	@Schema(description = "Categoría de la relación")
	private Category category;

	//bi-directional many-to-one association to Film
	@ManyToOne
	@JoinColumn(name="film_id", insertable=false, updatable=false)
	@NotNull
	@Schema(description = "Pelicula de la relación")
	private Film film;

	public FilmCategory() {
	}

	public FilmCategory(Film film, Category category) {
		this.film = film;
		this.category = category;
		setId(new FilmCategoryPK(film.getFilmId(), category.getCategoryId()));
	}

	public FilmCategoryPK getId() {
		return this.id;
	}

	public void setId(FilmCategoryPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

}
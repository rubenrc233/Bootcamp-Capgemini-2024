package com.example.domains.entities;

import java.io.Serializable;

import com.example.domains.core.entities.EntityBase;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

/**
 * The primary key class for the film_category database table.
 * 
 */
@Embeddable
@Schema(name = "Identificador de la relación pelicula-categoría")
public class FilmCategoryPK extends EntityBase<FilmCategoryPK> implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="film_id", insertable=false, updatable=false, unique=true, nullable=false)
	@Schema(description = "Identificador de la pelicula")
	private int filmId;

	@Column(name="category_id", insertable=false, updatable=false, unique=true, nullable=false)
	@Schema(description = "Identificador de la categoría")
	private int categoryId;

	public FilmCategoryPK() {
	}
	
	
	public FilmCategoryPK(int filmId, int categoryId) {
		this.filmId = filmId;
		this.categoryId = categoryId;
	}


	public int getFilmId() {
		return this.filmId;
	}
	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}
	public int getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(byte categoryId) {
		this.categoryId = categoryId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FilmCategoryPK)) {
			return false;
		}
		FilmCategoryPK castOther = (FilmCategoryPK)other;
		return 
			(this.filmId == castOther.filmId)
			&& (this.categoryId == castOther.categoryId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.filmId;
		hash = hash * prime + ((int) this.categoryId);
		
		return hash;
	}
}
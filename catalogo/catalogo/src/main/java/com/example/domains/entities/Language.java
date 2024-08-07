package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * The persistent class for the language database table.
 * 
 */
@Entity
@Table(name="language")
@NamedQuery(name="Language.findAll", query="SELECT l FROM Language l")
@Schema(name = "Entidad del idioma")
public class Language extends EntityBase<Language> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="language_id", unique=true, nullable=false)
	@Schema(description = "Identificador único del idioma", example = "1")
	private int languageId;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	@Schema(description = "Marca de tiempo de la última actualización")
	private Timestamp lastUpdate;

	@Column(nullable=false, length=20)
	@NotBlank
	@Size(max=20, min=2)
	@Schema(description = "Nombre del idioma", example = "Inglés", required = true)
	private String name;

	// Asociación bidireccional muchos-a-uno a Film
	@OneToMany(mappedBy="language")
	@JsonBackReference
	@ArraySchema(arraySchema = @Schema(description = "Lista de peliculas con version en este idioma"),schema = @Schema(description = "Pelicula en este idioma", implementation = Film.class))
	private List<Film> films;

	// Asociación bidireccional muchos-a-uno a Film
	@OneToMany(mappedBy="languageVO")
	@JsonBackReference
	@ArraySchema(arraySchema = @Schema(description = "Lista de peliculas con version original en este idioma"), schema = @Schema(description = "Pelicula en la versión original del idioma", implementation = Film.class))
	private List<Film> filmsVO;

	public Language() {
	}
	
	

	public Language(int languageId) {
		super();
		this.languageId = languageId;
	}



	public Language(int languageId, @NotBlank @Size(max = 20, min = 2) String name) {
		super();
		this.languageId = languageId;
		this.name = name;
	}



	public int getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Film> getFilms() {
		return this.films;
	}

	public void setFilms(List<Film> films) {
		this.films = films;
	}

	public Film addFilm(Film film) {
		getFilms().add(film);
		film.setLanguage(this);

		return film;
	}

	public Film removeFilm(Film film) {
		getFilms().remove(film);
		film.setLanguage(null);

		return film;
	}

	public List<Film> getFilmsVO() {
		return this.filmsVO;
	}

	public void setFilmsVO(List<Film> filmsVO) {
		this.filmsVO = filmsVO;
	}

	public Film addFilmsVO(Film filmsVO) {
		getFilmsVO().add(filmsVO);
		filmsVO.setLanguageVO(this);

		return filmsVO;
	}

	public Film removeFilmsVO(Film filmsVO) {
		getFilmsVO().remove(filmsVO);
		filmsVO.setLanguageVO(null);

		return filmsVO;
	}



	@Override
	public int hashCode() {
		return Objects.hash(languageId);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		return languageId == other.languageId;
	}



	@Override
	public String toString() {
		return "Language [languageId=" + languageId + ", lastUpdate=" + lastUpdate + ", name=" + name + "]";
	}
	
	

}
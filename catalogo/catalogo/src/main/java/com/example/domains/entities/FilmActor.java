package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;

import java.sql.Timestamp;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * The persistent class for the film_actor database table.
 * 
 */
@Entity
@Table(name="film_actor")
@NamedQuery(name="FilmActor.findAll", query="SELECT f FROM FilmActor f")
@Schema(name = "Relacion entre pelicula y actor")
public class FilmActor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	@Schema(description = "Identificador de la relación", implementation = FilmActorPK.class)
	private FilmActorPK id;

	@Column(name="last_update", insertable = false, updatable = false)
	@Schema(description = "Ultima actualización de la información")
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to Actor
	@ManyToOne
	@JoinColumn(name="actor_id", insertable=false, updatable=false)
	@Schema(description = "Actor de la relación", implementation = Actor.class)
	private Actor actor;

	//bi-directional many-to-one association to Film
	@ManyToOne
	@JoinColumn(name="film_id", insertable=false, updatable=false)
	@Schema(description = "Pelicula de la relación", implementation = Film.class)
	private Film film;

	public FilmActor() {
	}

	public FilmActor(Film film, Actor actor) {
		super();
		this.film = film;
		this.actor = actor;
		setId(new FilmActorPK(film.getFilmId(), actor.getActorId()));
	}

	public FilmActorPK getId() {
		return this.id;
	}

	public void setId(FilmActorPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

}
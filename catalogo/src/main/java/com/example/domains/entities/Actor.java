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
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The persistent class for the actor database table.
 */
@Entity
@Table(name = "actor")
@NamedQuery(name = "Actor.findAll", query = "SELECT a FROM Actor a")
@Schema(description = "Entidad que representa un actor")
public class Actor extends EntityBase<Actor> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id", unique = true, nullable = false)
    @Schema(description = "Identificador único del actor", example = "1")
    private int actorId;

    @Column(name = "first_name", nullable = false, length = 45)
    @NotBlank
    @Size(max = 45, min = 2)
    @Schema(description = "Nombre del actor", example = "John")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    @NotBlank
    @Size(max = 45, min = 2)
    @Schema(description = "Apellido del actor", example = "Doe")
    private String lastName;

    @Column(name = "last_update", insertable = false, updatable = false, nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Schema(description = "Fecha de la última actualización", example = "2024-07-04 12:00:00")
    private Timestamp lastUpdate;

    // bi-directional many-to-one association to FilmActor
    @OneToMany(mappedBy = "actor", fetch = FetchType.EAGER)
    @JsonBackReference
    @Schema(description = "Lista de film-actor asociadas al actor")
    private List<FilmActor> filmActors;

    public Actor() {
    }

    public Actor(int actorId) {
        this.actorId = actorId;
    }

    public Actor(int actorId, String firstName, String lastName) {
        this.actorId = actorId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getActorId() {
        return this.actorId;
    }

    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<FilmActor> getFilmActors() {
        return this.filmActors;
    }

    public void setFilmActors(List<FilmActor> filmActors) {
        this.filmActors = filmActors;
    }

    public FilmActor addFilmActor(FilmActor filmActor) {
        getFilmActors().add(filmActor);
        filmActor.setActor(this);

        return filmActor;
    }

    public FilmActor removeFilmActor(FilmActor filmActor) {
        getFilmActors().remove(filmActor);
        filmActor.setActor(null);

        return filmActor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Actor other = (Actor) obj;
        return actorId == other.actorId;
    }

    @Override
    public String toString() {
        return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
                + lastUpdate + "]";
    }

    public void jubilate() {
        // Lógica para jubilar a un actor
    }

    public void recibePremio(String premio) {
        // Lógica para recibir un premio
    }
}

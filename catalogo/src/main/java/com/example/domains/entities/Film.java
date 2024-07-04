package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The persistent class for the film database table.
 */
@Entity
@Table(name="film")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
@Schema(description = "Entidad que representa una película")
public class Film extends EntityBase<Film> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="film_id", unique=true, nullable=false)
    @Schema(description = "Identificador único de la película", example = "1")
    private int filmId;

    @Lob
    @Schema(description = "Descripción de la película", example = "Una gran aventura...")
    private String description;

    @Column(name="last_update", insertable=false, updatable=false, nullable=false)
    @Schema(description = "Fecha y hora de la última actualización", example = "2023-01-01T00:00:00Z")
    private Timestamp lastUpdate;

    @PositiveOrZero
    @Digits(fraction = 0, integer = 5)
    @Schema(description = "Duración de la película en minutos", example = "120")
    private int length;

    @Column(length=1)
    @Schema(description = "Clasificación de la película", example = "PG")
    private String rating;

    @Column(name="release_year")
    @Schema(description = "Año de lanzamiento de la película", example = "2022")
    private Short releaseYear;

    @Column(name="rental_duration", nullable=false)
    @NotNull
    @PositiveOrZero
    @Digits(fraction = 0, integer = 3)
    @Schema(description = "Duración del alquiler en días", example = "3")
    private byte rentalDuration;

    @Column(name="rental_rate", nullable=false, precision=10, scale=2)
    @NotNull
    @Digits(fraction = 2, integer = 4)
    @Schema(description = "Tarifa de alquiler de la película", example = "4.99")
    private BigDecimal rentalRate;

    @Column(name="replacement_cost", nullable=false, precision=10, scale=2)
    @NotNull
    @Digits(fraction = 2, integer = 5)
    @Schema(description = "Coste de reposición de la película", example = "19.99")
    private BigDecimal replacementCost;

    @Column(nullable=false, length=128)
    @NotBlank
    @Size(max=128, min=2)
    @Schema(description = "Título de la película", example = "El Gran Escape")
    private String title;

    //bi-directional many-to-one association to Language
    @ManyToOne
    @JoinColumn(name="language_id", nullable=false)
    @NotNull
    @Schema(description = "Idioma de la película")
    private Language language;

    //bi-directional many-to-one association to Language
    @ManyToOne
    @JoinColumn(name="original_language_id")
    @Schema(description = "Idioma original de la película")
    private Language languageVO;

    //bi-directional many-to-one association to FilmActor
    @OneToMany(mappedBy="film", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de actores que participan en la película")
    private List<FilmActor> filmActors;

    //bi-directional many-to-one association to FilmCategory
    @OneToMany(mappedBy="film", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Lista de categorías a las que pertenece la película")
    private List<FilmCategory> filmCategories;

    public Film() {
    }

    public Film(int filmId) {
        this.filmId = filmId;
    }

    public Film(int filmId, int length, @Size(max = 1, min = 1) String rating, @NotBlank byte rentalDuration,
                @NotBlank BigDecimal rentalRate, @NotBlank BigDecimal replacementCost) {
        this.filmId = filmId;
        this.length = length;
        this.rating = rating;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
    }

    public Film(int filmId, String description, Integer length, @Size(max = 1, min = 1) String rating, @NotBlank byte rentalDuration,
                @NotBlank BigDecimal rentalRate, @NotBlank BigDecimal replacementCost, String title, Language language,
                Language languageVO, List<Actor> actors, List<Category> categories) {
        this.filmId = filmId;
        this.length = length;
        this.rating = rating;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.replacementCost = replacementCost;
    }

    public Film(int filmId, @NotBlank @Size(max = 128) String title, String description, @Min(1895) Short releaseYear,
                @NotNull Language language, Language languageVO, @Positive Byte rentalDuration,
                @Positive @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 2, fraction = 2) BigDecimal rentalRate,
                @Positive Integer length,
                @DecimalMin(value = "0.0", inclusive = false) @Digits(integer = 3, fraction = 2) BigDecimal replacementCost,
                String rating) {
        super();
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language;
        this.languageVO = languageVO;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
        this.length = length;
        this.replacementCost = replacementCost;
        this.rating = rating;
    }

    public int getFilmId() {
        return this.filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Short getReleaseYear() {
        return this.releaseYear;
    }

    public void setReleaseYear(Short releaseYear) {
        this.releaseYear = releaseYear;
    }

    public byte getRentalDuration() {
        return this.rentalDuration;
    }

    public void setRentalDuration(byte rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public BigDecimal getRentalRate() {
        return this.rentalRate;
    }

    public void setRentalRate(BigDecimal rentalRate) {
        this.rentalRate = rentalRate;
    }

    public BigDecimal getReplacementCost() {
        return this.replacementCost;
    }

    public void setReplacementCost(BigDecimal replacementCost) {
        this.replacementCost = replacementCost;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Language getLanguage() {
        return this.language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Language getLanguageVO() {
        return this.languageVO;
    }

    public void setLanguageVO(Language languageVO) {
        this.languageVO = languageVO;
    }

    //Actores

    public List<Actor> getActors() {
        return this.filmActors.stream().map(item -> item.getActor()).toList();
    }

    public void setActors(List<Actor> source) {
        if (filmActors == null || !filmActors.isEmpty()) clearActors();
        source.forEach(item -> addActor(item));
    }

    public void clearActors() {
        filmActors = new ArrayList<FilmActor>();
    }

    public void addActor(Actor actor) {
        FilmActor filmActor = new FilmActor(this, actor);
        filmActors.add(filmActor);
    }

    public void addActor(int actorId) {
        addActor(new Actor(actorId));
    }

    public void removeActor(Actor actor) {
        var filmActor = filmActors.stream().filter(item -> item.getActor().equals(actor)).findFirst();
        if (filmActor.isEmpty())
            return;
        filmActors.remove(filmActor.get());
    }

    //Categorias 

    public List<Category> getCategories() {
        return this.filmCategories.stream().map(item -> item.getCategory()).toList();
    }

    public void setCategories(List<Category> source) {
        if (filmCategories == null || !filmCategories.isEmpty()) clearCategories();
        source.forEach(item -> addCategory(item));
    }

    public void clearCategories() {
        filmCategories = new ArrayList<FilmCategory>();
    }

    public void addCategory(Category item) {
        FilmCategory filmCategory = new FilmCategory(this, item);
        filmCategories.add(filmCategory);
    }

    public void addCategory(int id) {
        addCategory(new Category(id));
    }

    public void removeCategory(Category ele) {
        var filmCategory = filmCategories.stream().filter(item -> item.getCategory().equals(ele)).findFirst();
        if (filmCategory.isEmpty())
            return;
        filmCategories.remove(filmCategory.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, filmId, lastUpdate, length, rating, releaseYear, rentalDuration, rentalRate,
                replacementCost, title);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Film other = (Film) obj;
        return Objects.equals(description, other.description) && filmId == other.filmId
                && Objects.equals(lastUpdate, other.lastUpdate) && length == other.length
                && Objects.equals(rating, other.rating) && Objects.equals(releaseYear, other.releaseYear)
                && rentalDuration == other.rentalDuration && Objects.equals(rentalRate, other.rentalRate)
                && Objects.equals(replacementCost, other.replacementCost) && Objects.equals(title, other.title);
    }

    @Override
    public String toString() {
        return "Film [filmId=" + filmId + ", description=" + description + ", lastUpdate=" + lastUpdate + ", length="
                + length + ", rating=" + rating + ", releaseYear=" + releaseYear + ", rentalDuration=" + rentalDuration
                + ", rentalRate=" + rentalRate + ", replacementCost=" + replacementCost + ", title=" + title + "]";
    }

    public Film combine(Film film) {
        film.title = title;
        film.description = description;
        film.releaseYear = releaseYear;
        film.language = language;
        film.languageVO = languageVO;
        film.rentalDuration = rentalDuration;
        film.rentalRate = rentalRate;
        film.length = length;
        film.replacementCost = replacementCost;
        film.rating = rating;

        film.getActors().stream()
            .filter(item -> !getActors().contains(item))
            .forEach(item -> film.removeActor(item));

        getActors().stream()
            .filter(item -> !film.getActors().contains(item))
            .forEach(item -> film.addActor(item));

        film.getCategories().stream()
            .filter(item -> !getCategories().contains(item))
            .forEach(item -> film.removeCategory(item));

        getCategories().stream()
            .filter(item -> !film.getCategories().contains(item))
            .forEach(item -> film.addCategory(item));
        return film;
    }

    public static enum Rating {
        GENERAL_AUDIENCES("G"), PARENTAL_GUIDANCE_SUGGESTED("PG"), PARENTS_STRONGLY_CAUTIONED("PG-13"), RESTRICTED("R"),
        ADULTS_ONLY("NC-17");

        @Schema(description = "Valor de la clasificación de la película")
        String value;

        Rating(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Rating getEnum(String value) {
            switch (value) {
                case "G":
                    return Rating.GENERAL_AUDIENCES;
                case "PG":
                    return Rating.PARENTAL_GUIDANCE_SUGGESTED;
                case "PG-13":
                    return Rating.PARENTS_STRONGLY_CAUTIONED;
                case "R":
                    return Rating.RESTRICTED;
                case "NC-17":
                    return Rating.ADULTS_ONLY;
                case "":
                    return null;
                default:
                    throw new IllegalArgumentException("Unexpected value: " + value);
            }
        }

        public static final String[] VALUES = { "G", "PG", "PG-13", "R", "NC-17" };
    }
}

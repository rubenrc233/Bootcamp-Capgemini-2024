package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the film database table.
 * 
 */
@Entity
@Table(name="film")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
public class Film extends EntityBase<Film> implements Serializable {
	private static final long serialVersionUID = 1L;

	
	public static enum Rating {
	    GENERAL_AUDIENCES("G"),
	    PARENTAL_GUIDANCE_SUGGESTED("PG"),
	    PARENTS_STRONGLY_CAUTIONED("PG-13"),
	    RESTRICTED("R"),
	    ADULTS_ONLY("NC-17");

	    String value;
	    
	    Rating(String value) {
	        this.value = value;
	    }

	    public String getValue() {
	        return value;
	    }
		public static Rating getEnum(String value) {
			switch (value) {
			case "G": return Rating.GENERAL_AUDIENCES;
			case "PG": return Rating.PARENTAL_GUIDANCE_SUGGESTED;
			case "PG-13": return Rating.PARENTS_STRONGLY_CAUTIONED;
			case "R": return Rating.RESTRICTED;
			case "NC-17": return Rating.ADULTS_ONLY;
			default:
				throw new IllegalArgumentException("Unexpected value: " + value);
			}
		}
		public static final String[] VALUES = {"G", "PG", "PG-13", "R", "NC-17"};
	}
	@Converter
	private static class RatingConverter implements AttributeConverter<Rating, String> {
	    @Override
	    public String convertToDatabaseColumn(Rating rating) {
	        if (rating == null) {
	            return null;
	        }
	        return rating.getValue();
	    }
	    @Override
	    public Rating convertToEntityAttribute(String value) {
	        if (value == null) {
	            return null;
	        }

	        return Rating.getEnum(value);
	    }
	}

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="film_id", unique=true, nullable=false)
	private int filmId;

	@Lob
	private String description;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	private Timestamp lastUpdate;

	@PositiveOrZero
	@Digits(fraction = 0, integer = 5)
	private int length;

	@Convert(converter = RatingConverter.class)
	private Rating rating;

	@Column(name="release_year")
	private Short releaseYear;

	@Column(name="rental_duration", nullable=false)
	@NotNull
	@PositiveOrZero
	@Digits(fraction = 0, integer = 3)
	private byte rentalDuration;

	@Column(name="rental_rate", nullable=false, precision=10, scale=2)
	@NotNull
	@Digits(fraction = 2, integer = 4)
	private BigDecimal rentalRate;

	@Column(name="replacement_cost", nullable=false, precision=10, scale=2)
	@NotNull
	@Digits(fraction = 2, integer = 5)
	private BigDecimal replacementCost;

	@Column(nullable=false, length=128)
	@NotBlank
	@Size(max=128, min=2)
	private String title;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="language_id", nullable=false)
	@NotNull
	@JsonManagedReference
	private Language language;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="original_language_id")
	@JsonManagedReference
	private Language languageVO;

	//bi-directional many-to-one association to FilmActor
	@OneToMany(mappedBy="film", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<FilmActor> filmActors = new ArrayList<FilmActor>();

	//bi-directional many-to-one association to FilmCategory
	
	@OneToMany(mappedBy="film", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<FilmCategory> filmCategories = new ArrayList<FilmCategory>();

	public Film() {
	}

	
	
	public Film(int filmId) {
		this.filmId = filmId;
	}


	
	public Film(int filmId, int length, Rating rating, @NotBlank byte rentalDuration,
			@NotBlank BigDecimal rentalRate, @NotBlank BigDecimal replacementCost) {
		this.filmId = filmId;
		this.length = length;
		this.rating = rating;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
	}



	public Film(@NotBlank @Size(max = 128) String title, @NotNull Language language, byte rentalDuration,
			BigDecimal rentalRate,BigDecimal replacementCost) {
		super();
		this.title = title;
		this.language = language;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
	}
	
	public Film(int filmId, @NotBlank @Size(max = 128) String title, Language language, byte rentalDuration, BigDecimal rentalRate,
			 BigDecimal replacementCost) {
		super();
		this.filmId = filmId;
		this.title = title;
		this.language = language;
		this.rentalDuration = rentalDuration;
		this.rentalRate = rentalRate;
		this.replacementCost = replacementCost;
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

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
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

	// Gestión de actores

		public List<Actor> getActors() {
			return this.filmActors.stream().map(item -> item.getActor()).toList();
		}
		public void setActors(List<Actor> source) {
			if(filmActors == null || !filmActors.isEmpty()) clearActors();
			source.forEach(item -> addActor(item));
		}
		public void clearActors() {
			filmActors = new ArrayList<FilmActor>() ;
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
			if(filmActor.isEmpty())
				return;
			filmActors.remove(filmActor.get());
		}

		// Gestión de categorias

		public List<Category> getCategories() {
			return this.filmCategories.stream().map(item -> item.getCategory()).toList();
		}
		public void setCategories(List<Category> source) {
			if(filmCategories == null || !filmCategories.isEmpty()) clearCategories();
			source.forEach(item -> addCategory(item));
		}
		public void clearCategories() {
			filmCategories = new ArrayList<FilmCategory>() ;
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
			if(filmCategory.isEmpty())
				return;
			filmCategories.remove(filmCategory.get());
		}

	

	@Override
	public int hashCode() {
		return Objects.hash(filmId);
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
		return filmId == other.filmId;
	}



	@Override
	public String toString() {
		return "Film [filmId=" + filmId + ", description=" + description + ", lastUpdate=" + lastUpdate + ", length="
				+ length + ", rating=" + rating + ", releaseYear=" + releaseYear + ", rentalDuration=" + rentalDuration
				+ ", rentalRate=" + rentalRate + ", replacementCost=" + replacementCost + ", title=" + title + "]";
	}
	
	

}
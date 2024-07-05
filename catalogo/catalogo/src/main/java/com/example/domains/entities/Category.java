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
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
@Schema(name = "Entidad de la categoria", description = "Información completa de la categoria")
public class Category extends EntityBase<Category> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id", unique=true, nullable=false)
	@Schema(description = "Identificador de la categoria")
	private int categoryId;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	@Schema(description = "Ultima actualización de la información" )
	private Timestamp lastUpdate;

	@Column(nullable=false, length=25)
	@NotBlank
	@Size(max=25, min=2)
	@Schema(description = "Nombre de la caregoría")
	private String name;

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="category")
	@JsonBackReference
	@ArraySchema(arraySchema = @Schema(description = "Lista de peliculas en las que el actor participo"))
	private List<FilmCategory> filmCategories;

	public Category() {
	}
	
	

	public Category(int categoryId) {
		super();
		this.categoryId = categoryId;
	}



	public Category(int categoryId, @NotBlank @Size(max = 25, min = 2) String name) {
		super();
		this.categoryId = categoryId;
		this.name = name;
	}



	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	public List<FilmCategory> getFilmCategories() {
		return this.filmCategories;
	}

	public void setFilmCategories(List<FilmCategory> filmCategories) {
		this.filmCategories = filmCategories;
	}

	public FilmCategory addFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().add(filmCategory);
		filmCategory.setCategory(this);

		return filmCategory;
	}

	public FilmCategory removeFilmCategory(FilmCategory filmCategory) {
		getFilmCategories().remove(filmCategory);
		filmCategory.setCategory(null);

		return filmCategory;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(categoryId);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return categoryId == other.categoryId;
	}



	@Override
	public String toString() {
		return "Category [categoryId=" + categoryId + ", lastUpdate=" + lastUpdate + ", name=" + name + "]";
	}
	
	
	
	

}
package com.example.domains.entities.models;

import java.io.Serializable;

import com.example.domains.entities.Category;


import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("serial")
@Data @AllArgsConstructor
public class CategoryDTO implements Serializable {
	private int categoryId;
	private String name;
	
	public static CategoryDTO from(Category source)  {
		return new CategoryDTO(
				source.getCategoryId()
				,source.getName());
	}
	
	public static Category from(CategoryDTO source)  {
		return new Category(
				source.getCategoryId()
				,source.getName());
	}
}

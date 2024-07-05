package com.example.domains.entities.models;

import java.io.Serializable;

import com.example.domains.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("serial")
@Data @AllArgsConstructor
public class LanguageDTO implements Serializable {
	private int languageId;
	private String name;
	
	public static LanguageDTO from(Language source)  {
		return new LanguageDTO(
				source.getLanguageId()
				,source.getName());
	}
	
	public static Language from(LanguageDTO source)  {
		return new Language(
				source.getLanguageId()
				,source.getName());
	}
}

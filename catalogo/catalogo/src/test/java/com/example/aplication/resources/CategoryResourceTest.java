package com.example.aplication.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.CategoryService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Category;
import com.example.domains.entities.models.ActorDTO;
import com.example.domains.entities.models.ActorShort;
import com.example.domains.entities.models.CategoryDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(CategoryResource.class)
class CategoryResourceTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private CategoryService srv;

	@Autowired
	ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testGetAllString() throws Exception {
		List<CategoryDTO> lista = new ArrayList<>(
		        Arrays.asList(new CategoryDTO(1, "Accion"),
		        		new CategoryDTO(2, "Drama"),
		        		new CategoryDTO(3, "Musical")));
		when(srv.getByProjection(CategoryDTO.class)).thenReturn(lista);
		mockMvc.perform(get("/api/category/v1?modo=short").accept(MediaType.APPLICATION_JSON))
			.andExpectAll(
					status().isOk(), 
					content().contentType("application/json"),
					jsonPath("$.size()").value(3)
					);
//		mvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_XML))
//			.andExpectAll(
//					status().isOk(), 
//					content().contentType("application/json"),
//					jsonPath("$.size()").value(3)
//					);
	}

	@Test
	void testGetAllPageable() throws Exception {
		List<CategoryDTO> lista = new ArrayList<>(
		        Arrays.asList(new CategoryDTO(1, "Accion"),
		        		new CategoryDTO(2, "Drama"),
		        		new CategoryDTO(3, "Musical")));

		when(srv.getByProjection(PageRequest.of(0, 20), CategoryDTO.class))
			.thenReturn(new PageImpl<>(lista));
		mockMvc.perform(get("/api/category/v1").queryParam("page", "0"))
			.andExpectAll(
				status().isOk(), 
				content().contentType("application/json"),
				jsonPath("$.content.size()").value(3),
				jsonPath("$.size").value(3)
				);
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Category(id, "Drama");
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/category/v1/{id}", id))
			.andExpect(status().isOk())
	        .andExpect(jsonPath("$.categoryId").value(id))
	        .andExpect(jsonPath("$.name").value(ele.getName()))
	        .andDo(print());
	}
	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		var ele = new Category(id, "Drama");
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/category/v1/{id}", id))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.title").value("Not Found"))
	        .andDo(print());
	}

	@Test
	void testCreate() throws Exception {
		int id = 1;
		var ele = new Category(id, "Drana");
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(post("/api/category/v1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(CategoryDTO.from(ele)))
			)
			.andExpect(status().isCreated())
	        .andExpect(header().string("Location", "http://localhost/api/category/v1/1"))
	        .andDo(print())
	        ;
	}
	
	@Test
	void testModify() throws Exception {
		int id = 1;
		var ele = new Category(id, "Drana");
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(put("/api/category/v1/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(CategoryDTO.from(ele)))
			)
			.andExpect(status().isNoContent())
	        .andDo(print())
	        ;
	}
	
	@Test
	void testModify400() throws Exception {
		int id = 1;
		var ele = new Category(id, "Drana");
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(put("/api/category/v1/2")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(CategoryDTO.from(ele)))
			)
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.title").value("Bad Request"))
			.andExpect(jsonPath("$.detail").value("No coinciden los identificadores"))
	        .andDo(print())
	        ;
	}


}

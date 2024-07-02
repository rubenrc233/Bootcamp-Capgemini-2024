package com.example.application.resources;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LanguageResource.class)
class LanguageResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LanguageRepository dao;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() throws Exception {
        Language language1 = new Language();
        language1.setLanguageId(1);
        language1.setName("English");
        Language language2 = new Language();
        language2.setLanguageId(2);
        language2.setName("Spanish");
        when(dao.findAll(Sort.by("name"))).thenReturn(Arrays.asList(language1, language2));

        mockMvc.perform(get("/idiomas/v1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("English")))
                .andExpect(jsonPath("$[1].name", is("Spanish")));
    }

    @Test
    void testGetOne() throws Exception {
        Language language = new Language();
        language.setLanguageId(1);
        language.setName("English");
        when(dao.findById(1)).thenReturn(Optional.of(language));

        mockMvc.perform(get("/idiomas/v1/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("English")));
    }

    @Test
    void testGetOne_NotFound() throws Exception {
        when(dao.findById(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/idiomas/v1/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAdd() throws Exception {
        Language language = new Language();
        language.setLanguageId(1);
        language.setName("French");
        when(dao.save(any(Language.class))).thenReturn(language);

        mockMvc.perform(post("/idiomas/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(language)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testModify() throws Exception {
        Language language = new Language();
        language.setLanguageId(1);
        language.setName("French");

        when(dao.findById(1)).thenReturn(Optional.of(language));
        when(dao.save(any(Language.class))).thenReturn(language);

        mockMvc.perform(put("/idiomas/v1/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(language)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("French")));
    }

    @Test
    void testDelete() throws Exception {
        when(dao.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/idiomas/v1/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_NotFound() throws Exception {
        when(dao.existsById(1)).thenReturn(false);

        mockMvc.perform(delete("/idiomas/v1/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}

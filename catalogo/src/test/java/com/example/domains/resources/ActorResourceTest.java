package com.example.domains.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.example.domains.entities.dtos.ActorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ActorResourceTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test  
    public void testGetAllV1() throws Exception {
        mockMvc.perform(get("/actores/v1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetAllV2() throws Exception {
        mockMvc.perform(get("/actores/v2"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetOneV1() throws Exception {
        mockMvc.perform(get("/actores/v1/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetOneV2() throws Exception {
        mockMvc.perform(get("/actores/v2/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetPelisV1() throws Exception {
        mockMvc.perform(get("/actores/v1/1/pelis"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetPelisV2() throws Exception {
        mockMvc.perform(get("/actores/v2/1/pelis"))
            .andExpect(status().isOk());
    }

    @Test
    public void testJubilar() throws Exception {
        mockMvc.perform(delete("/actores/v1/1/jubilacion"))
            .andExpect(status().isAccepted());
    }
    
    @Test
    public void testCreateV1() throws Exception {
        ActorDTO actorDTO = new ActorDTO(900,"Prueba","Prueba");
        
        mockMvc.perform(post("/actores/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(actorDTO)))
            .andExpect(status().isCreated());
    }

    

    @Test
    public void testModifyV1() throws Exception {
        ActorDTO actorDTO = new ActorDTO(1,"Prueba","Prueba");
        
        mockMvc.perform(put("/actores/v1/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(actorDTO)))
            .andExpect(status().isNoContent());
    }

    @Test
    public void testModifyV2() throws Exception {
        ActorDTO actorDTO = new ActorDTO(1,"Prueba","Prueba");

        mockMvc.perform(put("/actores/v2/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(actorDTO)))
            .andExpect(status().isNoContent());
    }
    

    @Test
    public void testDeleteV1() throws Exception {
        ActorDTO actorDTO = new ActorDTO(202,"Prueba","Prueba");
        mockMvc.perform(delete("/actores/v1/"+actorDTO.getActorId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(actorDTO)))
            .andExpect(status().isNoContent());
    }
}

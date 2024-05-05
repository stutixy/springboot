package com.stuti.database.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stuti.database.TestDataUtils;
import com.stuti.database.domain.entities.AuthorEntity;
import com.stuti.database.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc

public class AuthorControllerIntegrationTest {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private AuthorService authorService;
    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Aria Montgomery", 80);
        authorEntity.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201CreatedandMatched() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Aria Montgomery", 80);
        authorEntity.setId(null);
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber()
                ).andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Aria Montgomery"));

    }

    @Test
    public void testThatFindAllAuthorsSuccessfullyReturnsHttp201Created() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatFindAllAuthorsSuccessfullyCreated() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Aria Montgomery", 80);
        authorService.save(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].name").value("Aria Montgomery")
                )
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$[0].age").value(80)
                );

    }

    @Test
    public void testThatGetAuthorsSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Aria Montgomery", 80);
        authorService.save(authorEntity);
          mockMvc.perform(MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                );

    }

    @Test
    public void testThatGetAuthorsSuccessfullyReturnsHttp201CreatedandMatched() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Aria Montgomery", 80);
        authorService.save(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNumber()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Aria Montgomery")
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(80)
                );

    }

    @Test
    public void testThatFullUpdateAuthorsSuccessfullyReturnsHttp404IfAuthorDoesntExist() throws Exception {

        AuthorEntity updatedAuthorEntity = TestDataUtils.createTestAuthor(99L, "Stephen King", 75);
        String updatedAuthorJson = objectMapper.writeValueAsString(updatedAuthorEntity);
        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + updatedAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAuthorJson))
                .andExpect( MockMvcResultMatchers.status().isNotFound());

    }
    @Test
    public void testThatFullUpdateAuthorsSuccessfullyReturnsHttp201CreatedandMatched() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Stephen King", 80);
        AuthorEntity savedEntity = authorService.save(authorEntity);

        AuthorEntity updatedAuthorEntity = TestDataUtils.createTestAuthor(savedEntity.getId(), "Stephen King", 75);
        String updatedAuthorJson = objectMapper.writeValueAsString(updatedAuthorEntity);
        mockMvc.perform(MockMvcRequestBuilders.put("/authors/" + updatedAuthorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAuthorJson))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNumber()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Stephen King")
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(75)
                );

    }

    @Test
    public void testThatFPartialUpdateAuthorsSuccessfullyReturnsHttp201CreatedandMatched() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Stephen King", 80);
        AuthorEntity savedEntity = authorService.save(authorEntity);
        savedEntity.setName("Maria Montgomery");
        String updatedAuthorJson = objectMapper.writeValueAsString(savedEntity);
        mockMvc.perform(MockMvcRequestBuilders.patch("/authors/" + savedEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedAuthorJson))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.id").isNumber()
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.name").value("Maria Montgomery")
                ).andExpect(
                        MockMvcResultMatchers.jsonPath("$.age").value(80)
                );

    }

    @Test
    public void testThatDeleteAuthorsSuccessfullyReturnsHttp204() throws Exception {
        AuthorEntity authorEntity = TestDataUtils.createTestAuthor(1L, "Nma King", 90);
        authorService.save(authorEntity);
        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/" + authorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(
                        MockMvcResultMatchers.status().isNoContent()
                );

    }

}

package com.stuti.database.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stuti.database.TestDataUtils;
import com.stuti.database.domain.dto.AuthorDto;
import com.stuti.database.domain.dto.BookDto;
import com.stuti.database.domain.entities.BookEntity;
import com.stuti.database.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BookService bookService;
    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        BookDto bookDto = TestDataUtils.createTestBookDto("979-1-3892-8990-0", "The Shining", (AuthorDto) null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/979-1-3892-8990-0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("979-1-3892-8990-0")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The Shining"));

    }

    @Test
    public void testThatFindAllBookSuccessfullyReturnsHttp200Created() throws Exception {
        BookEntity bookEntity = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", null);
        bookService.createBook(bookEntity, "979-1-3892-8990-0");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").isString()
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("The Shining")
        );
    }

    @Test
    public void testThatFindBookSuccessfullyReturnsHttp200Created() throws Exception {
        BookEntity bookEntity = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", null);
        bookService.createBook(bookEntity, "979-1-3892-8990-0");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/979-1-3892-8990-0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatFindBookSuccessfullyReturnsHttp200CreatedandMatched() throws Exception {
        BookEntity bookEntity = TestDataUtils.createTestBook("979-1-3892-8990-0", "The Shining", null);
        bookService.createBook(bookEntity, "979-1-3892-8990-0");
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/979-1-3892-8990-0")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("979-1-3892-8990-0")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The Shining")
        );

    }

    @Test
    public void testThatFullUpdateBookSuccessfullyReturnsHttp200() throws Exception {
        BookDto bookDto = TestDataUtils.createTestBookDto("979-1-3892-8990-2", "The chamki", (AuthorDto) null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("979-1-3892-8990-2")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The chamki"));

    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201() throws Exception {
        BookEntity bookEntity = TestDataUtils.createTestBook("979-1-3892-8990-2", "The chamki",  null);
        bookService.createBook(bookEntity, bookEntity.getIsbn());
        BookDto bookDto = TestDataUtils.createTestBookDto(bookEntity.getIsbn(), "The chamkila", (AuthorDto) null);
        String bookJson = objectMapper.writeValueAsString(bookDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatPartialUpdtaeBookSuccessfullyReturnsHttp201() throws Exception {
        BookEntity bookEntity = TestDataUtils.createTestBook("979-1-3892-8990-2", "The chamki",  null);
        bookService.createBook(bookEntity, bookEntity.getIsbn());
        bookEntity.setTitle("The chamkila");
        String bookJson = objectMapper.writeValueAsString(bookEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("979-1-3892-8990-2")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The chamkila"));
    }

    @Test
    public void testThatDeleteBookSuccessfullyReturnsHttp204() throws Exception {
        BookEntity bookEntity = TestDataUtils.createTestBook("979-1-3892-8990-2", "The chamki",  null);
        bookService.createBook(bookEntity, bookEntity.getIsbn());
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/"+ bookEntity.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }



}

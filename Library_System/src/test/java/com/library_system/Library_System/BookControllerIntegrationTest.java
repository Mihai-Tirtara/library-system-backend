package com.library_system.Library_System;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library_system.Library_System.Exceptions.AiServiceException;
import com.library_system.Library_System.Model.Book;
import com.library_system.Library_System.Service.AiService;
import com.library_system.Library_System.Service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AiService aiService;

    @Autowired
    private BookService bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setIsbn("9781234567897");
        testBook.setPublicationYear(2024);
        testBook.setDescription("Test Description");
    }

    @Test
    void createBook_Success() throws Exception {
        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(testBook.getTitle()))
                .andExpect(jsonPath("$.author").value(testBook.getAuthor()));
    }

    @Test
    void getAllBooks_success() throws Exception {
        Book savedBook = bookService.createNewBook(testBook);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").exists());
    }

    @Test
    void createBook_ValidationFailure() throws Exception {
        testBook.setTitle(""); // Invalid title

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("title"));
    }

    @Test
    void getBook_Success() throws Exception {
        Book savedBook = bookService.createNewBook(testBook);

        mockMvc.perform(get("/api/books/{id}", savedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(testBook.getTitle()));
    }

    @Test
    void getBook_NotFound() throws Exception {
        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBook_Success() throws Exception {
        Book savedBook = bookService.createNewBook(testBook);
        testBook.setTitle("Updated Title");

        mockMvc.perform(put("/api/books/{id}", savedBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"));
    }

    @Test
    void deleteBook_Success() throws Exception {
        Book savedBook = bookService.createNewBook(testBook);

        mockMvc.perform(delete("/api/books/{id}", savedBook.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/books/{id}", savedBook.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void searchBooks_Success() throws Exception {
        bookService.createNewBook(testBook);

        mockMvc.perform(get("/api/books/search")
                .param("title", "Test Book")
                .param("author", "Test Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(testBook.getTitle()));
    }

    @Test
    void getAiInsights_Success() throws Exception {
        Book savedBook = bookService.createNewBook(testBook);
        String mockInsight = "This is a fascinating book about testing.";

        when(aiService.generateInsights(any(Book.class)))
                .thenReturn(mockInsight);

        mockMvc.perform(get("/api/books/{id}/ai-insights", savedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.insights").value(mockInsight));
    }

    @Test
    void getAiInsights_ServiceFailure() throws Exception {
        Book savedBook = bookService.createNewBook(testBook);

        when(aiService.generateInsights(any(Book.class)))
                .thenThrow(new AiServiceException("AI Service unavailable"));

        mockMvc.perform(get("/api/books/{id}/ai-insights", savedBook.getId()))
                .andExpect(status().isServiceUnavailable());
    }
}
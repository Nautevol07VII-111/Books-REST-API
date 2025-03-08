package com.nilejackson.books.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilejackson.books.domain.Book;
import com.nilejackson.books.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // No server started
@AutoConfigureMockMvc
public class BookControllerFullIT {

    
    @TestConfiguration
    static class TestConfig {
        @Bean
        public BookService bookService() {
            return mock(BookService.class);
        }
    }
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private Book testBook;
    
    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setIsbn("1234567890");
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setGenre("Test Genre");
        
        // Configure the mock
        when(bookService.createBook(any(Book.class))).thenReturn(testBook);
    }
    
    @Test
    public void testThatBookIsCreated() throws Exception {
        String bookJson = objectMapper.writeValueAsString(testBook);
        
        mockMvc.perform(put("/book/{isbn}", "1234567890")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testBook.getId()))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()))
                .andExpect(jsonPath("$.author").value(testBook.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(testBook.getIsbn()))
                .andExpect(jsonPath("$.genre").value(testBook.getGenre()));
    }
}
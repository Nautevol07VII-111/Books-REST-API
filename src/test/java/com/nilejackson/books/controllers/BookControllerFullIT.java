package com.nilejackson.books.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilejackson.books.TestData;
import com.nilejackson.books.domain.Book;
import com.nilejackson.books.services.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class) 
public class BookControllerFullIT {

    @Autowired
    private MockMvc mockMvc;
    
    private BookController bookController;
    @MockBean
    private BookService bookService;

    @BeforeEach
    public void setup() {
        bookController = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testThatBookIsCreated() throws Exception {
        // Use TestData class to create the test book
        Book testBook = TestData.testBook();
        
        // Configure mock to return the test book
        when(bookService.createBook(any(Book.class))).thenReturn(testBook);
        
        // Convert to JSON and perform the test
        String bookJson = objectMapper.writeValueAsString(testBook);
        
        mockMvc.perform(put("/book/{isbn}", testBook.getIsbn())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testBook.getId()))
                .andExpect(jsonPath("$.title").value(testBook.getTitle()))
                .andExpect(jsonPath("$.author").value(testBook.getAuthor()))
                .andExpect(jsonPath("$.isbn").value(testBook.getIsbn()))
                .andExpect(jsonPath("$.genre").value(testBook.getGenre()));
    }

    @Test 
    @GetMapping("/book/{id}")
    public void testToEnsureThatRetrievedBookReturns404WhenFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}", 1L)).andExpect(status().isNotFound());
    }




    @Test 
    public void testThatretrieveBookReturnsBookWhenBookExists() throws Exception {
     
        Book testBook = TestData.testBook();

        Long testId = 1L;

        when(bookService.findById(testId)).thenReturn(Optional.of(testBook));



        mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}", testId))
        .andExpect(status().isOk())
       
       .andExpect(jsonPath("$.title").value(testBook.getTitle()));

      
    }


    
    

    @Test 
    public void testThatGetAllBooksReturnsBookList() throws Exception {
     
        final Book book = TestData.testBook();
        bookService.createBook(book);

        Book savedBook = book;
        savedBook.setId(1L);

        when(bookService.createBook(any(Book.class))).thenReturn(savedBook);
        when(bookService.findAll()).thenReturn(Collections.singletonList(savedBook));



        mockMvc.perform(MockMvcRequestBuilders.get("/book/"))
        .andExpect(status().isOk());


    }
      
    
}
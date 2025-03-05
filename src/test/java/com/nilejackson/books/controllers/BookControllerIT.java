package com.nilejackson.books.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilejackson.books.TestData;
import com.nilejackson.books.domain.Book;
import com.nilejackson.books.services.BookService;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.main.allow-bean-definition-overriding=true"
})
public class BookControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Autowired
    private WebApplicationContext context;
    
    @BeforeEach
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        
        // Create a controller with our mock service
        BookController controller = new BookController(bookService);
        
        // Set up MockMvc with our controller
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .build();
    }
    
    @Test
    public void testThatBookIsCreated() throws Exception {
        final Book book = TestData.testBook();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String bookJson = objectMapper.writeValueAsString(book);
         when(bookService.createBook(any(Book.class))).thenReturn(book);
        mockMvc.perform((RequestBuilder) ((ResultActions) MockMvcRequestBuilders.put("/books/" + book.getIsbn())
        .contentType(MediaType.APPLICATION_JSON)
        .content(bookJson))
        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(book.getGenre()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.author").value(book.getAuthor()));
    }
}

package com.nilejackson.books.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nilejackson.books.domain.Book;
import com.nilejackson.books.domain.BookEntity;
import com.nilejackson.books.repositories.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    
    @Mock
    private BookRepository bookRepository;
    
    @InjectMocks
    private BookServiceImpl underTest;

    private Book testBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setGenre("Fiction");
        book.setCheckedOut(false);
        return book;
    }
    
    private BookEntity testBookEntity() {
        BookEntity entity = new BookEntity();
        entity.setId(1L);
        entity.setTitle("Test Book");
        entity.setAuthor("Test Author");
        entity.setIsbn("1234567890");
        entity.setGenre("Fiction");
        entity.setCheckedOut(false);
        return entity;
    }

    @BeforeEach
    public void setup() {
        reset(bookRepository);
    }
    
    @Test
    public void testThatBookIsSaved() {
    
        Book book = testBook();
        BookEntity bookEntity = testBookEntity();
        
        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);
        
       
        Book result = underTest.createBook(book);
        
        // Debug output
        System.out.println("Book fields: id=" + book.getId() + 
            ", title=" + book.getTitle() + 
            ", author=" + book.getAuthor() +
            ", isbn=" + book.getIsbn() +
            ", genre=" + book.getGenre() +
            ", checkedOut=" + book.isCheckedOut());
        System.out.println("Result fields: id=" + result.getId() + 
            ", title=" + result.getTitle() + 
            ", author=" + result.getAuthor() +
            ", isbn=" + result.getIsbn() +
            ", genre=" + result.getGenre() +
            ", checkedOut=" + result.isCheckedOut());
        
       
        verify(bookRepository).save(any(BookEntity.class));
        
        assertNotNull(result);
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getGenre(), result.getGenre());
        assertEquals(book.isCheckedOut(), result.isCheckedOut());
    }
    
    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBookIsPresent() {
       
        when(bookRepository.findById(5555555L)).thenReturn(Optional.empty());
        
       
        final Optional<Book> result = underTest.findById(5555555L);
        
       
        assertEquals(Optional.empty(), result);
    }
    
    @Test
    public void testThatFindByIdReturnsBookWhenBookExists() {
       
        final Long bookId = 1L;
        BookEntity bookEntity = testBookEntity();
        
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));
        
       
        final Optional<Book> result = underTest.findById(bookId);
        
       
        assertTrue(result.isPresent());
        assertEquals(bookId, result.get().getId());
        assertEquals("Test Book", result.get().getTitle());
    }

    @Test 
    public void listBooksReturnsEmptyListWhenNoBooksExist() {
       
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

      
        List<Book> result = underTest.listBooks();

       
        assertTrue(result.isEmpty());
    }

    @Test
    public void findAll_ReturnsOnlyAvailableBooks() {
      
        List<BookEntity> allBooks = new ArrayList<>();
        
        BookEntity availableBook1 = testBookEntity();
        availableBook1.setId(1L);
        availableBook1.setCheckedOut(false);
        allBooks.add(availableBook1);
        
        BookEntity availableBook2 = testBookEntity();
        availableBook2.setId(2L);
        availableBook2.setCheckedOut(false);
        allBooks.add(availableBook2);
        
        

        when(bookRepository.findAll()).thenReturn(allBooks);

      
        List<Book> result = underTest.findAll();

        
        assertEquals(2, result.size());
        
        List<Long> resultIds = result.stream()
            .map(Book::getId)
            .collect(Collectors.toList());
            
        assertTrue(resultIds.contains(1L));
        assertTrue(resultIds.contains(2L));
        
    }

    @Test 
    public void listBooksReturnsAllBooksRegardlessOfStatus() {
       
        List<BookEntity> allBooks = new ArrayList<>();
        
        BookEntity availableBook = testBookEntity();
        availableBook.setId(1L);
        availableBook.setCheckedOut(false);
        allBooks.add(availableBook);
        
        BookEntity checkedOutBook = testBookEntity();
        checkedOutBook.setId(2L);
        checkedOutBook.setCheckedOut(true);
        allBooks.add(checkedOutBook);

        when(bookRepository.findAll()).thenReturn(allBooks);
        
        
        List<Book> result = underTest.listBooks();

       
        assertEquals(2, result.size());
        
        List<Long> resultIds = result.stream()
            .map(Book::getId)
            .collect(Collectors.toList());
            
        assertTrue(resultIds.contains(1L));
        assertTrue(resultIds.contains(2L));
    }

    @Test
    public void isBookAvailable_ReturnsFalse_WhenNoBookWithTitleExists() {
       
        String title = "Non-existent Book";
        when(bookRepository.findByTitleIgnoreCase(title)).thenReturn(Collections.emptyList());

       
        Boolean result = underTest.isBookAvailable(title);

        
        assertFalse(result);
        verify(bookRepository).findByTitleIgnoreCase(title);
    }
}

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    

    
    @Test
    void testCreateBookReturnsCreatedStatus() throws Exception {
        
        Book book = new Book();
        book.setTitle("Integration Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("123-test-456");
        book.setGenre("Test");
        book.setCheckedOut(false);
        
       
        String bookJson = objectMapper.writeValueAsString(book);
        
       
        mockMvc.perform(MockMvcRequestBuilders
                .put("/book/123-test-456")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}

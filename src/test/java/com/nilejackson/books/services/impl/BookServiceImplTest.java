package com.nilejackson.books.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nilejackson.books.domain.BookEntity;

import com.nilejackson.books.services.BookService;
import com.nilejackson.books.domain.Book;
import com.nilejackson.books.repositories.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    
    @Mock
    private BookRepository bookrepository;
    
    @InjectMocks
    private BookServiceImpl underTest;
    //This ensures that when we create or save a book, we can then have that same book returned(this helps solidify our persistence layer)
    @Test
    public void testThatBookIsSaved() {
        final Book book = Book.builder()
        .isbn( "0385277199")
        .id((long) 777)
        .genre("Satire")
        .title("The Vertical Smile")
        .author("Richard Condon")
        .build();

        final BookEntity bookEntity = BookEntity.builder()
        .isbn( "0385277199")
        .id((long) 777)
        .genre("Satire")
        .title( "The Vertical Smile")
        .author( "Richard Condon")
        .build();
        //*In reference to the line below* When the save method is called with something that equals bookEntity, then return bookEntity." The eq() matcher is important because it matches based on object equality rather than object identity, which is usually what's preferred when testing with mocks.
        when(bookrepository.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result = underTest.create(book);
        assertEquals(book, result);
    }
}

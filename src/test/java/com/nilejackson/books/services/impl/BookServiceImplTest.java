package com.nilejackson.books.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nilejackson.books.domain.BookEntity;
import static com.nilejackson.books.TestData.tesBookEntity;

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
       

        final Book book = testBook();

       final BookEntity bookEntity = tesBookEntity();
        //*In reference to the line below* When the save method is called with something that equals bookEntity, then return bookEntity." The eq() matcher is important because it matches based on object equality rather than object identity, which is usually what's preferred when testing with mocks.
        when(bookrepository.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result = underTest.createBook(book);
        assertEquals(book, result);
    }

//Added this for bookEntity scope in test below it
    private BookEntity testBookEntity() {
      
        BookEntity entity = new BookEntity();
        
        return entity;
    }

    @Test
    public  void testThatFindByIdReturnsEmptyWhenNoBookIsPresent() {
        
        final Book book = testBook();

         final BookEntity bookEntity = testBookEntity();

        when(bookrepository.findById(eq(book.getId()))).thenReturn(Optional.of(bookEntity));
        final Optional<Book> result = underTest.findById(5555555L);
        assertEquals(Optional.empty(), result);
    }
 private Book testBook() {
    Book book = new Book();

    return book;
 }
    
    @Test
    
    public void testThatFindByIdReturnsBookWhenBookExists() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookrepository.findById(eq(book.getId()))).thenReturn(Optional.of(bookEntity));
        final Optional<Book> result = underTest.findById(book.getId());
        assertEquals(Optional.of(book), result);
    }

}

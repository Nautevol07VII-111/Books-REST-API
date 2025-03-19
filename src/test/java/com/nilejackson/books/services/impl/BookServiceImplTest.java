package com.nilejackson.books.services.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nilejackson.books.domain.BookEntity;
import static com.nilejackson.books.TestData.tesBookEntity;

import com.nilejackson.books.domain.Book;
import com.nilejackson.books.repositories.BookRepository;
import com.nilejackson.books.services.BookService;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    
    @Mock
    private BookRepository bookrepository;
    
    @InjectMocks
    private BookServiceImpl underTest;

    @Mock
    private BookService bookService;

   
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

    @Test 
    public void listBooksReturnsEmptyListWhenNoBooksExist() {
        when(bookrepository.findAll()).thenReturn(Collections.emptyList());

        List<Book> result = underTest.listBooks();

        assertTrue(result.isEmpty());
    }

    @Test
    public void findAll_ReturnsOnlyAvailableBooks() {
       
        BookEntity availableBook1 = testBookEntity();
        availableBook1.setId(1L);
        availableBook1.setTitle("The Manchurian Candidate");
        availableBook1.setCheckedOut(false);

        BookEntity availableBook2 = testBookEntity();
        availableBook2.setId(2L);
        availableBook2.setTitle("Mile High");
        availableBook2.setCheckedOut(false);

        BookEntity checkedOutBook1 = testBookEntity();
        checkedOutBook1.setId(3L);
        checkedOutBook1.setTitle("Prizzi's Honor");
        checkedOutBook1.setCheckedOut(true);

        BookEntity checkedOutBook2 = testBookEntity();
        checkedOutBook2.setId(4L);
        checkedOutBook2.setTitle("The Oldest Confession");
        checkedOutBook2.setCheckedOut(true);


        List<BookEntity> allBooks = new ArrayList<>();
            allBooks.add(availableBook1);
            allBooks.add(availableBook2);
            allBooks.add(checkedOutBook1);
            allBooks.add(checkedOutBook2);

       when(bookrepository.findAll()).thenReturn(allBooks);

       List<Book> result = underTest.findAll();

       assertEquals(2, result.size());

       List<Long> resultIds = result.stream()
       .map(Book::getId)
       .collect(Collectors.toList());

       assertTrue(resultIds.contains(1L));
       assertTrue(resultIds.contains(2L));
       assertFalse(resultIds.contains(3L));
       assertFalse(resultIds.contains(4L));
    }
           

}

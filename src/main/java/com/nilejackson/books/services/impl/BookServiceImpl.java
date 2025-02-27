package com.nilejackson.books.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nilejackson.books.domain.BookEntity;
import com.nilejackson.books.domain.Book;
import com.nilejackson.books.repositories.BookRepository;
import com.nilejackson.books.services.BookService;


@Service 
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    // Constructor injection lets Spring automatically wire up our repository
    // This way we don't have to create the repository object ourselves
    @Autowired //Here for dependecy injection
    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(final Book book) {
         // Step 1: Convert the incoming Book (API/domain model) to a BookEntity (database model)
        // Think of this as translating from "web language" to "database language"
       final BookEntity bookEntity = bookToBookEntity(book); 
       final BookEntity savedBookEntity = bookRepository.save(bookEntity);
       return bookEntityToBook(savedBookEntity);
    }

    private BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
        .isbn(book.getIsbn())
        .title(book.getTitle())
        .author(book.getAuthor())
        .build();
    }

    private Book bookEntityToBook(BookEntity bookEntity) {
        return Book.builder()
        .isbn(bookEntity.getIsbn())
        .title(bookEntity.getTitle())
        .author(bookEntity.getAuthor())
        .build();
    }

    
}

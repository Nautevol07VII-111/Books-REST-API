package com.nilejackson.books.services.impl;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.List;

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
    public Book createBook(final Book book) {
         // Step 1: Convert the incoming Book (API/domain model) to a BookEntity (database model)
        // Think of this as translating from "web language" to "database language"
       final BookEntity bookEntity = bookToBookEntity(book); 

        // Step 2: Save the entity to the database via the repository
        // The repository handles all the SQL stuff under the hood - we just pass the object
       final BookEntity savedBookEntity = bookRepository.save(bookEntity);

       //Step 3: We convert the saved Entity back to a Book object to return to the caller(likely, whatever controller method that received the initial HTTP request)
       return bookEntityToBook(savedBookEntity);
    }

    //This is a helper method that converts our API book object into a database BookEntity
    private BookEntity bookToBookEntity(Book book) {
        //Below we us the builder pattern(a design pattern used to 'build' complex objects. By utilizing the build pattern we call a series of methods to set each property individually and finish with the build() call to create the object, this is different from using setters b/c each property we 'set' here is immutbale which can prevent bugs that could occur from unexpected property changes )
        BookEntity entity = BookEntity.builder()
        
        .isbn(book.getIsbn())
        .id(book.getId())
        .genre(book.getGenre())
        .title(book.getTitle())
        .author(book.getAuthor())
        .build();

        

        entity.setCheckedOut(book.isCheckedOut());

        return entity;
    }
    //The helper method below converts  database entites back into API models(does the opposite of the method above) which completes the two way translation between our layers 
    private Book bookEntityToBook(BookEntity bookEntity) {
      
        if (bookEntity == null) return null;

        Book book = Book.builder()
        .isbn(bookEntity.getIsbn())
        .id(bookEntity.getId())
        .genre(bookEntity.getGenre())
        .title(bookEntity.getTitle())
        .author(bookEntity.getAuthor())
        .build();
        book.setCheckedOut(bookEntity.isCheckedOut());

        return book;
    }

    @Override
    public Optional<Book> findById(Long Id) {
        final Optional<BookEntity> foundBook = bookRepository.findById(Id);
        return foundBook.map(book -> bookEntityToBook(book));
    }

    

   @Override
    public List<Book> listBooks() {
     List<BookEntity> foundBookList = bookRepository.findAll();
     return foundBookList.stream()
     .map(entity -> bookEntityToBook(entity))
     .collect(Collectors.toList());
    }

    @Override 
    public List<Book> findAll() {
        List<BookEntity> allBookEntities = bookRepository.findAll();

        return allBookEntities.stream()
        .filter(entity -> !entity.isCheckedOut())
        .map(entity -> bookEntityToBook(entity))
        .collect(Collectors.toList());
    }

    @Override 
    public Boolean isBookAvailable(String title) {
     List<BookEntity> books = bookRepository.findByTitleIgnoreCase(title);

     return books.stream()
     .anyMatch(book -> !book.isCheckedOut());
     
    }

    @Override
    public Boolean bookIsUnavailable(String title) {
        List<BookEntity> books = bookRepository.findByTitleIgnoreCase(title);
        //If book doesnt exist at all or all books under specific are checked Out
        return books.isEmpty() || books.stream().allMatch(book -> book.isCheckedOut());
    }
        

    
}

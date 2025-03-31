package com.nilejackson.books.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nilejackson.books.domain.Book;
import com.nilejackson.books.domain.BookEntity;
import com.nilejackson.books.repositories.BookRepository;
import com.nilejackson.books.services.BookService;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public Book createBook(Book book) {
        System.out.println("Inside createBook method");
        BookEntity bookEntity = bookToBookEntity(book);
        System.out.println("Entity before save: " + bookEntity);
        
        try {
            BookEntity savedEntity = bookRepository.save(bookEntity);
            System.out.println("After save: " + savedEntity);
            return bookEntityToBook(savedEntity);
        } catch (Exception e) {
            System.out.println("Exception during save: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public Long getId(Long id) {
        return id;
    }
    
    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id)
                .map(this::bookEntityToBook);
    }
    
    @Override
    public List<Book> findAll() {
        List<BookEntity> allEntities = bookRepository.findAll();
        System.out.println("Total books from repository: " + allEntities.size());
        
        for (BookEntity entity : allEntities) {
            System.out.println("Book ID: " + entity.getId() + 
                              ", Title: " + entity.getTitle() + 
                              ", Checked out: " + entity.isCheckedOut());
        }
        
        List<Book> result = allEntities.stream()
                .filter(entity -> {
                    boolean keep = entity.isCheckedOut() == false;
                    System.out.println("Filtering book ID " + entity.getId() + 
                                      ": is checked out = " + entity.isCheckedOut() + 
                                      ", keeping = " + keep);
                    return keep;
                })
                .map(this::bookEntityToBook)
                .collect(Collectors.toList());
        
        System.out.println("After filtering: " + result.size() + " books");
        return result;
    }
    
    @Override
    public List<Book> listBooks() {
        return bookRepository.findAll()
                .stream()
                .map(this::bookEntityToBook)
                .collect(Collectors.toList());
    }
    
    @Override
    public Boolean isBookAvailable(String title) {
        List<BookEntity> books = bookRepository.findByTitleIgnoreCase(title);
        return !books.isEmpty() && books.stream().anyMatch(book -> !book.isCheckedOut());
    }
    
    @Override
    public Boolean bookIsUnavailable(String title) {
        return !isBookAvailable(title);
    }
    
    private Book bookEntityToBook(BookEntity bookEntity) {
        if (bookEntity == null) return null;
        
        Book book = new Book();
        book.setId(bookEntity.getId());
        book.setIsbn(bookEntity.getIsbn());
        book.setTitle(bookEntity.getTitle());
        book.setAuthor(bookEntity.getAuthor());
        book.setGenre(bookEntity.getGenre());
        book.setCheckedOut(bookEntity.isCheckedOut());
        
        return book;
    }
    
    private BookEntity bookToBookEntity(Book book) {
        if (book == null) return null;
        
        BookEntity entity = new BookEntity();
        entity.setId(book.getId());
        entity.setIsbn(book.getIsbn());
        entity.setTitle(book.getTitle());
        entity.setAuthor(book.getAuthor());
        entity.setGenre(book.getGenre());
        entity.setCheckedOut(book.isCheckedOut());
        
        return entity;
    }
}
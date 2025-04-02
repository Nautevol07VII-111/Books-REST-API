package com.nilejackson.books.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Collection;
import org.springframework.http.ResponseEntity;

import com.nilejackson.books.domain.Book;
import com.nilejackson.books.domain.BookEntity;

public interface BookService {
   
    Book createBook(Book book);

    public Long getId(Long Id);
    
    
    public Optional<Book> findById(Long Id);

    public List<Book> findAll();

    public List<Book> listBooks();

    public Boolean isBookAvailable(String title);

    public Boolean bookIsUnavailable(String title);

    public  void deleteById(Long id);

    public List<Book> saveAll(List<Book> books);

    public long count();
    
    

}

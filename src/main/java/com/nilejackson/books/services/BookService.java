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
    
    
    public Optional<Book> findById(Long Id);

    public List<Book> findAll();

    public List<Book> listBooks();
    
    

}

package com.nilejackson.books.services;

import java.util.List;
import java.util.Optional;

import com.nilejackson.books.domain.Book;

public interface BookService {
   
    Book createBook(Book book);
    
    
    public Optional<Book> findById(Long Id);

    public List<Book> findAll();
}

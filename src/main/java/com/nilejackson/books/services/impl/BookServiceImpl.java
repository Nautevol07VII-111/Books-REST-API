package com.nilejackson.books.services.impl;
import org.springframework.stereotype.Service;

import com.nilejackson.books.domain.Book;
import com.nilejackson.books.repositories.BookRepository;
import com.nilejackson.books.services.BookService;


@Service 
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(final Book book) {
       
        return null;
    }
    
}

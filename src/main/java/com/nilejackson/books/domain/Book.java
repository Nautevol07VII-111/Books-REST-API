package com.nilejackson.books.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Book {
    
    private Long id;
    private String author; 
    private String isbn;
    private String title; 
    private String genre;
    private List<Book> relatedBooks;
    
}

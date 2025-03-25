package com.nilejackson.books.domain;

import java.util.List;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "books")
public class Book {
    
    private Long id;
    private String author; 
    private String isbn;
    private String title; 
    private String genre;
    private List<Book> relatedBooks;
    
}

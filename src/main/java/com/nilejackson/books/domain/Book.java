package com.nilejackson.books.domain;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    private Long id;
    private String author;
    private String isbn;
    private String title;
    private String genre;
    private List<Book> relatedBooks;
    private boolean checkedOut;  
    
    
    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;  
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Book> getRelatedBooks() {
        return relatedBooks;
    }

    public void setRelatedBooks(List<Book> relatedBooks) {
        this.relatedBooks = relatedBooks;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }
 
}
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
 
}
package com.nilejackson.books.domain;

import java.io.ObjectInputFilter.Status;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Calling Lombok to generate boilerplate 
@AllArgsConstructor
@NoArgsConstructor //Added this constructor generator because SpringBoot actually does look for a no args constructor/ JPA Hibernate needs this for entity instances when loading from db--JSON also uses this to instantiate objects during serialization 
@Builder //establishes reliable pattern for building objects
@Entity
@Table(name = "books") // Uses Standard Java Enterprise Edition API for JPA 
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String author; 
    @Column(unique = true)
    private String isbn;
    private String title; 
    private String genre;
    private boolean isCheckedOut;
    private Status status;
    private Date checkOutDate;

    public void checkOut() {
        this.isCheckedOut = true;
    }

    public void returnBook() {
        this.isCheckedOut = false;
    }
    
    
    public boolean isCheckedOut() {
        
        return this.isCheckedOut;
    }
}

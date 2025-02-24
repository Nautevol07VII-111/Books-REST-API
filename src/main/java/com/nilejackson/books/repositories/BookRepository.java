package com.nilejackson.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nilejackson.books.domain.BookEntity;

//essentially this is a class that allows for CRUD actions to take place! Right click the JpaRepository annotation and select implementation to see what's going on under the hood.
@Repository //This allows us to utilize Spring's implemetation(a bunch of methods/interfaces that actually extend other interfaces that access even more methods) of the book repo which saves time and effort on our end
public interface BookRepository extends JpaRepository<BookEntity, Long>{
    
}

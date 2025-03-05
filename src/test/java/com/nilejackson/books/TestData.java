package com.nilejackson.books;

import com.nilejackson.books.domain.Book;
import com.nilejackson.books.domain.BookEntity;

public class TestData {
    private TestData() {

    }

    public static Book testBook() {
        return Book.builder()
        .isbn( "0385277199")
        .id((long) 777)
        .genre("Satire")
        .title("The Vertical Smile")
        .author("Richard Condon")
        .build();
        
    }

    public static BookEntity tesBookEntity() {
        return BookEntity.builder()
        .isbn( "0385277199")
        .id((long) 777)
        .genre("Satire")
        .title("The Vertical Smile")
        .author("Richard Condon")
        .build();
    }
}

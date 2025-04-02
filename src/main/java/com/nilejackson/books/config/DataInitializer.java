package com.nilejackson.books.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.nilejackson.books.domain.BookEntity;
import com.nilejackson.books.repositories.BookRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Autowired
    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override 
    public void run(String... args ) {
        //ONly populate data if database is empty 
        if (bookRepository.count() == 0) {
            createSampleBooks();
        }
    }

    private void createSampleBooks() {
       BookEntity book1 = new BookEntity();
        book1.setTitle("Hitchhiker's Guide to the Galaxy");
        book1.setAuthor("Douglas Adams");
        book1.setIsbn("978-0061120084");
        book1.setGenre("Science Fiction");
        book1.setCheckedOut(false);
        bookRepository.save(book1); 

        BookEntity book2 = new BookEntity();
        book1.setTitle("The Manchurian Candidate");
        book1.setAuthor("Richard Condon");
        book1.setIsbn("978-0061120082");
        book1.setGenre("Political Fiction");
        book1.setCheckedOut(false);
        bookRepository.save(book2);

        BookEntity book3 = new BookEntity();
        book1.setTitle("Brave New World");
        book1.setAuthor("Aldous Huxley");
        book1.setIsbn("978-0061120083");
        book1.setGenre("Science Fiction");
        book1.setCheckedOut(false);
        bookRepository.save(book3);

        System.out.println("Sample books created successfully!");
    }
    
}

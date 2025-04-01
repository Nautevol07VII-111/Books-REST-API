package com.nilejackson.books.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Id;

import com.nilejackson.books.domain.Book;
import com.nilejackson.books.services.BookService;

@RestController
@RequestMapping("/book")
@Tag(name = "Book Controller", description = "API endpoints for book management")
public class BookController {
    private final BookService bookService;
    
    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }
    
    @PutMapping(path = "/{isbn}")
    @Operation(summary = "Create a book", description = "Creates a new book with the provided ISBN")
    public ResponseEntity<Book> createBook(@PathVariable final String isbn, @RequestBody final Book book) {
        book.setIsbn(isbn);
        final Book savedBook = bookService.createBook(book);
        final ResponseEntity<Book> response = new ResponseEntity<Book>(savedBook, HttpStatus.CREATED);
        return response;
    }
    
    @GetMapping(path = "/{id}")
    @Operation(summary = "Find book by ID", description = "Returns a book by its ID")
    public ResponseEntity<Book> retrieveBook(@PathVariable final Long id) {
        final Optional<Book> foundBook = bookService.findById(id);
        return foundBook
            .map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
            .orElse(new ResponseEntity<>( HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/available")
    @Operation(summary = "Get available books", description = "Returns all books that are available (not checked out)")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> book = bookService.findAll();
        if(book.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
    
    @GetMapping(path = "/all")
    @Operation(summary = "Get all books", description = "Returns all books regardless of availability status")
    public ResponseEntity<List<Book>> listBooks() {
        List<Book> book = bookService.listBooks();
        if(book.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete a book", responses = {
        @ApiResponse(responseCode = "204", description = "Book successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Book not found")
        }
    )
    public ResponseEntity<Void> deleteBook(@PathVariable final Long id) {
        
        Optional<Book> book = bookService.findById(id);
        if(!book.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND)
        }

        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
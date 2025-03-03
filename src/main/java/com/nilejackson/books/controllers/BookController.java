package com.nilejackson.books.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nilejackson.books.domain.Book;
import com.nilejackson.books.services.BookService;

@Controller //The controller annotation marks a class as a web controller(a thing that handles HTTP requests) which is a Spring Component. Due to the fact that the @Controller class is a Spring Component means that classes declared with this annotation are all eligble for dependency injection
public class BookController {

    private final BookService bookService;
    
    @Autowired //This AutoWired Annotation gives us the ability to automatically add dependencies as needed instead of having to configure them manually 
    //To elaborate on the comment above, Spring to inject a dependency, in the case of My library API, Spring would inject something like BookServiceImpl whenever necessary-> This is a good example of loose coupling (the controller here only depends on the interface)
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }
    

    //Below we utilize something called a ResponseEntity which is an object from the Spring library that allows us to control things like response codes 
    //The @RequestBody annotation tells Spring to deserialize the request body into the annotated parameter. Ex: In this API incoming JSON in the request body is converted into book objects. 
    //@PutMapping maps HTTP PUT requests to this specific method
    @PutMapping(path = "/book/{isbn}")
    public ResponseEntity<Book> createBook(@RequestBody final Book book) {
            return new ResponseEntity<>(null,HttpStatus.CREATED);
        
    }


    
}

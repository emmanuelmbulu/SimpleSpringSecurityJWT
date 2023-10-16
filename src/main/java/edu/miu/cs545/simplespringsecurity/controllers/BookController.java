package edu.miu.cs545.simplespringsecurity.controllers;

import edu.miu.cs545.simplespringsecurity.model.Book;
import edu.miu.cs545.simplespringsecurity.services.BookService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    final private BookService _service;
    
    public BookController(@Qualifier("bookServiceImplementation") BookService service) {
        _service = service;
    }
    
    @GetMapping("")
    public List<Book> getAllBooks() {
        return _service.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable(name = "id") Long bookId) {
        if(bookId == null || bookId == 0) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We could not find the book with the provided ID.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        Book book = _service.getBookById(bookId);
        if(book == null) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We could not find the book with the provided ID.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> postAddBook(@RequestBody Book book) {
        if(book == null) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We could not find data for the book to create";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        String title = book.getTitle();
        if(title == null || title.trim().isEmpty()) {
            Object result = new Object(){
                public int code = 1;
                public String message = "Please provide the title for this book";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        title = title.trim();
        if(_service.getBookByTitle(title) != null) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We found another book with the same title.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        book.setTitle(title);

        String isbn = book.getIsbn();
        if(isbn == null || isbn.trim().isEmpty()) {
            Object result = new Object(){
                public int code = 1;
                public String message = "Please provide the isbn for this book.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        isbn = isbn.trim();
        if(_service.getBookByIsbn(isbn) != null) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We found another book with the same ISBN number.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        book.setIsbn(isbn);

        LocalDate publishedDate = book.getPublishedDate();
        if(publishedDate.isEqual(LocalDate.MIN)) {
            Object result = new Object(){
                public int code = 1;
                public String message = "Please provide a valid date for the published date. Make sure to use the format YYYY-MM-DD (Ex: 2023-12-25)";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        book = _service.save(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> postAddBook(
            @PathVariable(name = "id") Long bookId,
            @RequestBody Book book) {
        if(bookId == null || bookId == 0) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We could not find the book with the provided ID.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        if(book == null) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We could not find data for the book to update";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        String title = book.getTitle();
        if(title != null) {
            title = title.trim();
            if(title.isEmpty()) {
                Object result = new Object(){
                    public int code = 1;
                    public String message = "Please provide the title for the book.";
                };
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            Book existingBook = _service.getBookByTitle(title);
            if(existingBook != null && existingBook.getId() != bookId) {
                Object result = new Object(){
                    public int code = 1;
                    public String message = "We found another book with this title.";
                };
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            book.setTitle(title);
        }



        String isbn = book.getIsbn();
        if(isbn != null) {
            isbn = isbn.trim();

            if(isbn.isEmpty()) {
                Object result = new Object(){
                    public int code = 1;
                    public String message = "Please provide the isbn for this book.";
                };
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }

            Book existingBook = _service.getBookByIsbn(isbn);
            if(existingBook != null && existingBook.getId() != bookId) {
                Object result = new Object(){
                    public int code = 1;
                    public String message = "We found another book with the same ISBN number.";
                };
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
            book.setIsbn(isbn);
        }

        LocalDate publishedDate = book.getPublishedDate();
        if(publishedDate != null) {
            if(publishedDate.isEqual(LocalDate.MIN)) {
                Object result = new Object(){
                    public int code = 1;
                    public String message = "Please provide a valid date for the published date. Make sure to use the format YYYY-MM-DD (Ex: 2023-12-25)";
                };
                return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
            }
        }

        book = _service.update(bookId, book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable(name = "id") Long bookId) {
        if(bookId == null || bookId == 0) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We could not find the book with the provided ID.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        Book book = _service.delete(bookId);
        if(book == null) {
            Object result = new Object(){
                public int code = 1;
                public String message = "We could not find the book with the provided ID.";
            };
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }
}

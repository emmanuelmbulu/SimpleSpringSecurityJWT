package edu.miu.cs545.simplespringsecurity.services;

import edu.miu.cs545.simplespringsecurity.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    List<Book> getAllBooks();
    List<Book> getAllBooksOfAuthor(String author);
    Book getBookById(Long id);
    Book getBookByIsbn(String isbn);
    Book getBookByTitle(String title);
    Book save(Book book);
    Book update(Long id, Book book);
    Book delete(Long id);

}

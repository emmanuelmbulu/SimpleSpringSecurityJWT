package edu.miu.cs545.simplespringsecurity.services.implementations;

import edu.miu.cs545.simplespringsecurity.model.Book;
import edu.miu.cs545.simplespringsecurity.repositories.BookRepository;
import edu.miu.cs545.simplespringsecurity.services.BookService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BookServiceImplementation implements BookService {
    final private BookRepository _repository;

    public BookServiceImplementation(BookRepository repository) {
        this._repository = repository;
    }
    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        Iterable<Book> data = _repository.findAll();
        for (Book book : data) books.add(book);
        return books;
    }

    @Override
    public List<Book> getAllBooksOfAuthor(String author) {
        List<Book> books = new ArrayList<>();
        for (Book book : _repository.findAllByAuthor(author)) books.add(book);
        return books;
    }

    @Override
    public Book getBookById(Long id) {
        return _repository.findById(id).orElse(null);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return _repository.findByIsbn(isbn).orElse(null);
    }

    @Override
    public Book getBookByTitle(String title) {
        return _repository.findByTitleIgnoreCase(title).orElse(null);
    }

    @Override
    public Book save(Book book) {
        return _repository.save(book);
    }

    @Override
    public Book update(Long id, Book book) {
        Book bookToUpdate = getBookById(id);
        if(bookToUpdate == null) return null;

        if(book.getTitle() != null && !book.getTitle().isEmpty()) {
            bookToUpdate.setTitle(book.getTitle());
        }
        if(book.getAuthor() != null && !book.getAuthor().isEmpty()) {
            bookToUpdate.setAuthor(book.getAuthor());
        }
        if(book.getIsbn() != null && !book.getIsbn().isEmpty()) {
            bookToUpdate.setIsbn(book.getIsbn());
        }
        if(book.getPublishedDate() != null && !book.getPublishedDate().isEqual(LocalDate.MIN)) {
            bookToUpdate.setPublishedDate(book.getPublishedDate());
        }
        return _repository.save(bookToUpdate);
    }

    @Override
    public Book delete(Long id) {
        Book book = getBookById(id);
        if(book == null) return null;

        _repository.delete(book);
        return book;
    }
}

package edu.miu.cs545.simplespringsecurity.repositories;

import edu.miu.cs545.simplespringsecurity.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    Iterable<Book> findAllByAuthor(String author);
    Optional<Book> findByIsbn(String isbn);
    Optional<Book> findByTitleIgnoreCase(String title);
}

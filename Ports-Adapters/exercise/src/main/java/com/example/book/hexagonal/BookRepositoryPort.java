package com.example.hexagonal.port;

import com.example.hexagonal.Book;
import java.util.Optional;

// Port wychodzący: definiuje interfejs dla repozytorium książek
public interface BookRepositoryPort {
    Book save(Book book);
    Optional<Book> findById(String id);
    Optional<Book> findByTitle(String title); // Dodane dla logiki biznesowej
    void deleteById(String id);
}

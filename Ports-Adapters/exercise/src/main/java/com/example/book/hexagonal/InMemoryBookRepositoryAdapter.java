package com.example.hexagonal.adapter;

import com.example.hexagonal.Book;
import com.example.hexagonal.port.BookRepositoryPort;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// Adapter, który implementuje port BookRepositoryPort i przechowuje dane w pamięci
public class InMemoryBookRepositoryAdapter implements BookRepositoryPort {
    private final Map<String, Book> storage = new HashMap<>();

    @Override
    public Book save(Book book) {
        if (book.getId() == null || book.getId().isEmpty()) {
            book.setId(UUID.randomUUID().toString()); // Generuj ID
        }
        storage.put(book.getId(), book);
        System.out.println("Hexagonal: Saved book " + book.getTitle() + " to In-Memory storage.");
        return book;
    }

    @Override
    public Optional<Book> findById(String id) {
        System.out.println("Hexagonal: Fetching book with ID " + id + " from In-Memory storage.");
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        System.out.println("Hexagonal: Fetching book with title " + title + " from In-Memory storage.");
        return storage.values().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    @Override
    public void deleteById(String id) {
        System.out.println("Hexagonal: Deleting book with ID " + id + " from In-Memory storage.");
        storage.remove(id);
    }
}

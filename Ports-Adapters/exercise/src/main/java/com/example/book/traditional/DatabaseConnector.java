package com.example.traditional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Symuluje połączenie z bazą danych i operacje CRUD
public class DatabaseConnector {
    private final Map<String, Book> storage = new HashMap<>();
    private int nextId = 1;

    public Book save(Book book) {
        if (book.getId() == null || book.getId().isEmpty()) {
            book.setId(String.valueOf(nextId++)); // Generuj ID
        }
        storage.put(book.getId(), book);
        System.out.println("Traditional: Saved book " + book.getTitle() + " to DB.");
        return book;
    }

    public Optional<Book> findById(String id) {
        System.out.println("Traditional: Fetching book with ID " + id + " from DB.");
        return Optional.ofNullable(storage.get(id));
    }

    public void deleteById(String id) {
        System.out.println("Traditional: Deleting book with ID " + id + " from DB.");
        storage.remove(id);
    }
}

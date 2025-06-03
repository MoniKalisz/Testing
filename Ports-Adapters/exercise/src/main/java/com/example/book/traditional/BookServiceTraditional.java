package com.example.traditional;

import java.util.Optional;
import java.util.UUID;

// Serwis, który bezpośrednio zależy od DatabaseConnector
public class BookServiceTraditional {

    private final DatabaseConnector databaseConnector; // Bezpośrednia zależność do implementacji

    public BookServiceTraditional() {
        this.databaseConnector = new DatabaseConnector(); // Tworzenie instancji wewnątrz serwisu
    }

    public Book addBook(String title, String author) {
        // Logika biznesowa: sprawdzenie unikalności tytułu (uproszczone)
        // W realnej aplikacji szukalibyśmy po tytule, ale tu pominiemy dla uproszczenia
        System.out.println("Traditional: Adding new book: " + title + " by " + author);
        Book newBook = new Book(UUID.randomUUID().toString(), title, author); // ID generowane tutaj
        return databaseConnector.save(newBook);
    }

    public Optional<Book> getBookById(String id) {
        return databaseConnector.findById(id);
    }

    public boolean deleteBook(String id) {
        if (databaseConnector.findById(id).isPresent()) {
            databaseConnector.deleteById(id);
            return true;
        }
        return false;
    }
}

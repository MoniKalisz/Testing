package com.example.hexagonal.domain;

import com.example.hexagonal.Book;
import com.example.hexagonal.port.BookRepositoryPort;

import java.util.Optional;
import java.util.UUID;

// Serwis logiki biznesowej, który zależy WYŁĄCZNIE od interfejsu (portu)
public class BookServiceHexagonal {

    private final BookRepositoryPort bookRepository; // Zależność do portu (interfejsu)

    public BookServiceHexagonal(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository; // Wstrzykiwanie zależności
    }

    public Book addBook(String title, String author) {
        // Logika biznesowa: sprawdzenie unikalności tytułu
        if (bookRepository.findByTitle(title).isPresent()) {
            throw new IllegalArgumentException("Książka o tytule '" + title + "' już istnieje.");
        }

        System.out.println("Hexagonal: Adding new book: " + title + " by " + author);
        Book newBook = new Book(null, title, author); // ID będzie ustawione przez adapter (repozytorium)
        return bookRepository.save(newBook);
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public boolean deleteBook(String id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

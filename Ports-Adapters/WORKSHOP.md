Ports-Adapters

Write unit tests for BookServiceHexagonal and BookServiceTraditional. Note how much easier it is to bracket the dependencies and test the business logic itself.


Required tests:

    testAddBookSuccess:
 Verify that the addBook method correctly adds and saves a new book when the title does not exist.
        Ensure that bookRepository.findByTitle is called and returns Optional.empty().
        Make sure that bookRepository.save is called with the newly created Book object and returns that object (with already generated ID).
    testAddBookAlreadyExists:
 Verify that the addBook method throws an IllegalArgumentException when a book with the specified title already exists.
        Ensure that bookRepository.findByTitle is called and returns an Optional containing an existing book.
        Ensure that bookRepository.save is not called.
 testGetBookById:
 Verify that the getBookById method retrieves the book correctly.
        Ensure that bookRepository.findById is called and returns Optional containing the book.
    testDeleteBookSuccess:
 Verify that the deleteBook method correctly deletes an existing book.
        Ensure that bookRepository.findById is called and returns Optional containing the book.
        Ensure that bookRepository.deleteById is called.
        The method should return true.
 testDeleteBookNotFound:
 Verify that the deleteBook method works correctly when the book does not exist.
        Ensure that bookRepository.findById is called and returns Optional.empty().
        Make sure bookRepository.deleteById is not called.
        The method should return false.

Additional Integration Test
 testAddBookWithInMemoryRepository:
 Create an instance of BookServiceHexagonal with InMemoryBookRepositoryAdapter.
        Add a few books and then try to download them to verify that the data is correctly stored and retrieved from the in-memory adapter.
        Comment: this test is already an integration test because it uses the actual repository implementation, but it still only tests the service logic in conjunction with one of its implementations.

Podsumowanie i Dyskusja

Po napisaniu wszystkich testów, zastanów się i porównaj:

    Łatwość pisania testów jednostkowych: Gdzie było łatwiej izolować i testować samą logikę biznesową?
    Szybkość testów: Które testy były szybsze (jednostkowe vs integracyjne)?
    Zmiana implementacji: Jak łatwo byłoby zmienić "bazę danych" w obu aplikacjach (np. z pamięci na plik, a z pliku na prawdziwą bazę SQL/NoSQL)?
    Zależności: Gdzie zależności są bardziej ukryte, a gdzie są jasno zadeklarowane?

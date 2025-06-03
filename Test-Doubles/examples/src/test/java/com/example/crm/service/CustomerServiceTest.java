package com.example.crm.service;

import com.example.crm.model.Customer;
import com.example.crm.notification.Notifier;
import com.example.crm.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    // Dummy: obiekt potrzebny, ale niewykorzystywany
    static class DummyNotifier implements Notifier {
        @Override
        public void sendWelcomeMessage(Customer customer) {
            // nic nie robi
        }
    }

    // Fake: uproszczona implementacja repozytorium (np. w pamięci)
    static class FakeCustomerRepository implements CustomerRepository {
        private final Map<String, Customer> db = new HashMap<>();

        @Override
        public void save(Customer customer) {
            db.put(customer.getId(), customer);
        }

        @Override
        public Optional<Customer> findById(String id) {
            return Optional.ofNullable(db.get(id));
        }
    }

    // Stub: zwraca predefiniowane dane
    static class StubRepository implements CustomerRepository {
        @Override
        public void save(Customer customer) {
            // nieistotne dla testu
        }

        @Override
        public Optional<Customer> findById(String id) {
            return Optional.of(new Customer(id, "Stub", "stub@example.com"));
        }
    }

    @Test
    void testWithDummyNotifier() {
        // given
        CustomerRepository repo = new FakeCustomerRepository();
        Notifier dummyNotifier = new DummyNotifier();
        CustomerService service = new CustomerService(repo, dummyNotifier);
        Customer customer = new Customer("1", "Alice", "alice@example.com");

        // when
        service.registerCustomer(customer);

        // then
        assertTrue(repo.findById("1").isPresent());
    }

    @Test
    void testWithFakeRepository() {
        // given
        FakeCustomerRepository repo = new FakeCustomerRepository();
        Notifier dummyNotifier = customer -> {}; // Dummy
        CustomerService service = new CustomerService(repo, dummyNotifier);
        Customer customer = new Customer("2", "Bob", "bob@example.com");

        // when
        service.registerCustomer(customer);

        // then
        assertTrue(repo.findById("2").isPresent());
    }

    @Test
    void testWithStubRepository() {
        // given
        StubRepository stubRepo = new StubRepository();
        Notifier dummyNotifier = customer -> {};
        CustomerService service = new CustomerService(stubRepo, dummyNotifier);

        // when
        Optional<Customer> found = stubRepo.findById("any-id");

        // then
        assertTrue(found.isPresent());
        assertEquals("Stub", found.get().getName());
    }

    @Test
    void testWithMockNotifier() {
        // given
        CustomerRepository repo = new FakeCustomerRepository();
        Notifier mockNotifier = mock(Notifier.class);
        CustomerService service = new CustomerService(repo, mockNotifier);
        Customer customer = new Customer("3", "Carol", "carol@example.com");

        // when
        service.registerCustomer(customer);

        // then
        verify(mockNotifier).sendWelcomeMessage(customer);
    }

    @Test
    void testWithSpyRepository() {
        // given
        CustomerRepository realRepo = new FakeCustomerRepository();
        CustomerRepository spyRepo = spy(realRepo);
        Notifier dummyNotifier = customer -> {};
        CustomerService service = new CustomerService(spyRepo, dummyNotifier);
        Customer customer = new Customer("4", "Dave", "dave@example.com");

        // when
        service.registerCustomer(customer);

        // then
        verify(spyRepo).save(customer);
    }
}

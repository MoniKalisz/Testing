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

    @Test
    void shouldRegisterCustomerAndWelcomeThem() {
        // given
        CustomerRepository repo = new FakeCustomerRepository();
        TrackingNotifier notifier = new TrackingNotifier(); // specjalny komponent testowy
        CustomerService service = new CustomerService(repo, notifier);
        Customer customer = new Customer("10", "Anna", "anna@example.com");

        // when
        service.registerCustomer(customer);

        // then
        assertTrue(repo.findById("10").isPresent(), "Klient powinien zostać zarejestrowany");
        assertTrue(notifier.wasMessageSentTo("anna@example.com"), "Powitanie powinno zostać wysłane");
    }
}

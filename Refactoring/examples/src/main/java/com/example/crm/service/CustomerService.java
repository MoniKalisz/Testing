package com.example.crm.service;

import com.example.crm.model.Customer;
import com.example.crm.notification.Notifier;
import com.example.crm.repository.CustomerRepository;

public class CustomerService {
    private final CustomerRepository repository;
    private final Notifier notifier;

    public CustomerService(CustomerRepository repository, Notifier notifier) {
        this.repository = repository;
        this.notifier = notifier;
    }

    public void registerCustomer(Customer customer) {
        if (customer.getEmail() == null) {
            throw new IllegalArgumentException("Email is required");
        }
        repository.save(customer);
        notifier.sendWelcomeMessage(customer);
    }
}

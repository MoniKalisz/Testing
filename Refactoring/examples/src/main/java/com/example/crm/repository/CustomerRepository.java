package com.example.crm.repository;

import com.example.crm.model.Customer;
import java.util.Optional;

public interface CustomerRepository {
    void save(Customer customer);
    Optional<Customer> findById(String id);
}

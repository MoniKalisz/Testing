package com.example.crm.notification;

import com.example.crm.model.Customer;

public interface Notifier {
    void sendWelcomeMessage(Customer customer);
}

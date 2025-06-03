package com.example.loan;

public interface NotificationService {
    void notifyApproval(String applicationId);
    void notifyRejection(String applicationId);
}
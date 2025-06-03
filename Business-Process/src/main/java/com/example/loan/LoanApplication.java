package com.example.loan;

public class LoanApplication {
    private final String id;
    private LoanStatus status;

    public LoanApplication(String id) {
        this.id = id;
        this.status = LoanStatus.NEW;
    }

    public String getId() {
        return id;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }
}
package com.example.loan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanApplicationStateMachineTest {

    private LoanProcessService service;

    @BeforeEach
    void setUp() {
        service = new LoanProcessService(id -> {});
    }

    @Test
    void fullHappyPath() {
        LoanApplication app = new LoanApplication("123");

        service.review(app);
        assertEquals(LoanStatus.UNDER_REVIEW, app.getStatus());

        service.approve(app);
        assertEquals(LoanStatus.APPROVED, app.getStatus());

        service.disburse(app);
        assertEquals(LoanStatus.DISBURSED, app.getStatus());
    }
}
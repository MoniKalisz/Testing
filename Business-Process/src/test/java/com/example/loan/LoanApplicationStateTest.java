package com.example.loan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanApplicationStateTest {

    private LoanProcessService service;

    @BeforeEach
    void setUp() {
        service = new LoanProcessService(id -> {});
    }

    @Test
    void shouldMoveToUnderReviewFromNew() {
        LoanApplication app = new LoanApplication("123");

        service.review(app);

        assertEquals(LoanStatus.UNDER_REVIEW, app.getStatus());
    }

    @Test
    void shouldNotApproveIfNotUnderReview() {
        LoanApplication app = new LoanApplication("123");

        service.approve(app);

        assertEquals(LoanStatus.NEW, app.getStatus());
    }
}
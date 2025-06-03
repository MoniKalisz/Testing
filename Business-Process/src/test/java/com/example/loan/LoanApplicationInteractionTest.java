package com.example.loan;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class LoanApplicationInteractionTest {

    private NotificationService notificationService;
    private LoanProcessService service;

    @BeforeEach
    void setUp() {
        notificationService = mock(NotificationService.class);
        service = new LoanProcessService(notificationService);
    }

    @Test
    void shouldNotifyOnApproval() {
        LoanApplication app = new LoanApplication("123");
        app.setStatus(LoanStatus.UNDER_REVIEW);

        service.approve(app);

        verify(notificationService).notifyApproval("123");
    }

    @Test
    void shouldNotifyOnRejection() {
        LoanApplication app = new LoanApplication("123");
        app.setStatus(LoanStatus.UNDER_REVIEW);

        service.reject(app);

        verify(notificationService).notifyRejection("123");
    }
}
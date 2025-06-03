package com.example.loan;

public class LoanProcessService {

    private final NotificationService notificationService;

    public LoanProcessService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void review(LoanApplication application) {
        if (application.getStatus() == LoanStatus.NEW) {
            application.setStatus(LoanStatus.UNDER_REVIEW);
        }
    }

    public void approve(LoanApplication application) {
        if (application.getStatus() == LoanStatus.UNDER_REVIEW) {
            application.setStatus(LoanStatus.APPROVED);
            notificationService.notifyApproval(application.getId());
        }
    }

    public void reject(LoanApplication application) {
        if (application.getStatus() == LoanStatus.UNDER_REVIEW) {
            application.setStatus(LoanStatus.REJECTED);
            notificationService.notifyRejection(application.getId());
        }
    }

    public void disburse(LoanApplication application) {
        if (application.getStatus() == LoanStatus.APPROVED) {
            application.setStatus(LoanStatus.DISBURSED);
        }
    }
}
class PaymentService {
    public boolean processPayment(double amount) {
        // Symulacja płatności - losowo udana/nieudana
        return Math.random() > 0.1; // 90% szans na sukces
    }
}

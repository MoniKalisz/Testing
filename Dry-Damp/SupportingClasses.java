// Enums i Value Objects
enum CustomerType { REGULAR, VIP, PREMIUM }
enum ProductCategory { ELECTRONICS, CLOTHING, BOOKS, HOME }
enum PaymentMethod { CREDIT_CARD, DEBIT_CARD, PAYPAL, BANK_TRANSFER }
enum OrderStatus { PENDING, PROCESSING, COMPLETED, CANCELLED }
enum RiskLevel { LOW, MEDIUM, HIGH }

class DiscountResult {
    private double discountPercentage;
    private double discountAmount;
    private String reason;
   
    // constructors, getters, setters
    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }
}

class PaymentProcessingResult {
    private boolean successful;
    private String transactionId;
    private double amount;
    private String errorMessage;
   
    // constructors, getters, setters
    public boolean isSuccessful() { return successful; }
    public String getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public String getErrorMessage() { return errorMessage; }
}

enum FraudCheckResult { SAFE, SUSPICIOUS, FRAUD }

class PaymentResult {
    public static PaymentResult success(String transactionId, double amount) {
        return new PaymentResult(true, transactionId, amount, null);
    }
   
    public static PaymentResult failure(String errorMessage) {
        return new PaymentResult(false, null, 0.0, errorMessage);
    }
   
    private boolean successful;
    private String transactionId;
    private double amount;
    private String errorMessage;
   
    private PaymentResult(boolean successful, String transactionId, double amount, String errorMessage) {
        this.successful = successful;
        this.transactionId = transactionId;
        this.amount = amount;
        this.errorMessage = errorMessage;
    }
   
    // getters
    public boolean isSuccessful() { return successful; }
    public String getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public String getErrorMessage() { return errorMessage; }
}
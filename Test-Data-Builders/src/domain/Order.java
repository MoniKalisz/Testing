
package domain;

public class Order {
    private final String id;
    private final User customer;
    private final double amount;

    public Order(String id, User customer, double amount) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
    }

    public String getId() { return id; }
    public User getCustomer() { return customer; }
    public double getAmount() { return amount; }
}

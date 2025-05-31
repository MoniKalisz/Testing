
package domain;

public class OrderService {
    public boolean placeOrder(Order order) {
        return order != null;
    }

    public Order placeOrderWithItems(Order order) {
        return order;
    }
}

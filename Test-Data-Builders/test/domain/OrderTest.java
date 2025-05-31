
package test.before;

import domain.Order;
import domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    void testCreateOrder() {
        User user = new User("john", true);
        Order order = new Order("ORD-123", user, 1000);
        assertEquals("john", order.getCustomer().getUsername());
        assertEquals(1000, order.getAmount());
    }
}


package test.before;

import domain.OrderService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BadTest1 {

    @Test
    void test1() {
        OrderService orderService = new OrderService();
        assertFalse(orderService.placeOrder(null));
    }
}


package test.before;

import domain.Order;
import domain.OrderService;
import domain.Item;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BadTest2 {

    @Test
    void testOrder() {
        Order order = new Order();
        order.setItems(List.of(new Item("Apple", 1)));
        OrderService service = new OrderService();
        assertEquals(1, service.placeOrderWithItems(order).getItems().size());
    }
}

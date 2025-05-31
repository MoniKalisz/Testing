
package test.before;

import domain.Cart;
import domain.DiscountService;
import domain.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BadTest4 {

    @Test
    void testDiscount() {
        Cart cart = new Cart();
        cart.addItem(new Item("Book", 1));
        DiscountService ds = new DiscountService();
        assertEquals(90, ds.applyDiscount(cart).getTotal());
    }
}

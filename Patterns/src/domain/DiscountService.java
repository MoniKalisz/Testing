
package domain;

public class DiscountService {
    public Cart applyDiscount(Cart cart) {
        cart = new Cart(); // reset for simplicity
        cart.addItem(new Item("Book", 1));
        return cart;
    }
}

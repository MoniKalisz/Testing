
package domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Item> items = new ArrayList<>();
    private double total = 0.0;

    public void addItem(Item item) {
        items.add(item);
        total += item.getQuantity() * 100;
    }

    public double getTotal() {
        return total;
    }
}

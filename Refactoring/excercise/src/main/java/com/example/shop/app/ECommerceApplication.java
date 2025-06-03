
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ECommerceApplication {
    public static void main(String[] args) {
        // Inicjalizacja aplikacji
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        PaymentService paymentService = new PaymentService();

        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(productService);
        OrderService orderService = new OrderService(cartService, productService,
                                                    orderRepository, paymentService);

        // Przykład użycia
        System.out.println("=== APLIKACJA E-COMMERCE ===");

        // Wyświetl dostępne produkty
        System.out.println("\nDostępne produkty:");
        productService.getAllProducts().forEach(p ->
            System.out.println(p.getId() + ". " + p.getName() + " - " + p.getPrice() + " PLN (stock: " + p.getStock() + ")")
        );

        // Stwórz koszyk i dodaj produkty
        Cart cart = new Cart();
        cartService.addToCart(cart, 1L, 1); // Laptop
        cartService.addToCart(cart, 2L, 2); // Mysz x2

        System.out.println("\nKoszyk:");
        cart.getItems().forEach(item ->
            System.out.println("- " + item.getProduct().getName() + " x" + item.getQuantity() +
                             " = " + item.getTotalPrice() + " PLN")
        );
        System.out.println("Suma: " + cart.getTotalAmount() + " PLN");

        // Złóż zamówienie
        try {
            Order order = orderService.createOrder(cart);
            System.out.println("\nZamówienie utworzone: #" + order.getId());

            // Przetwórz płatność
            orderService.processPayment(order.getId(), order.getTotalAmount());

            Order updatedOrder = orderService.getOrderById(order.getId());
            System.out.println("Status zamówienia: " + updatedOrder.getStatus());

        } catch (Exception e) {
            System.err.println("Błąd: " + e.getMessage());
        }
    }
}

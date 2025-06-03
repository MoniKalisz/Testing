class OrderService {
    private CartService cartService;
    private ProductService productService;
    private OrderRepository orderRepository;
    private PaymentService paymentService;

    public OrderService(CartService cartService, ProductService productService,
                       OrderRepository orderRepository, PaymentService paymentService) {
        this.cartService = cartService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }

    public Order createOrder(Cart cart) {
        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Koszyk nie może być pusty");
        }

        // Sprawdź dostępność produktów
        for (CartItem item : cart.getItems()) {
            if (!productService.isProductAvailable(item.getProduct().getId(), item.getQuantity())) {
                throw new RuntimeException("Produkt " + item.getProduct().getName() + " niedostępny");
            }
        }

        // Zarezerwuj produkty
        for (CartItem item : cart.getItems()) {
            productService.reserveStock(item.getProduct().getId(), item.getQuantity());
        }

        Order order = new Order(null, cart.getItems());
        return orderRepository.save(order);
    }

    public void processPayment(Long orderId, double amount) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new RuntimeException("Zamówienie nie zostało znalezione");
        }

        if (Math.abs(order.getTotalAmount() - amount) > 0.01) {
            throw new RuntimeException("Kwota płatności nie zgadza się z wartością zamówienia");
        }

        boolean paymentSuccessful = paymentService.processPayment(amount);

        if (paymentSuccessful) {
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
        } else {
            throw new RuntimeException("Płatność nieudana");
        }
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id);
        if (order == null) {
            throw new RuntimeException("Zamówienie nie zostało znalezione: " + id);
        }
        return order;
    }
}

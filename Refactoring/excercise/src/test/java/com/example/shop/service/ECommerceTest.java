class ECommerceTests {


    // Test techniczny - sprawdza implementację
    public void testCartAddItem() {
        Product product = new Product(1L, "Test Product", 100.0, 10);
        Cart cart = new Cart();

        cart.addItem(product, 2);

        assert cart.getItems().size() == 1;
        assert cart.getItems().get(0).getQuantity() == 2;
        assert cart.getTotalAmount() == 200.0;
    }

    // Test techniczny - sprawdza edge case
    public void testOrderCreationWithEmptyCart() {
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        PaymentService paymentService = new PaymentService();

        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(productService);
        OrderService orderService = new OrderService(cartService, productService,
                                                    orderRepository, paymentService);

        Cart emptyCart = new Cart();

        try {
            orderService.createOrder(emptyCart);
            assert false : "Powinien rzucić wyjątek";
        } catch (RuntimeException e) {
            assert e.getMessage().contains("pusty");
        }
    }

    // Test techniczny - sprawdza logikę biznesową
    public void testStockReservation() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);

        Product product = productRepository.findById(1L);
        int initialStock = product.getStock();

        productService.reserveStock(1L, 2);

        Product updatedProduct = productRepository.findById(1L);
        assert updatedProduct.getStock() == initialStock - 2;
    }

    // Test techniczny - duplikuje setup
    public void testPaymentProcessing() {
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();
        PaymentService paymentService = new PaymentService();

        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(productService);
        OrderService orderService = new OrderService(cartService, productService,
                                                    orderRepository, paymentService);

        Cart cart = new Cart();
        Product product = new Product(1L, "Test Product", 100.0, 10);
        cart.addItem(product, 1);

        Order order = orderService.createOrder(cart);
        orderService.processPayment(order.getId(), 100.0);

        Order updatedOrder = orderService.getOrderById(order.getId());
        assert updatedOrder.getStatus() == OrderStatus.CONFIRMED;
    }

    // Test techniczny - duplikuje tworzenie produktów
    public void testProductSearch() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);

        Product laptop = new Product(1L, "Gaming Laptop", 2999.99, 5);
        Product mouse = new Product(2L, "Gaming Mouse", 199.99, 20);
        Product keyboard = new Product(3L, "Mechanical Keyboard", 399.99, 15);

        productRepository.save(laptop);
        productRepository.save(mouse);
        productRepository.save(keyboard);

        List<Product> gamingProducts = productService.searchProducts("Gaming");
        assert gamingProducts.size() == 2;
    }

    // Test techniczny - sprawdza walidację ilości
    public void testAddToCartWithInvalidQuantity() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(productService);

        Product product = new Product(1L, "Test Product", 100.0, 10);
        productRepository.save(product);

        Cart cart = new Cart();

        try {
            cartService.addToCart(cart, 1L, 0);
            assert false : "Powinien rzucić wyjątek";
        } catch (RuntimeException e) {
            assert e.getMessage().contains("większa od 0");
        }

        try {
            cartService.addToCart(cart, 1L, -1);
            assert false : "Powinien rzucić wyjątek";
        } catch (RuntimeException e) {
            assert e.getMessage().contains("większa od 0");
        }
    }

    // Test techniczny - sprawdza dostępność produktu
    public void testProductAvailabilityCheck() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);

        Product limitedProduct = new Product(1L, "Limited Product", 500.0, 3);
        productRepository.save(limitedProduct);

        assert productService.isProductAvailable(1L, 2) == true;
        assert productService.isProductAvailable(1L, 3) == true;
        assert productService.isProductAvailable(1L, 4) == false;
    }

    // Test techniczny - sprawdza obliczanie sumy koszyka
    public void testCartTotalCalculation() {
        Cart cart = new Cart();

        Product product1 = new Product(1L, "Product 1", 100.0, 10);
        Product product2 = new Product(2L, "Product 2", 250.0, 5);
        Product product3 = new Product(3L, "Product 3", 75.5, 20);

        cart.addItem(product1, 2);    // 200.0
        cart.addItem(product2, 1);    // 250.0
        cart.addItem(product3, 3);    // 226.5

        double expectedTotal = 676.5;
        assert Math.abs(cart.getTotalAmount() - expectedTotal) < 0.01;
    }

    // Test techniczny - sprawdza usuwanie z koszyka
    public void testRemoveFromCart() {
        ProductRepository productRepository = new ProductRepository();
        ProductService productService = new ProductService(productRepository);
        CartService cartService = new CartService(productService);

        Product product1 = new Product(1L, "Product 1", 100.0, 10);
        Product product2 = new Product(2L, "Product 2", 200.0, 10);

        productRepository.save(product1);
        productRepository.save(product2);

        Cart cart = new Cart();
        cartService.addToCart(cart, 1L, 2);
        cartService.addToCart(cart, 2L, 1);

        assert cart.getItems().size() == 2;
        assert cart.getTotalAmount() == 400.0;

        cartService.removeFromCart(cart, 1L);

        assert cart.getItems().size() == 1;
        assert cart.getTotalAmount() == 200.0;
    }
}

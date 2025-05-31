@ExtendWith(MockitoExtension.class)
class OrderDiscountServiceTest {
    
    @Mock private CustomerRepository customerRepository;
    @Mock private ProductRepository productRepository;
    @InjectMocks private OrderDiscountService orderDiscountService;

    // Helper method
    private Customer createTestCustomer(String type, int yearsActive, double totalSpent) {
        Customer customer = new Customer();
        customer.setId("CUST_" + System.currentTimeMillis());
        customer.setCustomerType(CustomerType.valueOf(type));
        customer.setYearsActive(yearsActive);
        customer.setTotalAmountSpent(totalSpent);
        customer.setEmail("test@example.com");
        customer.setName("Test Customer");
        return customer;
    }

    private Product createTestProduct(double price, String category) {
        Product product = new Product();
        product.setId("PROD_" + System.currentTimeMillis());
        product.setPrice(price);
        product.setCategory(ProductCategory.valueOf(category));
        product.setName("Test Product");
        product.setStock(100);
        return product;
    }

    private Order createTestOrder(Customer customer, Product... products) {
        Order order = new Order();
        order.setId("ORDER_" + System.currentTimeMillis());
        order.setCustomerId(customer.getId());
        order.setItems(new ArrayList<>());
        
        for (Product product : products) {
            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setQuantity(1);
            item.setPrice(product.getPrice());
            order.getItems().add(item);
        }
        return order;
    }

    private void mockRepositories(Customer customer, Product... products) {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        for (Product product : products) {
            when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        }
    }

    // Test 1
    @Test
    void shouldApplyVIPDiscount() {
        // Given
        Customer customer = createTestCustomer("VIP", 5, 10000.0);
        Product product = createTestProduct(100.0, "ELECTRONICS");
        Order order = createTestOrder(customer, product);
        mockRepositories(customer, product);

        // When
        DiscountResult result = orderDiscountService.calculateDiscount(order);

        // Then
        assertThat(result.getDiscountPercentage()).isEqualTo(20.0);
    }

    // Test 2
    @Test
    void shouldApplyLoyaltyDiscount() {
        // Given
        Customer customer = createTestCustomer("REGULAR", 3, 5000.0);
        Product product = createTestProduct(200.0, "CLOTHING");
        Order order = createTestOrder(customer, product);
        mockRepositories(customer, product);

        // When
        DiscountResult result = orderDiscountService.calculateDiscount(order);

        // Then
        assertThat(result.getDiscountPercentage()).isEqualTo(10.0);
    }

    // Test 3
    @Test
    void shouldNotApplyDiscountForNewCustomer() {
        // Given
        Customer customer = createTestCustomer("REGULAR", 0, 0.0);
        Product product = createTestProduct(50.0, "BOOKS");
        Order order = createTestOrder(customer, product);
        mockRepositories(customer, product);

        // When
        DiscountResult result = orderDiscountService.calculateDiscount(order);

        // Then
        assertThat(result.getDiscountPercentage()).isEqualTo(0.0);
    }
}

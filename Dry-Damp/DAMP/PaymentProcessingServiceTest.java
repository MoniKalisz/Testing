@ExtendWith(MockitoExtension.class)
class PaymentProcessingServiceTest {

    @Mock private PaymentGateway paymentGateway;
    @Mock private FraudDetectionService fraudDetectionService;
    @Mock private NotificationService notificationService;
    @Mock private OrderRepository orderRepository;
    @InjectMocks private PaymentProcessingService paymentProcessingService;

    @Test
    void shouldProcessCreditCardPaymentForRegularOrder() {
        // Given
        String orderId = "ORDER-001";
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId("CUSTOMER-001");
        order.setTotalAmount(150.0);
        order.setStatus(OrderStatus.PENDING);
        
        Customer customer = new Customer();
        customer.setId("CUSTOMER-001");
        customer.setEmail("john@example.com");
        customer.setCustomerType(CustomerType.REGULAR);
        customer.setRiskLevel(RiskLevel.LOW);
        
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setAmount(150.0);
        paymentRequest.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        paymentRequest.setCreditCardNumber("4111-1111-1111-1111");
        paymentRequest.setCustomerId("CUSTOMER-001");
        
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(fraudDetectionService.checkForFraud(any(PaymentRequest.class))).thenReturn(FraudCheckResult.SAFE);
        when(paymentGateway.processPayment(any(PaymentRequest.class))).thenReturn(PaymentResult.success("TXN-001", 150.0));

        // When
        PaymentProcessingResult result = paymentProcessingService.processPayment(paymentRequest);

        // Then
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getTransactionId()).isEqualTo("TXN-001");
        assertThat(result.getAmount()).isEqualTo(150.0);
        verify(fraudDetectionService, times(1)).checkForFraud(paymentRequest);
        verify(paymentGateway, times(1)).processPayment(paymentRequest);
        verify(notificationService, times(1)).sendPaymentConfirmation("john@example.com", result);
    }

    @Test
    void shouldProcessDebitCardPaymentForRegularOrder() {
        // Given 
        String orderId = "ORDER-002";
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId("CUSTOMER-002");
        order.setTotalAmount(75.0);
        order.setStatus(OrderStatus.PENDING);
        
        Customer customer = new Customer();
        customer.setId("CUSTOMER-002");
        customer.setEmail("jane@example.com");
        customer.setCustomerType(CustomerType.REGULAR);
        customer.setRiskLevel(RiskLevel.LOW);
        
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setAmount(75.0);
        paymentRequest.setPaymentMethod(PaymentMethod.DEBIT_CARD);
        paymentRequest.setDebitCardNumber("5555-5555-5555-4444");
        paymentRequest.setCustomerId("CUSTOMER-002");
        
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(fraudDetectionService.checkForFraud(any(PaymentRequest.class))).thenReturn(FraudCheckResult.SAFE);
        when(paymentGateway.processPayment(any(PaymentRequest.class))).thenReturn(PaymentResult.success("TXN-002", 75.0));

        // When
        PaymentProcessingResult result = paymentProcessingService.processPayment(paymentRequest);

        // Then
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getTransactionId()).isEqualTo("TXN-002");
        assertThat(result.getAmount()).isEqualTo(75.0);
        verify(fraudDetectionService, times(1)).checkForFraud(paymentRequest);
        verify(paymentGateway, times(1)).processPayment(paymentRequest);
        verify(notificationService, times(1)).sendPaymentConfirmation("jane@example.com", result);
    }

    @Test
    void shouldProcessPayPalPaymentForRegularOrder() {
        // Given
        String orderId = "ORDER-003";
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId("CUSTOMER-003");
        order.setTotalAmount(200.0);
        order.setStatus(OrderStatus.PENDING);
       
        Customer customer = new Customer();
        customer.setId("CUSTOMER-003");
        customer.setEmail("bob@example.com");
        customer.setCustomerType(CustomerType.REGULAR);
        customer.setRiskLevel(RiskLevel.LOW);
       
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setAmount(200.0);
        paymentRequest.setPaymentMethod(PaymentMethod.PAYPAL);
        paymentRequest.setPaypalEmail("bob@example.com");
        paymentRequest.setCustomerId("CUSTOMER-003");
       
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(fraudDetectionService.checkForFraud(any(PaymentRequest.class))).thenReturn(FraudCheckResult.SAFE);
        when(paymentGateway.processPayment(any(PaymentRequest.class))).thenReturn(PaymentResult.success("TXN-003", 200.0));

        // When
        PaymentProcessingResult result = paymentProcessingService.processPayment(paymentRequest);

        // Then
        assertThat(result.isSuccessful()).isTrue();
        assertThat(result.getTransactionId()).isEqualTo("TXN-003");
        assertThat(result.getAmount()).isEqualTo(200.0);
        verify(fraudDetectionService, times(1)).checkForFraud(paymentRequest);
        verify(paymentGateway, times(1)).processPayment(paymentRequest);
        verify(notificationService, times(1)).sendPaymentConfirmation("bob@example.com", result);
    }

    @Test
    void shouldRejectPaymentWhenFraudDetected() {
        // Given
        String orderId = "ORDER-004";
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId("CUSTOMER-004");
        order.setTotalAmount(1500.0);
        order.setStatus(OrderStatus.PENDING);
       
        Customer customer = new Customer();
        customer.setId("CUSTOMER-004");
        customer.setEmail("suspicious@example.com");
        customer.setCustomerType(CustomerType.REGULAR);
        customer.setRiskLevel(RiskLevel.HIGH);
       
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setAmount(1500.0);
        paymentRequest.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        paymentRequest.setCreditCardNumber("4111-1111-1111-1111");
        paymentRequest.setCustomerId("CUSTOMER-004");
       
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(fraudDetectionService.checkForFraud(any(PaymentRequest.class))).thenReturn(FraudCheckResult.SUSPICIOUS);

        // When
        PaymentProcessingResult result = paymentProcessingService.processPayment(paymentRequest);

        // Then
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Payment rejected due to fraud suspicion");
        verify(fraudDetectionService, times(1)).checkForFraud(paymentRequest);
        verify(paymentGateway, never()).processPayment(any());
        verify(notificationService, times(1)).sendFraudAlert("suspicious@example.com", paymentRequest);
    }

    @Test
    void shouldHandlePaymentGatewayFailure() {
        // Given
        String orderId = "ORDER-005";
        Order order = new Order();
        order.setId(orderId);
        order.setCustomerId("CUSTOMER-005");
        order.setTotalAmount(99.0);
        order.setStatus(OrderStatus.PENDING);
       
        Customer customer = new Customer();
        customer.setId("CUSTOMER-005");
        customer.setEmail("unlucky@example.com");
        customer.setCustomerType(CustomerType.REGULAR);
        customer.setRiskLevel(RiskLevel.LOW);
       
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(orderId);
        paymentRequest.setAmount(99.0);
        paymentRequest.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        paymentRequest.setCreditCardNumber("4111-1111-1111-1111");
        paymentRequest.setCustomerId("CUSTOMER-005");
       
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(fraudDetectionService.checkForFraud(any(PaymentRequest.class))).thenReturn(FraudCheckResult.SAFE);
        when(paymentGateway.processPayment(any(PaymentRequest.class))).thenReturn(PaymentResult.failure("Gateway timeout"));

        // When
        PaymentProcessingResult result = paymentProcessingService.processPayment(paymentRequest);

        // Then
        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getErrorMessage()).isEqualTo("Gateway timeout");
        verify(fraudDetectionService, times(1)).checkForFraud(paymentRequest);
        verify(paymentGateway, times(1)).processPayment(paymentRequest);
        verify(notificationService, times(1)).sendPaymentFailureNotification("unlucky@example.com", result);
    }
}
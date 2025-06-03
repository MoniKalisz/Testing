class Order {
    private Long id;
    private List<CartItem> items;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(Long id, List<CartItem> items) {
        this.id = id;
        this.items = new ArrayList<>(items);
        this.totalAmount = items.stream().mapToDouble(CartItem::getTotalPrice).sum();
        this.status = OrderStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public List<CartItem> getItems() { return new ArrayList<>(items); }
    public double getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(OrderStatus status) { this.status = status; }
}

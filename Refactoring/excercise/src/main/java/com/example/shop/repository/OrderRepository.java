class OrderRepository {
    private Map<Long, Order> orders = new HashMap<>();
    private Long nextId = 1L;

    public Order save(Order order) {
        if (order.getId() == null) {
            order = new Order(nextId++, order.getItems());
        }
        orders.put(order.getId(), order);
        return order;
    }

    public Order findById(Long id) {
        return orders.get(id);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
}

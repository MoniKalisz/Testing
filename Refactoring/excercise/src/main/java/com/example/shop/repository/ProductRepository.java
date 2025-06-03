class ProductRepository {
    private Map<Long, Product> products = new HashMap<>();
    private Long nextId = 1L;

    public ProductRepository() {
        // Przykładowe dane
        save(new Product(null, "Laptop", 2999.99, 10));
        save(new Product(null, "Mysz", 99.99, 50));
        save(new Product(null, "Klawiatura", 299.99, 30));
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product = new Product(nextId++, product.getName(), product.getPrice(), product.getStock());
        }
        products.put(product.getId(), product);
        return product;
    }

    public Product findById(Long id) {
        return products.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public List<Product> findByNameContaining(String name) {
        return products.values().stream()
                      .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                      .collect(Collectors.toList());
    }
}

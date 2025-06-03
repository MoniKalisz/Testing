class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            throw new RuntimeException("Produkt nie został znaleziony: " + id);
        }
        return product;
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContaining(query);
    }

    public boolean isProductAvailable(Long productId, int quantity) {
        Product product = getProductById(productId);
        return product.getStock() >= quantity;
    }

    public void reserveStock(Long productId, int quantity) {
        Product product = getProductById(productId);
        if (product.getStock() < quantity) {
            throw new RuntimeException("Niewystarczająca ilość na stanie");
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
}

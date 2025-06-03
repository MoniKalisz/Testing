class CartService {
    private ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public void addToCart(Cart cart, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new RuntimeException("Ilość musi być większa od 0");
        }

        Product product = productService.getProductById(productId);

        if (!productService.isProductAvailable(productId, quantity)) {
            throw new RuntimeException("Produkt niedostępny w wymaganej ilości");
        }

        cart.addItem(product, quantity);
    }

    public void removeFromCart(Cart cart, Long productId) {
        Product product = productService.getProductById(productId);
        cart.removeItem(product);
    }

    public double calculateTotal(Cart cart) {
        return cart.getTotalAmount();
    }
}

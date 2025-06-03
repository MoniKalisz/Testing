
class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(Product product, int quantity) {
        CartItem existingItem = findItemByProduct(product);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            items.add(new CartItem(product, quantity));
        }
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().getId().equals(product.getId()));
    }

    public double getTotalAmount() {
        return items.stream()
                   .mapToDouble(CartItem::getTotalPrice)
                   .sum();
    }

    public List<CartItem> getItems() { return new ArrayList<>(items); }

    private CartItem findItemByProduct(Product product) {
        return items.stream()
                   .filter(item -> item.getProduct().getId().equals(product.getId()))
                   .findFirst()
                   .orElse(null);
    }
}

// 代码生成时间: 2025-09-07 14:24:55
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// ShoppingCartService is an application-scoped service responsible for managing the shopping cart operations.
@ApplicationScoped
public class ShoppingCartService {

    // Injecting the repository for managing cart items
    @Inject
    private CartItemRepository cartItemRepository;

    // Adds an item to the shopping cart
    @Transactional
    public CartItem addItemToCart(Long cartId, Long itemId) {
        // Check if cart exists
        Optional<CartItem> existingItem = cartItemRepository.findById(itemId);
        if (existingItem.isPresent()) {
            // Update the existing item quantity
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            return cartItemRepository.update(item);
        } else {
            // Create a new cart item
            CartItem newItem = new CartItem();
            newItem.setCartId(cartId);
            newItem.setItemId(itemId);
            newItem.setQuantity(1);
            return cartItemRepository.create(newItem);
        }
    }

    // Removes an item from the shopping cart
    @Transactional
    public boolean removeItemFromCart(Long itemId) {
        Optional<CartItem> itemToRemove = cartItemRepository.findById(itemId);
        if (itemToRemove.isPresent()) {
            cartItemRepository.delete(itemToRemove.get());
            return true;
        } else {
            // Item not found in the cart; throw an exception or handle accordingly
            throw new IllegalArgumentException("Item not found in the cart");
        }
    }

    // Lists all items in the shopping cart
    public List<CartItem> listCartItems(Long cartId) {
        // Assuming a method that filters items by cartId exists in the repository
        return cartItemRepository.findAllByCartId(cartId);
    }
}

// Repository interface for CartItem
public interface CartItemRepository {
    Optional<CartItem> findById(Long itemId);
    CartItem create(CartItem item);
    CartItem update(CartItem item);
    void delete(CartItem item);
    List<CartItem> findAllByCartId(Long cartId);
}

// Entity representing a cart item
public class CartItem {
    private Long id;
    private Long cartId;
    private Long itemId;
    private int quantity;

    // Getters and setters for the fields
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

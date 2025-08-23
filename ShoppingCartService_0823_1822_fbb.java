// 代码生成时间: 2025-08-23 18:22:34
package com.example.shoppingcart;

import io.quarkus.runtime.annotations.RegisterForReflection;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

// DTO for cart items
@RegisterForReflection
public class CartItem {
    private String productId;
    private int quantity;

    public CartItem() {
    }

    public CartItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

// DTO for the shopping cart
@RegisterForReflection
public class ShoppingCart {
    private List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    // Adds a new item or updates the quantity of an existing item
    public void addItem(@Valid @NotNull CartItem item) {
        if (item.getQuantity() <= 0) {
            removeItem(item.getProductId());
            return;
        }

        items.stream()
            .filter(cartItem -> cartItem.getProductId().equals(item.getProductId()))
            .findFirst()
            .ifPresent(cartItem -> cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity()));

        items.stream()
            .filter(cartItem -> cartItem.getProductId().equals(item.getProductId()))
            .findAny()
            .orElseGet(() -> items.add(item));
    }

    // Removes an item from the cart
    public void removeItem(String productId) {
        items.removeIf(cartItem -> cartItem.getProductId().equals(productId));
    }

    // Returns the list of items in the cart
    public List<CartItem> getItems() {
        return items;
    }
}

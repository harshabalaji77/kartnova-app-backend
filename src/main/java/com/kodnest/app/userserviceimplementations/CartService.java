package com.kodnest.app.userserviceimplementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodnest.app.entities.CartItem;
import com.kodnest.app.entities.Product;
import com.kodnest.app.entities.ProductImage;
import com.kodnest.app.entities.User;
import com.kodnest.app.userrepositories.CartRepository;
import com.kodnest.app.userrepositories.ProductImageRepository;
import com.kodnest.app.userrepositories.ProductRepository;
import com.kodnest.app.userservices.CartServiceContract;

@Service
public class CartService implements CartServiceContract {
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductImageRepository productImageRepository;
	
	public CartService(CartRepository cartRepository, ProductRepository productRepository,
			ProductImageRepository productImageRepository) {
		super();
		this.cartRepository = cartRepository;
		this.productRepository = productRepository;
		this.productImageRepository = productImageRepository;
	}
	
	// Add an item to the cart
	public void addToCart(User user, int productId, int quantity) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

		// Fetch cart item for this userId and productId
		Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(user.getUserId(), productId);

		if (existingItem.isPresent()) {
			CartItem cartItem = existingItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			cartRepository.save(cartItem);
		} else {
			CartItem newItem = new CartItem(user, product, quantity);
			cartRepository.save(newItem);
		}
	}
	
	// Get Cart Items for a User
	public Map<String, Object> getCartItems(User authenticatedUser) {
		// Fetch the cart items for the user with product details
		List<CartItem> cartItems = cartRepository.findCartItemsWithProductDetails(authenticatedUser.getUserId());
		
		// Create a response map to hold the cart details
		Map<String, Object> response = new HashMap<>();
		response.put("username", authenticatedUser.getUsername());
		response.put("role", authenticatedUser.getRole().toString());
		
		// List to hold the product details
		List<Map<String, Object>> products = new ArrayList<>();
		int overallTotalPrice = 0;
		
		for (CartItem cartItem : cartItems) {
			Map<String, Object> productDetails = new HashMap<>();

			// Get product details
			Product product = cartItem.getProduct();

			// Fetch product images from the ProductImageRepository
			List<ProductImage> productImages = productImageRepository.findByProduct_ProductId(product.getProductId());
			String imageUrl = (productImages != null && !productImages.isEmpty()) ? productImages.get(0).getImageUrl() : "default-image-url";

			// Populate product details into the map
			productDetails.put("product_id", product.getProductId());
			productDetails.put("image_url", imageUrl);
			productDetails.put("name", product.getName());
			productDetails.put("description", product.getDescription());
			productDetails.put("price_per_unit", product.getPrice());
			productDetails.put("quantity", cartItem.getQuantity());
			productDetails.put("total_price", cartItem.getQuantity() * product.getPrice().doubleValue());
			
			// Add the product details to the products list
			products.add(productDetails);

			// Add to the overall total price
			overallTotalPrice += cartItem.getQuantity() * product.getPrice().doubleValue();
		}
		// Prepare the final cart response
		Map<String, Object> cart = new HashMap<>();
		cart.put("products", products);
		cart.put("overall_total_price", overallTotalPrice);

		// Add the cart details to the response
		response.put("cart", cart);
		return response;
	}
	
	// Update Cart Item Quantity
	public void updateCartItemQuantity(User authenticatedUser, int productId, int quantity) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		// Fetch cart item for this userId and productId
		Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(authenticatedUser.getUserId(), productId);

		if (existingItem.isPresent()) {
			CartItem cartItem = existingItem.get();
			if (quantity == 0) {
				deleteCartItem(authenticatedUser.getUserId(), productId);
			} else {
				cartItem.setQuantity(quantity);
				cartRepository.save(cartItem);
			}
		}
	}
	
	// Delete Cart Item
	public void deleteCartItem(int userId, int productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("Product not found"));

		cartRepository.deleteCartItem(userId, productId);
	}
	
	// Get the total cart item count for a user
	public int getCartItemCount(int userId) {
		return cartRepository.countTotalItems(userId);
	}
}

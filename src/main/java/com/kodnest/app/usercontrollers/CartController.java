package com.kodnest.app.usercontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodnest.app.entities.User;
import com.kodnest.app.userrepositories.UserRepository;
import com.kodnest.app.userservices.CartServiceContract;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
    private CartServiceContract cartService;
	
    public CartController(CartServiceContract cartService) {
		super();
		this.cartService = cartService;
	}

	@PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
    public ResponseEntity<Void> addToCart(@RequestBody Map<String, Object> request, HttpServletRequest req) {
    	User user = (User) req.getAttribute("authenticatedUser");
    	String username = (String) request.get("username");
        int productId = (int) request.get("productId");
        
        // Handle quantity: Default to 1 if not provided
        int quantity = request.containsKey("quantity") ? (int) request.get("quantity") : 1;
        
        // Add the product to the cart
        cartService.addToCart(user, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
	
	// Fetch all cart items for the user (based on username)
    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getCartItems(HttpServletRequest request) {
        // Fetch user by username to get the userId
    	User user= (User) request.getAttribute("authenticatedUser");

        // Call the service to get cart items for the user
        Map<String, Object> response = cartService.getCartItems(user);
        return ResponseEntity.ok(response);
    }
    
    // Update Cart Item Quantity
    @PutMapping("/update")
    public ResponseEntity<Void> updateCartItemQuantity(@RequestBody Map<String, Object> request, HttpServletRequest req) {
    	User user = (User) req.getAttribute("authenticatedUser");
        String username = (String) request.get("username");
        int productId = (int) request.get("productId");
        int quantity = (int) request.get("quantity");

        // Update the cart item quantity
        cartService.updateCartItemQuantity(user, productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    // Delete Cart Item
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCartItem(@RequestBody Map<String, Object> request, HttpServletRequest req) {
    	User user = (User) req.getAttribute("authenticatedUser");
        String username = (String) request.get("username");
        int productId = (int) request.get("productId");

        // Delete the cart item
        cartService.deleteCartItem(user.getUserId(), productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    // Fetch userId from username coming from the filter and get cart item count
    @GetMapping("/items/count")
    public ResponseEntity<Integer> getCartItemCount(@RequestParam String username, HttpServletRequest request) {
    	User user = (User) request.getAttribute("authenticatedUser");
 
        // Call the service to get the total cart item count
        int count = cartService.getCartItemCount(user.getUserId());
        return ResponseEntity.ok(count);
    }
}

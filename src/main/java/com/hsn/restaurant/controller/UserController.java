package com.hsn.restaurant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsn.restaurant.request.CartItemRequest;
import com.hsn.restaurant.request.OrderRequest;
import com.hsn.restaurant.request.UpdateQuantityRequest;
import com.hsn.restaurant.service.CartService;
import com.hsn.restaurant.service.CategoryService;
import com.hsn.restaurant.service.OrderService;
import com.hsn.restaurant.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final CartService cartService;
	private final OrderService orderService;
	private final CategoryService categoryService;
	private final ProductService productService;
	
	//manage cart
    @PutMapping("/add-item")
	public ResponseEntity<?> AddItemToCart(@Valid @RequestBody CartItemRequest itemRequest, Authentication auth) {
		return ResponseEntity.ok(cartService.addItemToCart(itemRequest, auth));
	}
    @PutMapping("/cart/update-quantity")
    public ResponseEntity<?>updateCartItemQuantity(@Valid @RequestBody UpdateQuantityRequest request){
    	return ResponseEntity.ok(cartService.updateCartItemQuantity(request.getProductId(), request.getQuantity()));
    }
    
    @PutMapping("/cart/remove-item/{itemId}")
    public ResponseEntity<?>removeItemFromCart(@PathVariable Long itemId,Authentication auth){
    	return ResponseEntity.ok(cartService.removeItemFromCart(itemId,auth));
    }
    
    @PutMapping("/cart/clear")
    public ResponseEntity<?>clearCart(Authentication authentication){
    	return ResponseEntity.ok(cartService.clearCart(authentication));
    }
    @GetMapping("/cart")
    public ResponseEntity<?>viewCart(Authentication authentication){
    	return ResponseEntity.ok(cartService.findCartByUserId(authentication));
    }
    
    // Order
    @PostMapping("/order/create")
    public ResponseEntity<?>createOrder(@Valid@RequestBody OrderRequest orderRequest,Authentication auth){
    	return ResponseEntity.ok(orderService.createOrder(orderRequest, auth));
    }
    
    @DeleteMapping("/order/cancel/{orderId}")
    public ResponseEntity<?>cancelOrder(@PathVariable Long orderId,Authentication auth){
    	return ResponseEntity.ok(orderService.cancelOrder(orderId, auth));
    }
    
    @GetMapping("/order")
    public ResponseEntity<?>viewOrder(Authentication authentication){
    	return ResponseEntity.ok(orderService.findByUser(authentication));
    }
    
    //view
    @GetMapping("/categories")
	public ResponseEntity<?> findAll(Authentication authentication) {
		return ResponseEntity.ok(categoryService.findAll());
	}
    
    @GetMapping("/products/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}
    
    @GetMapping("/products")
	public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "10", required = false) int size) {
		return ResponseEntity.ok(productService.findAll(page, size));
	}
    @GetMapping("/products-category/{categoryId}")
	public ResponseEntity<?> findAllByCategory(@PathVariable Long categoryId,
			@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "10", required = false) int size) {
		return ResponseEntity.ok(productService.findAllByCategorey(page, size, categoryId));
	}

}
